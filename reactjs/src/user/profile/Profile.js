import React, {Component} from 'react';
import './Profile.css';


class Profile extends Component {

    constructor(props) {
        super(props);
    }



    render() {
        return (
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            {
                                this.props.currentUser.imageUrl ? (
                                    <img src={this.props.currentUser.imageUrl}/>
                                ) : (
                                    <div className="text-avatar">
                                        <span>{this.props.currentUser.username && this.props.currentUser.username[0]}</span>
                                    </div>
                                )
                            }
                        </div>
                        <div className="profile-name">
                            <h2>{this.props.currentUser.username}</h2>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Profile;
