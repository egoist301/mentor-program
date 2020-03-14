import React from 'react';
import Modal from "antd/es/modal";
import '../Doctor.css'
import { formatDateTime } from "../../util/Helpers";
import BuyConsultationModal from "./BuyConsultationModal";
import { Tag, Icon } from "antd";
import { withRouter } from "react-router-dom";
import Button from "antd/es/button";
import { localizedStrings } from "../../util/Localization";

class ConsultationInfoPopUp extends React.Component {

    state = {
        doctor: this.props.doctor,
        illnessViews: this.props.illnessesViews,
        isAuthenticated: this.props.isAuthenticated,
        currentUser: this.props.currentUser,

        modalVisible: false,
    };


    setModalVisible(modalVisible) {
        this.setState({ modalVisible });
    }

    render() {


        return (

            <span>
                <Button className="title-text"
                    onClick={() => this.setModalVisible(true)}>
                    {this.state.doctor.last_name}
                </Button>
                <div className="price-position">
                    <div className="price">
                        {this.props.doctor.price_per_consultation} <Icon type="dollar" className="nav-icon" />
                    </div>
                </div>

                <Modal
                    width={'700px'}

                    title={
                        <span>
                            <span className="title-text">
                                {this.state.doctor.last_name}
                            </span>
                            <span className="title-text">
                                {"  " + this.state.doctor.first_name}
                            </span>
                            <span className="title-text">
                                {"" + this.state.doctor.middle_name}
                            </span>
                            <span className="doctor-creation-date-popUp">
                                {formatDateTime(this.state.doctor.date_of_birth)}
                            </span>
                        </span>
                    }
                    centered
                    visible={this.state.modalVisible}
                    onOk={() => this.setModalVisible(false)}
                    onCancel={() => this.setModalVisible(false)}>

                    <div className="doctor-content-pop-up">
                        <div className="tag-content">
                            {this.state.illnessViews}
                        </div>
                    </div>


                    <div className="doctor-footer">
                        <div className="footer-position">
                            <span className={
                                this.props.currentUser !== null ?
                                    this.props.currentUser.role === 'ROLE_USER' ? '' : 'custom-hidden' : 'custom-hidden'}>
                                <BuyConsultationModal
                                    handleBuyConsultation={this.props.handleBuyConsultation} />
                            </span>
                            <div className="price-position">
                                <div className="price">
                                    {this.props.doctor.price_per_consultation} <Icon type="dollar" className="nav-icon" />
                                </div>
                            </div>
                        </div>
                    </div>
                </Modal>
            </span>
        );
    }
}

export default withRouter(ConsultationInfoPopUp);