import { ACCESS_TOKEN, API_BASE_URL } from '../constants';

const request = (options) => {

    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = { headers: headers };
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response => {
            if (!response.ok) {
                throw response
            }
            if (response.status !== 204) {
                return response.json()
            }
        }).then(json => {
            return json;
        });
};


export function getAllDoctors(searchCriteria) {
    const url = API_BASE_URL +
        "/doctors?" +
        "page=" + searchCriteria.page +
        "&size=" + searchCriteria.size +
        "&sort=" + searchCriteria.sortBy +
        "&order=" + searchCriteria.sortType +
        "&first_name=" + searchCriteria.first_name +
        "&illness=" + searchCriteria.illness;
    return request({
        url: url,
        method: 'GET'
    });

}

export function createDoctor(doctors) {
    return request({
        url: API_BASE_URL + "/doctors",
        method: 'POST',
        body: JSON.stringify(doctors)
    });
}


export function editDoctor(doctors) {
    return request({
        url: API_BASE_URL + "/doctors/" + Number(doctors.id),
        method: 'PUT',
        body: JSON.stringify(doctors)
    });
}

export function deleteDoctor(doctorId) {
    const uri = API_BASE_URL + "/doctors/" + Number(doctorId)
    return request({
        url: uri,
        method: 'DELETE'
    });
}


export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signUp(signupRequest) {
    return request({
        url: API_BASE_URL + "/users/signUp",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function getCurrentUser() {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/users/me",
        method: 'GET'
    });
}

export function buyDoctor(order) {
    return request({
        url: API_BASE_URL + "/orders",
        method: 'POST',
        body: JSON.stringify(order)
    });
}

export function findAllUserOrder(userId, searchCriteria) {
    const url = API_BASE_URL + "/users/orders?page=" + searchCriteria.page
    +"&size=" + searchCriteria.size;
    return request({
        url: url,
        method: 'GET'
    })
}