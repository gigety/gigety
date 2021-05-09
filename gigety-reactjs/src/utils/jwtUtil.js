import axios from 'axios';

export const setJwtTokenHeader = (token) => {
	if (token) {
		axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
	} else {
		delete axios.defaults.headers.common['Authorization'];
	}
};
export const getUrlParameter = (name, uri) => {
	name = name.replace(/[[]/, '\\[').replace(/[\]]/, '\\]');
	const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
	const results = regex.exec(uri);
	return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};
