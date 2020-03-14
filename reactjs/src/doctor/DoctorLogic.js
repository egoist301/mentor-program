import React, { Component } from 'react';
import {
    ILLNESS_DESCRIPTION_MAX_LENGTH,
    DOCTOR_FIRST_NAME_MAX_LENGTH,
    DOCTOR_FIRST_NAME_MIN_LENGTH,
    DOCTOR_LAST_NAME_MAX_LENGTH,
    DOCTOR_LAST_NAME_MIN_LENGTH,
    DOCTOR_MIDDLE_NAME_MAX_LENGTH,
    DOCTOR_MIDDLE_NAME_MIN_LENGTH,
    ILLNESS_NAME_MAX_LENGTH,
    ILLNESS_NAME_MIN_LENGTH,
    MAX_PRICE,
    MAX_NUMBER,
    MIN_NUMBER,
    MAX_ILLNESS,
    ILLNESS_DESCRIPTION_MIN_LENGTH,
    ILLNESS_CHANCE_TO_DIE_MIN,
    ILLNESS_CHANCE_TO_DIE_MAX,
    DOCTOR_PHONE_LENGTH
} from '../constants';
import './DoctorLogic.css';
import { Button, Form, Icon, Input, notification, Select, DatePicker } from 'antd';
import moment from 'moment';
import { createDoctor, editDoctor } from "../util/APIUtils";
import { withRouter } from "react-router-dom";
import { localizedStrings } from "../util/Localization";
import DeleteDoctorModal from "./modal/DeleteDoctorModal";


const FormItem = Form.Item;
const Option = Select.Option;
const { TextArea } = Input;

class DoctorLogic extends Component {

    constructor(props) {
        super(props);

        this.state = {

            prevCert: props.doctor,

            edited: props.doctor.edited,

            id: props.doctor.id,
            firstName: {
                text: props.doctor.firstName.text,
                validateStatus: props.doctor.firstName.validateStatus
            },
            lastName: {
                text: props.doctor.lastName.text,
                validateStatus: props.doctor.lastName.validateStatus
            },
            middleName: {
                text: props.doctor.middleName.text,
                validateStatus: props.doctor.middleName.validateStatus
            },
            phone: {
                value: props.doctor.phone.value,
                validateStatus: props.doctor.phone.validateStatus
            },
            price: {
                value: props.doctor.price.value,
                validateStatus: props.doctor.price.validateStatus
            },
            dateOfBirth: {
                value: props.doctor.dateOfBirth.value,
                validateStatus: props.doctor.dateOfBirth.validateStatus
            },
            illnesses: props.doctor.illnesses,

            isUpdated: false
        };

        this.addIllness = this.addIllness.bind(this);
        this.removeIllness = this.removeIllness.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleFirstNameChange = this.handleFirstNameChange.bind(this);
        this.handleLastNameChange = this.handleLastNameChange.bind(this);
        this.handleMiddleNameChange = this.handleMiddleNameChange.bind(this);
        this.handleIllnessNameChange = this.handleIllnessNameChange.bind(this);
        this.handlePriceChange = this.handlePriceChange.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
        this.handleIllnessDescriptionChange = this.handleIllnessDescriptionChange.bind(this);
        this.handleIllnessChanceChange = this.handleIllnessChanceChange.bind(this);
        this.handlePhoneChange = this.handlePhoneChange.bind(this);
        this.handleDateOfBirth = this.handleDateOfBirth.bind(this);
    }


    addIllness() {
        const illnesses = this.state.illnesses.slice();
        this.setState({
            illnesses: illnesses.concat([{
                name: {
                    text: '',
                    validateStatus: ''
                },
                description: {
                    text: '',
                    validateStatus: ''
                },
                chanceToDie: {
                    text: '',
                    validateStatus: ''
                }
            }])
        });
    }


