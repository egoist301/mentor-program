import React, {Component} from 'react';
import {signUp} from '../../util/APIUtils';
import './Signup.css';
import {Link} from 'react-router-dom';
import {
    USERNAME_MAX_LENGTH,
    USERNAME_MIN_LENGTH,
    PASSWORD_MAX_LENGTH,
    PASSWORD_MIN_LENGTH
} from '../../constants';

import {Button, Form, Input, notification} from 'antd';
import {localizedStrings} from "../../util/Localization";

const FormItem = Form.Item;

class Signup extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: {
                value: ''
            },
            password: {
                value: ''
            },
            confirmedPassword: {
                value: ''
            }
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        const signupRequest = {
            username: this.state.username.value,
            password: this.state.password.value,
            confirmedPassword: this.state.confirmedPassword.value
        };

        signUp(signupRequest)
            .then(() => {
                notification.success({
                    message: 'Polling App',
                    description: localizedStrings.alertSuccessRegister,
                });
                this.props.history.push("/login");
            }).catch(error => {
            notification.error({
                message: 'Polling App',
                description: error.message || localizedStrings.alertException
            });
        });
    }

    isFormInvalid() {
        return !(this.state.username.validateStatus === 'success' &&
            this.state.password.validateStatus === 'success' &&
            this.state.confirmedPassword.validateStatus === 'success'
        );
    }

    render() {
        return (
            <div className="signup-container">
                <h1 className="page-title">{localizedStrings.signUp}</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem label={localizedStrings.username}
                                  hasFeedback
                                  validateStatus={this.state.username.validateStatus}
                                  help={this.state.username.errorMsg}>
                            <Input
                                size="large"
                                name="username"
                                autoComplete="off"
                                placeholder={localizedStrings.helpForUsername}
                                value={this.state.username.value}
                                onChange={(event) => this.handleInputChange(event, this.validateUserName)}/>
                        </FormItem>
                        <FormItem
                            label={localizedStrings.password}
                            validateStatus={this.state.password.validateStatus}
                            help={this.state.password.errorMsg}>
                            <Input
                                size="large"
                                name="password"
                                type="password"
                                autoComplete="off"
                                placeholder={localizedStrings.helpForPass}
                                value={this.state.password.value}
                                onChange={(event) => this.handleInputChange(event, this.validatePassword)}/>
                        </FormItem>
                        <FormItem
                            label={localizedStrings.confPassword}
                            validateStatus={this.state.confirmedPassword.validateStatus}
                            help={this.state.confirmedPassword.errorMsg}>
                            <Input
                                size="large"
                                name="confirmedPassword"
                                type="password"
                                autoComplete="off"
                                placeholder={localizedStrings.helpForPass}
                                value={this.state.confirmedPassword.value}
                                onChange={(event) => this.handleInputChange(event, this.validateConfirmedPassword)}/>
                        </FormItem>
                        <FormItem>
                            <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    className="signup-form-button"
                                    disabled={this.isFormInvalid()}>{localizedStrings.signUp}</Button>
                            {localizedStrings.alreadyRegister} <Link
                            to="/login">{localizedStrings.signUpFromLoginNow}
                        </Link>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }


    validateUserName = (name) => {
        if (name.length < USERNAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Name is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`
            }
        } else if (name.length > USERNAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Name is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    };

    validatePassword = (password) => {
        if (password.length < PASSWORD_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    };

    validateConfirmedPassword = (confirmedPassword) => {
        const validRes = this.validatePassword(confirmedPassword);
        if (validRes.validateStatus !== 'success' && confirmedPassword !== this.state.password) {
            return {
                validateStatus: 'error',
                errorMsg: validRes.errorMsg + ` Conf not equals pass`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    }

}

export default Signup;