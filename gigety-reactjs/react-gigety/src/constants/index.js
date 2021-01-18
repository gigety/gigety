export const GIGETY_API_URL = process.env.REACT_APP_GIGETY_URL + '/api';
export const GIGETY_MESSENGER_URL = process.env.REACT_APP_GIGETY_URL + '/messenger';
export const OAUTH2_REDIRECT_URI = process.env.REACT_APP_GIGETY_URL;
export const FACEBOOK_AUTH_URL = GIGETY_API_URL + '/oauth2/authorize/facebook?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GIGETY_AUTH_URL = GIGETY_API_URL + '/oauth2/authorize/samo?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GOOGLE_AUTH_URL = GIGETY_API_URL + '/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GOOGLE_GEOCODE_API_KEY = process.env.REACT_APP_GIGETY_GOOGLE_GEOCODE_API_KEY;
export const GOOGLE_MAP_LIBRARIES = ['places', 'drawing'];
export const METERS_PER_MILE = 1609.344;
