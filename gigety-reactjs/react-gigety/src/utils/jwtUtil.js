import axios from 'axios';

export const setJwtTokenHeader = token => {
	console.log(`toke: ${token}`);
	if (token) {
		console.log('set auth header');
		axios.defaults.headers.common['Authorization'] = token;
	} else {
		console.log('delete Auth header');
		delete axios.defaults.headers.common['Authorization'];
	}
};
