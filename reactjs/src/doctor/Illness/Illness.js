import React, { Component } from 'react';
import './Illness.css'

import { Tag } from 'antd';


class Illness extends Component {


    render() {
        return (
            <div>
                <Tag className="illness-look">
                    <div onClick={() => this.props.searchByName(this.props.illness.name)}>
                            {this.props.illness.name}
                    </div>
                </Tag>
            </div>
        )
    }
}


export default Illness;