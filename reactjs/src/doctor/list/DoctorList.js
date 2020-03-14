import React, { PureComponent } from 'react';
import List from "antd/es/list";
import './DoctorList.css';
import { findAllUserOrder, getAllDoctors } from '../../util/APIUtils';
import Doctor from '../Doctor';
import SearchLogic from "./SearchLogic";
import withRouter from "react-router/lib/withRouter";
import { Pagination } from 'antd';


class DoctorList extends PureComponent {


    constructor(props) {
        super(props);

        this.state = {
            doctors: [],

            pagesCount: 0,
            page: 1,
            size: 12,

            userDoctors: false,

            searchFirstName: '',
            searchByName: '',
            sortBy: sessionStorage.getItem('sortBy') != null ? sessionStorage.getItem('sortBy') : "dateOfBirth",

            isLoading: false,
            currentUserId: null
        };

        this.onSizeChangeHandler = this.onSizeChangeHandler.bind(this);
        this.onPageChangeHandler = this.onPageChangeHandler.bind(this);
        this.searchByName = this.searchByName.bind(this);
        this.loadAllUserDoctors = this.loadAllUserDoctors.bind(this);
        this.loadAllDoctors = this.loadAllDoctors.bind(this);
        this.searchDoctors = this.searchDoctors.bind(this);

        this.loadDoctorsList = this.loadDoctorsList.bind(this);
        this.deleteDoctor = this.deleteDoctor.bind(this);
        this.changeSort = this.changeSort.bind(this);

        this.setRememberedParams();
    }


    setRememberedParams() {
        if (sessionStorage.getItem('size') != null) {
            this.state.size = sessionStorage.getItem('size')
        }
        if (sessionStorage.getItem('page') != null) {
            this.state.page = Math.ceil(sessionStorage.getItem('page'))
        }
        if (sessionStorage.getItem('doctors') != null) {
            this.state.doctors = JSON.parse(sessionStorage.getItem('doctors'));
        }
        if (this.state.doctors.length !== 0) {
            this.state.pagesCount = Math.ceil(Number(this.state.doctors.length / this.state.size));
        }
        if (sessionStorage.getItem('searchFirstName') != null) {
            const searchText = sessionStorage.getItem('searchFirstName');
            this.state.searchFirstName = searchText === 'undefined' ? '' : searchText;
        }
        if (sessionStorage.getItem('userDoctors') != null) {
            this.state.userDoctors = sessionStorage.getItem('userDoctors');
        }
        if (sessionStorage.getItem('searchByName') != null) {
            this.state.searchByName = sessionStorage.getItem('searchByName');
        }

        if (this.props.currentUser != null) {
            sessionStorage.setItem('currentUserId', this.props.currentUser.id);
            this.state.currentUserId = this.props.currentUser.id;
        }
        if (this.props.currentUser == null && sessionStorage.getItem('currentUserId') != null) {
            this.state.currentUserId = sessionStorage.getItem('currentUserId');
        }
    }

    componentDidMount() {
        this.loadMore(this.state.page, this.state.size * 2);
    }


    changeSort(sortBy) {
        this.state.sortBy = sortBy;
        sessionStorage.setItem('sortBy', sortBy);

        this.clearSessionStorage();
        this.setDefaultState();

        this.loadMore(1, this.state.size * 2);
    }

    loadAllDoctors() {
        this.clearSessionStorage();
        sessionStorage.removeItem('searchFirstName');
        sessionStorage.removeItem('searchByName');

        this.setDefaultState();
        this.state.userDoctors = false;
        this.state.searchFirstName = '';
        this.state.searchByName = '';

        this.loadMore(1, this.state.size * 2);
    }

    loadAllUserDoctors(userId) {
        this.clearSessionStorage();
        this.setDefaultState();
        this.state.userDoctors = true;
        this.state.searchFirstName = '';
        this.state.searchByName = '';
        this.state.doctors = []
        this.state.pagesCount = 0
        sessionStorage.setItem('userDoctors', true)
        this.loadMore();
    }


    searchDoctors(firstName) {
        if (firstName === '' || firstName === undefined) {
            this.loadAllDoctors();
        } else {
            this.clearSessionStorage();
            this.setDefaultState();
            this.state.userDoctors = false;
            this.state.searchByName = '';
            this.state.searchFirstName = firstName;
            sessionStorage.setItem('searchFirstName', firstName)
            this.loadMore(1, this.state.size * 2);
        }
    }

    searchByName(illnessName) {

        this.clearSessionStorage();
        this.state.page = 1;
        this.state.pagesCount = 0;
        this.state.doctors = [];
        this.state.userDoctors = false;
        this.state.searchByName = illnessName;

        sessionStorage.setItem('searchByName', illnessName)
        this.loadMore(1, this.state.size * 2);
    }


