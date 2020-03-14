import React, {Component} from 'react';
import './NotFound.css';
import {Link} from 'react-router-dom';
import {Button} from 'antd';

class NoPermission extends Component {
    render() {
        return (
            <div className="page-not-found">
                <h1 className="title">
                    403
                </h1>
                <div className="desc">
                    You have no permission. Sorry!
                </div>
                <Link to="/"><Button className="go-back-btn" type="primary" size="large">Go Back</Button></Link>
            </div>
        );
    }
}

export default NoPermission;