    removeIllness(choiceNumber) {
        const illnesses = this.state.illnesses.slice();
        this.setState({
            illnesses: [...illnesses.slice(0, choiceNumber), ...illnesses.slice(choiceNumber + 1)]
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        this.props.setUnsavedDataStatus(false);

        const doctorData = {
            id: this.state.id,
            first_name: this.state.firstName.text,
            last_name: this.state.lastName.text,
            middle_name: this.state.middleName.text,
            phone_number: this.state.phone.value,
            price_per_consultation: this.state.price.value,
            date_of_birth: this.state.dateOfBirth.value,
            illnesses: this.state.illnesses.map(illness => {
                return {
                    name: illness.name.text,
                    description: illness.description.text,
                    chance_to_die: illness.chanceToDie.value
                }
            })
        };
        console.log('handleSubmit', doctorData)
        if (this.state.edited === false) {
            createDoctor(doctorData)
                .then(() => {
                    notification.success({
                        message: localizedStrings.alertAppName,
                        description: localizedStrings.alertAddDoctorSuccessfully
                    });
                    this.props.history.push("/");
                }).catch(error => {
                    if (error.status === 401) {
                        this.props.handleLogout('/login', 'error', localizedStrings.alertLoggedOut);
                    } else {
                        notification.error({
                            message: localizedStrings.alertAppName,
                            description: error.message || localizedStrings.alertException + error.message
                        });
                    }
                });
        } else {
            editDoctor(doctorData)
                .then(() => {
                    console.log(doctorData)
                    notification.success({
                        message: localizedStrings.alertAppName,
                        description: localizedStrings.alertEditDoctorSuccessfully
                    });
                    this.props.history.push("/");
                }).catch(error => {
                    if (error.status === 401) {
                        this.props.handleLogout('/login', 'error', localizedStrings.alertLoggedOut);
                    } else {
                        notification.error({
                            message: localizedStrings.alertAppName,
                            description: error.message || localizedStrings.alertException
                        });
                    }
                });

        }
    }

    handleFirstNameChange(event) {
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        this.setState({
            firstName: {
                text: value,
                ...this.validateFirstName(value)
            }
        });
    }

    handleLastNameChange(event) {
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        this.setState({
            lastName: {
                text: value,
                ...this.validateLastName(value)
            }
        });
    }

    handleMiddleNameChange(event) {
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        this.setState({
            middleName: {
                text: value,
                ...this.validateMiddleName(value)
            }
        });
    }

    handlePriceChange(event) {
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        this.setState({
            price: {
                value: value,
                ...this.validatePrice(value)
            }
        })
    }

    handlePhoneChange(event) {
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        this.setState({
            phone: {
                value: value,
                ...this.validatePhone(value)
            }
        })
    }

    handleDateOfBirth(value) {
        this.props.setUnsavedDataStatus(true);

        this.setState({
            dateOfBirth: {
                value: value
            }
        })
    }


    handleIllnessNameChange(event, index) {
        const illnesses = this.state.illnesses;
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);
        console.log(illnesses);
        illnesses[index].name.text = value;
        illnesses[index].name.validateStatus = this.validateIllnessName(value);
    }

    handleIllnessDescriptionChange(event, index) {
        const illnesses = this.state.illnesses;
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        illnesses[index].description.text = value;
        illnesses[index].description.validateStatus = this.validateDescription(value);
    }

    handleIllnessChanceChange(event, index) {
        const illnesses = this.state.illnesses;
        const value = event.target.value;
        this.props.setUnsavedDataStatus(true);

        illnesses[index].chanceToDie.value = value;
        illnesses[index].chanceToDie.validateStatus = this.validateIllnessChanceToDie(value);
    }


    isFormInvalid() {
        if (this.state.firstName.validateStatus !== 'success') {
            return true;
        }
        if (this.state.lastName.validateStatus !== 'success') {
            return true;
        }
        if (this.state.middleName.validateStatus !== 'success') {
            return true;
        }
        if (this.state.price.validateStatus !== 'success') {
            return true;
        }
        for (let i = 0; i < this.state.illnesses.length; i++) {
            const name = this.state.illnesses[i].name;
            if (name.validateStatus !== 'success') {
                return true;
            }
        }
        for (let i = 0; i < this.state.illnesses.length; i++) {
            const description = this.state.illnesses[i].description;
            if (description.validateStatus !== 'success') {
                return true;
            }
        }
        for (let i = 0; i < this.state.illnesses.length; i++) {
            const chanceToDie = this.state.illnesses[i].chanceToDie;
            if (chanceToDie.validateStatus !== 'success') {
                return true;
            }
        }
    }

    render() {
        const illnessesViews = [];
        this.state.illnesses.forEach((illness, index) => {
            illnessesViews.push(<DoctorIllness key={index}
                illness={illness}
                illnessNumber={index}
                removeIllness={this.removeIllness}
                handleIllnessNameChange={this.handleIllnessNameChange}
                handleIllnessDescriptionChange={this.handleIllnessDescriptionChange}
                handleIllnessChanceChange={this.handleIllnessChanceChange} />);
        });
        return (
            <div className="new-doctor-container">
                <div className="new-doctor-content">
                    <Form onSubmit={this.handleSubmit} className="create-doctor-form">
                        <FormItem
                            label={localizedStrings.firstName}
                            validateStatus={this.state.firstName.validateStatus}
                            help={this.state.firstName.errorMsg}
                            className="doctor-form-row">
                            <Input
                                type={"text"}
                                placeholder={localizedStrings.helpForFirstName}
                                style={{ fontSize: '16px' }}
                                autosize={{ minRows: 3, maxRows: 6 }}
                                name="firstName"
                                value={this.state.firstName.text}
                                onChange={this.handleFirstNameChange} />
                        </FormItem>
                        <FormItem
                            label={localizedStrings.lastName}
                            validateStatus={this.state.lastName.validateStatus}
                            help={this.state.lastName.errorMsg}
                            className="doctor-form-row">
                            <Input
                                type={"text"}
                                placeholder={localizedStrings.helpForLastName}
                                style={{ fontSize: '16px' }}
                                autosize={{ minRows: 3, maxRows: 6 }}
                                name="lastName"
                                value={this.state.lastName.text}
                                onChange={this.handleLastNameChange} />
                        </FormItem>
                        <FormItem
                            label={localizedStrings.middleName}
                            validateStatus={this.state.middleName.validateStatus}
                            help={this.state.middleName.errorMsg}
                            className="doctor-form-row">
                            <Input
                                type={"text"}
                                placeholder={localizedStrings.helpForMiddleName}
                                style={{ fontSize: '16px' }}
                                autosize={{ minRows: 3, maxRows: 6 }}
                                name="middleName"
                                value={this.state.middleName.text}
                                onChange={this.handleMiddleNameChange} />
                        </FormItem>
                        <FormItem
                            label={localizedStrings.price}
                            validateStatus={this.state.price.validateStatus}
                            help={this.state.price.errorMsg}
                            className="doctor-form-row">
                            <Input
                                type={"number"}
                                placeholder={localizedStrings.helpForDoctorPrice}
                                style={{ fontSize: '16px' }}
                                autosize={{ minRows: 3, maxRows: 6 }}
                                name="price"
                                value={this.state.price.value}
                                onChange={this.handlePriceChange} />
                        </FormItem>
                        <FormItem
                            label={localizedStrings.phone}
                            validateStatus={this.state.phone.validateStatus}
                            help={this.state.phone.errorMsg}
                            className="doctor-form-row">
                            <Input
                                type={"tel"}
                                placeholder={localizedStrings.helpForPhone}
                                style={{ fontSize: '16px' }}
                                autosize={{ minRows: 3, maxRows: 6 }}
                                name="phone"
                                value={this.state.phone.value}
                                onChange={this.handlePhoneChange} />
                        </FormItem>
                        <FormItem
                            label={localizedStrings.dateOfBirth}
                            validateStatus={this.state.dateOfBirth.validateStatus}
                            help={this.state.dateOfBirth.errorMsg}
                            className="doctor-form-row">
                            <DatePicker
                                name="dateOfBirth"
                                value={moment(this.state.dateOfBirth.value, 'yyyy-MM-dd')}
                                onChange={this.handleDateOfBirth}
                                dateFormat={"yyyy-MM-dd"} />

                        </FormItem>

                        {illnessesViews}


                        <FormItem className="doctor-form-row">
                            <Button type="dashed" onClick={this.addIllness}
                                disabled={this.state.illnesses.length === MAX_ILLNESS}>
                                <Icon type="plus-circle" /> {localizedStrings.addIllness}
                            </Button>
                        </FormItem>


                        <FormItem className="doctor-form-row">
                            <div className="buttons-position">
                                <span className={this.state.edited ? 'custom-hidden' : ''}>
                                    <Button type="primary"
                                        htmlType="submit"
                                        size="large"
                                        disabled={this.isFormInvalid()}
                                        className="doctor-form-button">
                                        <Icon type="save" className="nav-icon" />
                                    </Button>
                                </span>
                            </div>

                            <div className={this.state.edited ? '' : 'custom-hidden'}>
                                <div className="buttons-position">
                                    <Button type="primary"
                                        htmlType="submit"
                                        size="large"
                                        disabled={this.isFormInvalid()}
                                        className="doctor-form-button">
                                        <Icon type="check" className="nav-icon" />
                                    </Button>
                                </div>
                            </div>

                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }


    validateFirstName = (nameText) => {
        if (nameText.length < DOCTOR_FIRST_NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorFirstNameTooShort
            }
        } else if (nameText.length > DOCTOR_FIRST_NAME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorFirstNameTooLong
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };

    validateLastName = (nameText) => {
        if (nameText.length < DOCTOR_LAST_NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorLastNameTooShort
            }
        } else if (nameText.length > DOCTOR_LAST_NAME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorLastNameTooLong
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };

    validateMiddleName = (nameText) => {
        if (nameText.length < DOCTOR_MIDDLE_NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorMiddleNameTooShort
            }
        } else if (nameText.length > DOCTOR_MIDDLE_NAME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorMiddleNameTooLong
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };

    validatePrice = (priceValue) => {
        if (priceValue < 0 || priceValue === undefined || priceValue.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorPriceTooSmall
            }
        } else if (priceValue > MAX_PRICE) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorPriceTooBig
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };

    validatePhone = (phoneNumber) => {
        if (phoneNumber.length !== DOCTOR_PHONE_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadDoctorPhone
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };


    validateDescription = (descriptionText) => {
        if (descriptionText.length > ILLNESS_DESCRIPTION_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadIllnessDescriptionTooLong
            }
        } else if (descriptionText.length < ILLNESS_DESCRIPTION_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadIllnessDescriptionTooShort
            }
        }
        else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };
    
