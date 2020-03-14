import React, {Component} from 'react';
import DoctorLogic from "./DoctorLogic";
import {localizedStrings} from "../util/Localization";
import {Prompt} from "react-router-dom";


class AddDoctor extends Component {

    constructor(props) {
        super(props);

        this.state = {

            doctor: {
                edited: false,
                firstName: {
                    text: '',
                    validateStatus: ''
                },
                lastName: {
                    text: '',
                    validateStatus: ''
                },
                middleName: {
                    text: '',
                    validateStatus: ''
                },
                phone: {
                    value: '',
                    validateStatus: ''
                },
                price: {
                    value: '',
                    validateStatus: ''
                },
                dateOfBirth: {
                    value: new Date(),
                    validateStatus: ''
                },
                illnesses: [{
                    name: {
                        text: '',
                        validateStatus: ''
                    },
                    description: {
                        text: '',
                        validateStatus: ''
                    },
                    chanceToDie: {
                        value: '',
                        validateStatus: ''
                    }
                }]
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
        if (this.state.dataUnsaved !== null && this.state.dataUnsaved) {
            e.preventDefault();
            e.returnValue = true;
        }
    }

    setUnsavedDataStatus(status) {
        this.state.dataUnsaved = status;
        this.setState({
            state: status
        })
    }


    render() {

        return (
            <div>
                <h1 className="page-title">
                    {localizedStrings.addDoctor}
                </h1>

                <DoctorLogic doctor={this.state.doctor}
                                  setUnsavedDataStatus={this.setUnsavedDataStatus}/>
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


export default AddDoctor;