    loadDoctorsList(page, size) {
        if (this.state.userDoctors) {
            if (this.state.pagesCount <= 1) {
                const searchCriteria = {
                    page: page,
                    size: size
                }
                const promise = findAllUserOrder(this.state.currentUserId, searchCriteria);
                console.log(promise);
                this.extractPromise(promise);
            }
        } else {
            if (page >= this.state.pagesCount) {

                const searchCriteria = {
                    page: page,
                    size: size,

                    sortBy: this.state.sortBy,
                    sortType: 'asc',

                    first_name: this.state.searchFirstName,
                    illness: this.state.searchByName
                };

                const promise = getAllDoctors(searchCriteria);
                if (!promise) {
                    return;
                }
                this.extractPromise(promise);
            }

        }

    }


    extractPromise(promise) {

        this.setState({
            isLoading: true
        });

        promise
            .then(response => {
                const prevDoctors = this.state.doctors.slice();
                const doctorsCount = response.length;
                const currentPageSize = this.state.size;
                const prevPagesCount = this.state.pagesCount;
                const newPagesCount = Math.ceil(Number(doctorsCount / currentPageSize));

                this.setState({
                    doctors: prevDoctors.concat(response),
                    pagesCount: prevPagesCount + newPagesCount,

                    isLoading: false
                });
                if (this.state.page !== 1 && this.state.pageCount === this.state.page) {
                    this.state.page = this.state.page - 1;
                }
                sessionStorage.setItem('pagesCount')
            }).catch(() => {
                this.setState({
                    isLoading: false
                });

            });

    }


    loadMore(page = this.state.page, size = this.state.size) {
        this.loadDoctorsList(this.state.pagesCount + 1, size);
    }


    render() {

        const doctorsViews = [];
        this.state.doctors.forEach((doctor) => {
            doctorsViews.push(
                <Doctor
                    changeDoctorStatusAfterOrder={this.changeDoctorStatusAfterOrder}
                    deleteDoctor={this.deleteDoctor}
                    history={this.props.history}
                    currentUser={this.props.currentUser}
                    isAuthenticated={this.props.isAuthenticated}
                    key={doctor.id}
                    doctor={doctor}
                    searchByName={this.searchByName} />
            )
        });


        return (

            <div className="doctors-container">
                <div className="doctors-container-header">
                    <SearchLogic
                        userDoctors={this.state.userDoctors}
                        searchText={this.state.searchFirstName}
                        currentUser={this.props.currentUser}
                        isAuthenticated={this.props.isAuthenticated}
                        loadAllUserDoctors={this.loadAllUserDoctors}
                        loadAllDoctors={this.loadAllDoctors}
                        searchDoctors={this.searchDoctors}
                        sortBy={this.state.sortBy}
                        changeSort={this.changeSort} />
                </div>

                <div className="doctors-content">
                    <List
                        grid={{
                            gutter: 1,
                            xs: 1,
                            sm: 1,
                            md: 1,
                            lg: 2,
                            xl: 3,//column
                            xxl: 3,
                        }}

                        pagination={{
                            loading: this.state.isLoading,
                            showSizeChanger: true,
                            defaultCurrent: Number(this.state.page),
                            defaultPageSize: Number(this.state.size),
                            pageSizeOptions: ["12", "51", "99"],
                            position: "bottom",
                            onShowSizeChange: this.onSizeChangeHandler,
                            onChange: this.onPageChangeHandler,
                        }}

                        dataSource={doctorsViews}

                        renderItem={item => (
                            <List.Item>
                                {item}
                            </List.Item>
                        )}
                    />
                </div>
            </div>
        );
    }

    onSizeChangeHandler(page, size) {
        const prevCertCount = this.state.doctors.length;
        let newPageCount = Math.ceil((prevCertCount / size));
        let newPage;

        if (page !== 1 && page >= newPageCount) {
            newPage = page - 1;
        }

        if (newPageCount === 0) {
            newPageCount = 1;
        }

        this.setState({
            page: newPage,
            size: size,
            pagesCount: newPageCount
        })
    }

    onPageChangeHandler(pageNumber) {
        this.state.page = pageNumber;
        if (pageNumber >= this.state.pagesCount) {
            this.loadMore();
        }
        sessionStorage.setItem("page", pageNumber);
        sessionStorage.setItem('doctors', JSON.stringify(this.state.doctors));
    }


    clearSessionStorage() {
        sessionStorage.removeItem('size');
        sessionStorage.removeItem('page');
        sessionStorage.removeItem('doctors');
        sessionStorage.removeItem('userDoctors');
    }

    setDefaultState() {
        this.state.page = 1;
        this.state.pagesCount = 0;
        this.state.size = 24;
        this.state.doctors = [];
    }

    deleteDoctor(doctor) {
        for (let i = 0; i < this.state.doctors.length; i++) {
            if (this.state.doctors[i].id === doctor.id) {
                this.state.doctors.splice(i, 1);
            }
        }
    }

}


export default withRouter(DoctorList);

