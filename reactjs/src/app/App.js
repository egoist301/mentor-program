import React, {Component} from 'react';
import './App.css';
import {Route, Switch, withRouter, Redirect} from 'react-router-dom';

import {getCurrentUser} from '../util/APIUtils';
import {ACCESS_TOKEN, USER_ID} from '../constants';

import Login from '../user/login/Login';
import SignUp from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import PrivateRoute from '../common/PrivateRoute';

import {Layout, notification} from 'antd';
import OAuth2RedirectHandler from "../user/oauth2/OAuth2RedirectHandler";
import DoctorList from "../doctor/list/DoctorList";
import AppFooter from "../common/AppFooter";
import EditDoctor from "../doctor/EditDoctor";


import {localizedStrings} from "../util/Localization";
import PrivateAdminRoute from "../common/PrivateAdminRoute";
import AddDoctor from "../doctor/AddDoctor";
import NoPermission from '../common/NoPermission';


const {Content} = Layout;

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            isAuthenticated: false,
            isLoading: false,

            language: localStorage.getItem("language") === null ? 'en' : localStorage.getItem("language")
        };

        this.handleLogout = this.handleLogout.bind(this);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
        this.handleLanguageChange = this.handleLanguageChange.bind(this);
        this.handleNoPermissions = this.handleNoPermissions.bind(this);

        notification.config({
            placement: 'topRight',
            top: 70,
            duration: 3,
        });
    }


    handleLanguageChange(lang) {
        localStorage.setItem("language", lang);
        this.setState(() => ({
            language: lang
        }))
    }


    loadCurrentUser() {
        this.setState({
            isLoading: true
        });
        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
            }).catch(() => {
            this.setState({
                isLoading: false
            });
        });
    }

    componentDidMount() {
        this.loadCurrentUser();
    }

    handleLogout(redirectTo = "/login", notificationType = "success", description = localizedStrings.alertSuccessLogOut) {
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.removeItem(USER_ID);
        sessionStorage.clear();

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push(redirectTo);

        notification[notificationType]({
            message: localizedStrings.alertAppName,
            description: description,
        });
    }

    handleLogin() {
        notification.success({
            message: localizedStrings.alertAppName,
            description: localizedStrings.alertSuccessLogin,
        });
        this.loadCurrentUser();
        this.props.history.push("/");
    }

    handleNoPermissions() {
        notification.error({
            message: localizedStrings.alertAppName,
            description: localizedStrings.alertNoPermission
        });
        this.loadCurrentUser();
        this.props.history.push("/");
    }

    render() {

        if (this.state.isLoading) {
            return <LoadingIndicator/>
        }

        localizedStrings.setLanguage(this.state.language);

        return (
            <Layout className="app-container">

                <AppHeader isAuthenticated={this.state.isAuthenticated}
                           currentUser={this.state.currentUser}
                           onLogout={this.handleLogout}
                           handleLanguageChange={this.handleLanguageChange}/>

                <Content className="app-content">
                    <div className="container">
                        <Switch>

                            <Route exact path="/"
                                   render={(props) =>
                                       <DoctorList isAuthenticated={this.state.isAuthenticated}
                                                         currentUser={this.state.currentUser}
                                                         handleLogout={this.handleLogout}
                                                         {...props} />}/>

                            <PrivateAdminRoute path="/edit"
                                               route={this.props.route}
                                               authenticated={this.state.isAuthenticated}
                                               currentUser={this.state.currentUser}
                                               component={EditDoctor}
                                               handleLogout={this.handleLogout}/>

                            <PrivateAdminRoute path="/add"
                                               authenticated={this.state.isAuthenticated}
                                               currentUser={this.state.currentUser}
                                               component={AddDoctor}
                                               handleLogout={this.handleLogout}/>

                            <Route path="/login"
                                   render={(props) =>!this.state.isAuthenticated?
                                       <Login onLogin={this.handleLogin}
                                              {...props} />:<Redirect to="/"/>}/>

                            <Route path="/sign-up"
                                   render={(props) => !this.state.isAuthenticated?
                                       <SignUp
                                           authenticated={this.state.isAuthenticated}
                                           {...props} />:<Redirect to="/"/>}/>

                            <Route path="/oauth2/redirect"
                                   render={(props) =>
                                       <OAuth2RedirectHandler onLogin={this.handleLogin}
                                                              {...props} />}/>

                            <PrivateRoute path="/profile"
                                          authenticated={this.state.isAuthenticated}
                                          currentUser={this.state.currentUser}
                                          component={Profile}/>


                            <Route component={NotFound}/>
                        </Switch>
                    </div>
                </Content>

                <AppFooter/>
            </Layout>

        );
    }
}


export default withRouter(App);
