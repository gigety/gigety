import axios from 'axios';

export const setJwtTokenHeader = token => {
	console.log(`setting token in axios header: ${token}`);
	if (token) {
		console.log('set auth header');
		axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
	} else {
		console.log('delete Auth header');
		delete axios.defaults.headers.common['Authorization'];
	}
};
export const getUrlParameter = (name, uri) => {
	name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
	const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
	const results = regex.exec(uri);
	return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};