    validateIllnessName = (illnessText) => {
        if (illnessText.length < ILLNESS_NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadIllnessNameTooShort
            }
        } else if (illnessText.length > ILLNESS_NAME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadIllnessNameTooLong
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };

    validateIllnessChanceToDie = (chance) => {
        if (chance < ILLNESS_CHANCE_TO_DIE_MIN || chance === undefined || chance.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadIllnessChanceTooSmall
            }
        } else if (chance > ILLNESS_CHANCE_TO_DIE_MAX) {
            return {
                validateStatus: 'error',
                errorMsg: localizedStrings.alertBadIllnessChanceTooBig
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    };
}

function DoctorIllness(props) {
    console.log(props.illness);
    return (
        <div>
            <FormItem label={localizedStrings.illness + ' ' + (props.illnessNumber + 1)}/>
            <FormItem
                label={localizedStrings.name}
                validateStatus={props.illness.name.validateStatus.validateStatus}
                help={props.illness.name.validateStatus.errorMsg}
                className="doctor-form-row">
                <Input
                    placeholder={localizedStrings.name}
                    size="large"
                    name="name"
                    value={props.illness.name.text}
                    className={"optional-choice"}
                    onChange={(value) => props.handleIllnessNameChange(value, props.illnessNumber)} />
            </FormItem>
            <FormItem
                label={localizedStrings.description}
                validateStatus={props.illness.description.validateStatus.validateStatus}
                help={props.illness.description.validateStatus.errorMsg}
                className="doctor-form-row">
                <Input
                    placeholder={localizedStrings.description}
                    size="large"
                    name="description"
                    value={props.illness.description.text}
                    className={"optional-choice"}
                    onChange={(value) => props.handleIllnessDescriptionChange(value, props.illnessNumber)} />
                {
                    <Icon
                        className="dynamic-delete-button"
                        type="close"
                        onClick={() => props.removeIllness(props.illnessNumber)}
                    />
                }
            </FormItem>
            <FormItem
                label={localizedStrings.chanceToDie}
                validateStatus={props.illness.chanceToDie.validateStatus.validateStatus}
                help={props.illness.chanceToDie.validateStatus.errorMsg}
                className="doctor-form-row">
                <Input
                    type={"number"}
                    placeholder={localizedStrings.chanceToDie}
                    size="large"
                    name="chance"
                    value={props.illness.chanceToDie.value}
                    className={"optional-choice"}
                    onChange={(value) => props.handleIllnessChanceChange(value, props.illnessNumber)} />
            </FormItem>
        </div>
    );
}


export default withRouter(DoctorLogic);