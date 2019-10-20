import React from 'react';
import { Provider } from 'react-redux';
import jwt_decode from 'jwt-decode';
import { SET_CURRENT_USR } from 'actions/types';
import { setJwtTokenHeader } from 'utils/jwtUtil';
import { logout } from 'actions/auth';
import { store } from 'utils/store';

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
}

export default ({ children }) => {
	return <Provider store={store}>{children}</Provider>;
};
