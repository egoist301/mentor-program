import React from 'react';
import Button from "antd/es/button";
import Modal from "antd/es/modal";
import {localizedStrings} from "../../util/Localization";
import {deleteDoctor} from "../../util/APIUtils";
import {notification, Icon} from "antd";
import {withRouter} from "react-router"


class DeleteDoctorModal extends React.Component {


    state = {
        visible: false,
        className: this.props.className,
        type: this.props.type,
        size: this.props.size,

        id: this.props.id,
        handleDeleteSubmit: this.handleDeleteSubmit.bind(this),

        redirect: false
    };

    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    handleOk = () => {
        this.setState({
            visible: false,
            redirect: true
        });

        if (this.props.updateDeleteStatus !== undefined) {
            this.props.updateDeleteStatus()
        }
        this.handleDeleteSubmit();
    };

    handleCancel = () => {
        this.setState({
            visible: false,
        });
    };


    handleDeleteSubmit() {
        deleteDoctor(this.state.id)
            .then(() => {
                notification.success({
                    message: localizedStrings.alertAppName,
                    description: localizedStrings.alertDeleteDoctorSuccessfully
                });
                this.props.history.push("/");
            }).catch(error => {
            if (error.status === 401) {
                this.props.handleLogout('/login', 'error', localizedStrings.alertLoggedOut);
            } else if (error.status === 404) {
                notification.error({
                    message: localizedStrings.alertAppName,
                    description: 'doctor does not found!'
                });
            } else {
                notification.error({
                    message: localizedStrings.alertAppName,
                    description: error.message || localizedStrings.alertException
                });
            }
        });
    }

    render() {

        return (
            <span>
                <Button
                    type={this.state.type}
                    size={this.state.size}
                    className={this.state.className}
                    onClick={this.showModal}>
                    <Icon type={"delete"} className="nav-icon"/>
                </Button>
                <Modal
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                    okText={localizedStrings.helpOk}
                    cancelText={localizedStrings.helpCancel}>
                    <p>{localizedStrings.helpDeleteModal}</p>
                </Modal>
            </span>
        );
    }
}

export default withRouter(DeleteDoctorModal);
