import React, { Component } from 'react';
import { localizedStrings } from "../../util/Localization";
import { Button, Form, Input, Select, Icon } from "antd";
import FormItem from "antd/es/form/FormItem";
import './DoctorList.css'

const { Option } = Select;

class SearchLogic extends Component {


    constructor(props) {
        super(props);

        this.state = {
            defaultChoice: this.props.userDoctors === 'true' ? 'MyCert' : 'All',
            searchLastName: {
                value: this.props.searchLastName
            },
            searchIllnessName: {
                value: this.props.searchIllnessName
            },
            sortType: this.props.sortType,
            sortBy: this.props.sortBy
        };
        this.handleSubmitSearch = this.handleSubmitSearch.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.handleSubmitSort = this.handleSubmitSort.bind(this);
        this.handleSortSelectChange = this.handleSortSelectChange.bind(this);
        this.handleSortSelectChangeType = this.handleSortSelectChangeType.bind(this);
    }

    handleInputChange(event) {

        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue
            }
        });
    }


    handleSubmitSearch(event) {
        event.preventDefault();
        this.props.searchDoctors(this.state.searchLastName.value, this.state.searchIllnessName.value);
    }


    handleSelectChange(value) {
        this.setState({
            searchLastName: '',
            searchIllnessName: '',
            defaultChoice: value
        });
        if (value === 'All') {
            this.props.loadAllDoctors();
        } else if (this.props.currentUser != null) {
            this.props.loadAllUserDoctors(this.props.currentUser.id);
        }
    }


    render() {
        return (
            <div>
                <div className="doctors-header-text">
                    {localizedStrings.doctors}
                </div>
                <div className="search">
                    <span className={this.props.isAuthenticated && this.props.currentUser.role === 'ROLE_USER' ? '' : 'custom-hidden'}>
                        <div className="search-button">
                            <Select
                                defaultValue={this.state.defaultChoice}
                                style={{ width: 170 }}
                                onSelect={this.handleSelectChange}>
                                <Option value="All">
                                    {localizedStrings.selectShowAllDoctors}
                                </Option>
                                <Option value="MyCert"
                                    disabled={!this.props.isAuthenticated}>
                                    {localizedStrings.selectShowMyOrders}
                                </Option>
                            </Select>
                        </div>
                    </span>
                    <span className={this.state.defaultChoice === 'MyCert' ? 'custom-hidden' : ''}>

                        <Form onSubmit={this.handleSubmitSearch}>
                            <div className="search-line-position">
                                <FormItem hasFeedback>
                                    <Input
                                        name="searchLastName"
                                        type="text"
                                        autoComplete="off"
                                        placeholder={localizedStrings.helpSearchLastName}
                                        value={this.state.searchLastName.value}
                                        disabled={false}
                                        onChange={(event) => this.handleInputChange(event)} />
                                </FormItem>
                            </div>
                            <div className="search-line-position">
                                <FormItem hasFeedback>
                                    <Input
                                        name="searchIllnessName"
                                        type="text"
                                        autoComplete="off"
                                        placeholder={localizedStrings.helpSearchIllnessName}
                                        value={this.state.searchIllnessName.value}
                                        disabled={false}
                                        onChange={(event) => this.handleInputChange(event)} />
                                </FormItem>
                            </div>
                            <div className="search-button">
                                <Button type="primary"
                                    htmlType="submit"
                                    size="large">
                                    <Icon type="search" className="nav-icon" />
                                </Button>
                            </div>

                        </Form>

                        <div>
                            <div className="search-button">
                                <div>
                                    {localizedStrings.sort}
                                </div>
                                <Select
                                    defaultValue={this.state.sortBy}
                                    style={{ width: 170 }}
                                    onSelect={this.handleSortSelectChange}>

                                    <Option value="lastName">
                                        {localizedStrings.sortByLastName}
                                    </Option>
                                    <Option value="dateOfBirth">
                                        {localizedStrings.sortByDateOfBirth}
                                    </Option>
                                </Select>
                            </div>
                        </div>

                        <div>
                            <div className="search-button">
                                <Select
                                    defaultValue={this.state.sortType}
                                    style={{ width: 140 }}
                                    onSelect={this.handleSortSelectChangeType}>

                                    <Option value="asc">
                                        {localizedStrings.asc}
                                    </Option>
                                    <Option value="desc">
                                        {localizedStrings.desc}
                                    </Option>
                                </Select>
                            </div>
                        </div>
                    </span>
                </div>
            </div>
        );
    }


    handleSubmitSort(event) {
        event.preventDefault();
    }

    handleSortSelectChange(value) {
        this.setState({
            sortBy: value
        });
        this.props.changeSort(value);
    }

    handleSortSelectChangeType(value) {
        this.setState({
            sortType: value
        });
        this.props.changeSortType(value);
    }
}


export default SearchLogic;
