import React from 'react';
import { Provider } from 'react-redux';
import jwt_decode from 'jwt-decode';
import { SET_CURRENT_USR } from 'redux/actions/types';
import { setJwtTokenHeader, getUrlParameter } from 'utils/jwtUtil';
import { logout, loginAction } from 'redux/actions/auth';
import { store } from 'redux/store';

const jwtToken = localStorage.getItem('jwtToken');

if (jwtToken) {
	setJwtTokenHeader(jwtToken);
	const decodedToken = jwt_decode(jwtToken);
	console.log(`decoded jwt: ${decodedToken}`);
	store.dispatch({
		type: SET_CURRENT_USR,
		payload: decodedToken,
	});
	const now = Date.now() / 1000;
	if (decodedToken.exp < now) {
		store.dispatch(logout());
		window.location.href = '/';
	}
} else {
	const uri = window.location.search;

	console.log(`uri: ${uri}`);
	const token = getUrlParameter('t', uri);
	console.log(`token: ${token}`);
	const error = getUrlParameter('error', uri);
	console.log(`error: ${error}`);
	if (token) {
		store.dispatch(loginAction(token));
		window.location.href = '/';
	}
}

export default ({ children }) => <Provider store={store}>{children}</Provider>;
