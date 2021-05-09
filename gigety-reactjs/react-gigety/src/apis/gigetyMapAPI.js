import axios from 'axios';
import { GOOGLE_GEOCODE_API_KEY } from '../constants';
const instance = axios.create({
	baseURL: 'https://maps.googleapis.com/maps/api/geocode/json',
});
instance.interceptors.request.use((config) => {
	config.params = {
		// add default ones
		key: GOOGLE_GEOCODE_API_KEY,
		// spread params
		...config.params,
	};
	return config;
});

export default instance;
