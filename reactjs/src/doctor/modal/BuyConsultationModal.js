import React from 'react';
import Button from "antd/es/button";
import Modal from "antd/es/modal";
import {Icon} from "antd";
import {localizedStrings} from "../../util/Localization";


class BuyConsultationModal extends React.Component {

    state = {visible: false};

    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    handleOk = () => {
        this.setState({
            visible: false,
        });
        this.props.handleBuyDoctor();
    };

    handleCancel = () => {
        this.setState({
            visible: false,
        });
    };

    render() {
        return (
            <span>
                <Button className="button"
                        disabled={this.props.isAuthenticated}
                        onClick={this.showModal}>
                    <Icon type="shopping" className="nav-icon"/>
                </Button>
                <Modal
                    visible={this.state.visible}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                    okText={localizedStrings.helpOk}
                    cancelText={localizedStrings.helpCancel}>
                    <p>{localizedStrings.helpOrderDoctor}</p>
                </Modal>
            </span>
        );
    }
}

export default BuyConsultationModal;