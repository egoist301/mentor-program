import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import './AppHeader.css';
import pollIcon from '../img/poll.svg';
import { Avatar, Dropdown, Icon, Layout, Menu } from 'antd';
import { getAvatarColor } from "../util/Colors";

import { localizedStrings } from "../util/Localization";
import Button from "antd/es/button";


const Header = Layout.Header;

class AppHeader extends Component {
    constructor(props) {
        super(props);
        this.handleMenuClick = this.handleMenuClick.bind(this);
    }

    handleMenuClick({ key }) {
        if (key === "logout") {
            this.props.onLogout();
        }
    }

    render() {
        let menuItems;
        if (this.props.currentUser) {
            menuItems = [
                <Menu.Item key="/add">
                    <span className={this.props.currentUser.role === 'ROLE_ADMIN' ? '' : 'custom-hidden'}>
                        <Link to="/add">
                            <Icon type="plus" className="nav-icon" />
                        </Link>
                    </span>
                </Menu.Item>,

                <Menu.Item key="/">
                    <Link to="/">
                        <Icon type="home" className="nav-icon" style={{color: '#559fff'}} />
                    </Link>
                </Menu.Item>,

                <Menu.Item key="/profile" className="profile-menu">
                    <ProfileDropdownMenu
                        currentUser={this.props.currentUser}
                        handleMenuClick={this.handleMenuClick} />
                </Menu.Item>

            ];
        } else {
            menuItems = [
                <Menu.Item key="/">
                    <Link to="/">
                        <Icon type="home" className="nav-icon" />
                    </Link>
                </Menu.Item>,
                <Menu.Item key="/login">
                    <Link to="/login">
                        {localizedStrings.login}
                    </Link>
                </Menu.Item>,
                <Menu.Item key="/sign-up">
                    <Link to="/sign-up">
                        {localizedStrings.signUp}
                    </Link>
                </Menu.Item>
            ];
        }


        return (

            <Header className="app-header">
                <div className="container">
                    <span className="app-language-position">
                        <Button className="app-language app-language-style"
                            onClick={() => this.props.handleLanguageChange('ru')}>
                            RU
                        </Button>
                        <Button className="app-language app-language-style"
                            onClick={() => this.props.handleLanguageChange('en')}>
                            EN
                        </Button>
                    </span>


                    <Menu
                        className="app-menu"
                        mode="horizontal"
                        selectedKeys={[this.props.location.pathname]}
                        style={{ lineHeight: '64px', background: 'BLACK' }}>
                        {menuItems}
                    </Menu>

                </div>

            </Header>
        );
    }
}

function ProfileDropdownMenu(props) {
    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            <Menu.Item key="user-info" className="dropdown-item" disabled>
                <div className="user-full-name-info">
                    {props.currentUser.username}
                </div>
            </Menu.Item>
            <Menu.Divider />
            <Menu.Item key="profile" className="dropdown-item">
                <Link to="/profile">
                    {localizedStrings.profile}
                </Link>
            </Menu.Item>
            <Menu.Item key="logout" className="dropdown-item">
                {localizedStrings.logout}
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('profile-menu')[0]}>
            <a className="ant-dropdown-link">
                <Icon type="user" className="nav-icon" style={{ marginRight: 0, color: '#559fff' }} /> <Icon type="down" />
            </a>
        </Dropdown>
    );
}


export default withRouter(AppHeader);