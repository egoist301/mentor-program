import React, { Component } from 'react';
import DoctorLogic from "./DoctorLogic";
import { Prompt, withRouter } from "react-router-dom";
import { localizedStrings } from "../util/Localization";
import Illness from './Illness/Illness';

class EditDoctor extends Component {


    constructor(props) {
        super(props);

        const doctor =
            this.props.location.doctor === undefined ?
                JSON.parse(sessionStorage.getItem('doctorEdit'))
                : this.props.location.doctor;

        if (this.props.location.doctor !== undefined) {
            const doctorEdit = JSON.parse(JSON.stringify(this.props.location.doctor));
            sessionStorage.setItem('doctorEdit', JSON.stringify(doctorEdit));
        }

        this.state = {
            doctor: {
                edited: true,
                id: doctor.id,
                firstName: {
                    text: doctor.first_name,
                    validateStatus: 'success'
                },
                lastName: {
                    text: doctor.last_name,
                    validateStatus: 'success'
                },
                middleName: {
                    text: doctor.middle_name,
                    validateStatus: 'success'
                },
                phone: {
                    value: doctor.phone_number,
                    validateStatus: 'success'
                },
                price: {
                    value: doctor.price_per_consultation,
                    validateStatus: 'success'
                },
                dateOfBirth: {
                    value: doctor.date_of_birth,
                    validateStatus: 'success'
                },
                illnesses: doctor.illnesses.map(illness => {
                    return {
                        name: {
                            text: illness.name,
                            validateStatus: {
                                validateStatus: 'success',
                                errorMsg: '',
                            }
                        },
                        description: {
                            text: illness.description,
                            validateStatus: {
                                validateStatus: 'success',
                                errorMsg: '',
                            }
                        },
                        chanceToDie: {
                            value: illness.chance_to_die,
                            validateStatus: {
                                validateStatus: 'success',
                                errorMsg: '',
                            }
                        },
                        validateStatus:'success'
                    }
                }).slice()
            }
            ,
            dataUnsaved: false
        };

        this.setUnsavedDataStatus = this.setUnsavedDataStatus.bind(this);
    }


    componentDidMount() {
        window.addEventListener('beforeunload', this.beforeunload.bind(this));
    }

    componentWillUnmount() {
        window.removeEventListener('beforeunload', this.beforeunload.bind(this));
    }

    beforeunload(e) {
        if (this.state.dataUnsaved !== undefined && this.state.dataUnsaved !== null && this.state.dataUnsaved) {
            e.preventDefault();
            e.returnValue = true;
        }
    }

    setUnsavedDataStatus(status) {
        this.state.dataUnsaved = status;
        console.log(status);
        this.setState({
            state: status
        })
    }

    render() {
        return (
            <div>
                <h1 className="page-title">
                    {localizedStrings.editDoctor}
                </h1>
                <DoctorLogic doctor={this.state.doctor}
                    setUnsavedDataStatus={this.setUnsavedDataStatus} />
                <Prompt
                    when={this.state.dataUnsaved}
                    message={location =>
                        localizedStrings.alertRebootPage
                    }
                />
            </div>
        );
    }
}

export default EditDoctor;
