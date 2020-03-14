import React, {Component} from 'react';
import {login} from '../../util/APIUtils';
import './Login.css';
import {Link} from 'react-router-dom';
import {ACCESS_TOKEN, GITHUB_AUTH_URL, GOOGLE_AUTH_URL} from '../../constants';

import {Button, Form, Icon, Input, notification} from 'antd';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';
import welcomePicture from '../../img/welcome.jpg';
import {localizedStrings} from "../../util/Localization";

const FormItem = Form.Item;


class Login extends Component {


    componentDidMount() {
        if (this.props.location.state && this.props.location.state.error) {
            setTimeout(() => {
                notification.error({
                    message: localizedStrings.alertAppName,
                    description: this.props.location.state.error,
                    duration: 5000
                });
                this.props.history.replace({
                    pathname: this.props.location.pathname,
                    state: {}
                });
            }, 100);
        }
    }


    render() {
        const AntWrappedLoginForm = Form.create()(LoginForm);
        return (
            <div>
                <h1 className="page-title">
                    <div className="page-title-text">
                        {localizedStrings.shopName}
                    </div>
                </h1>
                <div className="login-container">
                    <div className="login-content">
                        <AntWrappedLoginForm onLogin={this.props.onLogin}/>
                    </div>
                </div>

                <div className="welcome-picture">
                    <img src={welcomePicture} width="800px" height="500px " alt={"welcome"}/>
                </div>
            </div>
        );
    }
}

class LoginForm extends Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const loginRequest = Object.assign({}, values);
                login(loginRequest)
                    .then(response => {
                        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                        console.log(response.accessToken)
                        this.props.onLogin();
                    }).catch(() => {
                    notification.error({
                        message: localizedStrings.alertAppName,
                        description: localizedStrings.alertWrongUsernameOrPassword
                    });
                });
            }
        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    <SocialLogin/>
                    {
                        getFieldDecorator('username', {
                            rules: [{required: true, message: localizedStrings.alertBadUsername}],
                        })(
                            <Input
                                prefix={<Icon type="user"/>}
                                size="large"
                                name="username"
                                placeholder={localizedStrings.username}/>
                        )
                    }
                </FormItem>
                <FormItem>
                    {
                        getFieldDecorator('password', {
                            rules: [{required: true, message: localizedStrings.alertBadPassword}],
                        })(
                            <Input
                                prefix={<Icon type="lock"/>}
                                size="large"
                                name="password"
                                type="password"
                                placeholder={localizedStrings.password}/>
                        )
                    }
                </FormItem>
                <FormItem>
                    <Button type="primary" htmlType="submit" size="large" className="login-form-button">
                        {localizedStrings.login}
                    </Button>
                    {localizedStrings.or}
                    <Link to="/signUp">
                        {localizedStrings.loginFormRegisterNow}
                    </Link>
                </FormItem>
            </Form>
        );
    }
}


class SocialLogin extends Component {
    render() {
        return (
            <FormItem>
                <FormItem>
                    <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
                        <img src={googleLogo} alt="Google"/>
                        {localizedStrings.logInWithGoogle}
                    </a>
                </FormItem>
                <FormItem>
                    <a className="btn btn-block social-btn github" href={GITHUB_AUTH_URL}>
                        <img src={githubLogo} alt="Github"/>
                        {localizedStrings.logInWithGithub}
                    </a>
                </FormItem>
            </FormItem>
        );
    }
}


export default Login;