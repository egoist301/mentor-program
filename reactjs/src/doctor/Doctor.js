import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import './Doctor.css';
import {formatDateTime} from '../util/Helpers';
import {Button, notification, Tag, Icon,} from 'antd';
import IllnessList from "./Illness/IllnessList";
import {localizedStrings} from "../util/Localization";
import BuyConsultationModal from "./modal/BuyConsultationModal";
import DeleteDoctorModal from "./modal/DeleteDoctorModal";
import ConsultationInfoPopUp from "./modal/ConsultationInfoPopUp";
import {buyDoctor} from "../util/APIUtils";
import Redirect from "react-router/lib/Redirect";


class Doctor extends Component {

    constructor(props) {
        super(props);

        this.handleBuyDoctor = this.handleBuyDoctor.bind(this);
        this.updateDeleteStatus = this.updateDeleteStatus.bind(this);
    }

    updateDeleteStatus() {
        this.props.deleteDoctor(this.props.doctor);
    }

    render() {

        const illnessesViews =
            <IllnessList
                illnesses={this.props.doctor.illnesses}
                searchByName={this.props.searchByName}
            />;
            const illnessesViewsForCard = <IllnessList
            illnesses={this.props.doctor.illnesses.slice(0,5)}
            searchByName={this.props.searchByName}
        />;
        return (
            <div className="doctor-card" style={{with: '50%'}}>

                <div className="doctor-header">
                    <div className="title-info">
                        <ConsultationInfoPopUp
                            doctor={this.props.doctor}
                            illnessesViews={illnessesViews}
                            isAuthenticated={this.props.isAuthenticated}
                            currentUser={this.props.currentUser}
                            handleBuyDoctor={this.handleBuyDoctor}
                            handleDeleteSubmit={this.handleDeleteSubmit}/>
                    </div>
                </div>

                <div className="doctor-content">
                    <div className="tag-content">
                        {illnessesViewsForCard}
                    </div>
                </div>

                <div className="doctor-footer">
                    <div className="footer-position">

                    <span className={
                        this.props.currentUser !== null ?
                            this.props.currentUser.role === 'ROLE_ADMIN' ? '' : 'custom-hidden' : 'custom-hidden'}>


                           <Link to={{
                               pathname: "/edit",
                               doctor: this.props.doctor
                           }}>
                                    <Button className="button">
                                        <Icon type={"edit"} className="nav-icon"/>
                                    </Button>
                               </Link>

                          <DeleteDoctorModal className={'button'}
                                                  type={''}
                                                  size={''}
                                                  id={this.props.doctor.id}
                                                  history={this.props.history}
                                                  updateDeleteStatus={this.updateDeleteStatus}/>
                    </span>

                        <span
                            className={this.props.isAuthenticated && this.props.currentUser.role === 'ROLE_USER' ? '' : 'custom-hidden'}>
                                <BuyConsultationModal
                                    handleBuyDoctor={this.handleBuyDoctor}/>
                         </span>
                        
                    </div>
                </div>
            </div>

        );
    }


    handleBuyDoctor() {
        const order = {
            doctors: [this.props.doctor.id]
        };
        console.log(order);
        buyDoctor(order)
            .then(() => {
                notification.success({
                    message: localizedStrings.alertAppName,
                    description: "order created"
                });
                return <Redirect to='/'/>;
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

        this.setState({
            available: false
        });

    }
}


export default Doctor;
