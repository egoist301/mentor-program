export const API_BASE_URL = 'http://localhost:8080';
export const ACCESS_TOKEN = 'accessToken';
export const USER_ID = 'id';

export const OAUTH2_REDIRECT_URI = 'http://localhost:3000/oauth2/redirect';

export const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL = API_BASE_URL + '/oauth2/authorize/github?redirect_uri=' + OAUTH2_REDIRECT_URI;


export const USERNAME_MIN_LENGTH = 4;
export const USERNAME_MAX_LENGTH = 30;
export const PASSWORD_MIN_LENGTH = 4;
export const PASSWORD_MAX_LENGTH = 30;


export const MAX_ILLNESS = 6;

export const DOCTOR_PHONE_LENGTH = 7;
export const MIN_PRICE = 1;
export const MAX_PRICE = 1000000;
export const DOCTOR_FIRST_NAME_MIN_LENGTH = 2;
export const DOCTOR_FIRST_NAME_MAX_LENGTH = 16;
export const DOCTOR_LAST_NAME_MIN_LENGTH = 2;
export const DOCTOR_LAST_NAME_MAX_LENGTH = 16;
export const DOCTOR_MIDDLE_NAME_MIN_LENGTH = 4;
export const DOCTOR_MIDDLE_NAME_MAX_LENGTH = 16;

export const ILLNESS_DESCRIPTION_MIN_LENGTH = 4;
export const ILLNESS_DESCRIPTION_MAX_LENGTH = 250;
export const ILLNESS_NAME_MIN_LENGTH = 4;
export const ILLNESS_NAME_MAX_LENGTH = 30;
export const ILLNESS_CHANCE_TO_DIE_MIN = 0;
export const ILLNESS_CHANCE_TO_DIE_MAX = 100;
