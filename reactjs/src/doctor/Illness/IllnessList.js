import React, {PureComponent} from 'react';
import Illness from "./Illness";
import './IllnessList.css'

class IllnessList extends PureComponent {


    render() {
        const illnesses = this.props.illnesses.map(illness =>
            <li key={illness.id} className="illnesses-list_li">
                <Illness
                    illness={illness}
                    searchByName={this.props.searchByName}/>
            </li>
        );

        return (
            <ul className="ul">
                {illnesses}
            </ul>
        )

    }
}


export default IllnessList;