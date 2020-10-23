import { GET_CURRENT_ADDRESS } from './types';
import { ProfileLocation } from '../../models/ProfileLocation';

export const getCurrentAddress = () => async (dispatch) => {
	//const location = JSON.parse(localStorage.getItem('currentLocation'));
	/* 	const storedLocation = localStorage.getItem('currentAddress');
	if (storedLocation) {
		console.log('Store Location: ', storedLocation);
		dispatch({
			type: GET_CURRENT_ADDRESS,
			payload: storedLocation,
		});
		return;
	} */
	console.log('DOES NOT GET HERE IF IN LOCAL STORAGE');
	const location = await getCurrentPosition();
	const { latitude, longitude } = location.coords;
	try {
		const geocoder = new window.google.maps.Geocoder();
		geocoder.geocode({ location: { lat: latitude, lng: longitude } }, (results, status) => {
			const address = results[0];
			if (address) {
				console.log(address);
				//const { formatted_address } = address;
				const profileLocation = new ProfileLocation({
					address: address.formatted_address,
					location: { lat: address.geometry.location.lat(), lng: address.geometry.location.lng() },
				});
				localStorage.setItem('currentAddress', address);
				dispatch({
					type: GET_CURRENT_ADDRESS,
					payload: address,
				});
			}
		});
	} catch (error) {
		console.error(error);
		throw error;
	}
};
export const getCurrentLocation = () => async (dispatch) => {};

const getCurrentPosition = (options = {}) => {
	return new Promise((resolve, reject) => {
		navigator.geolocation.getCurrentPosition(resolve, reject, options);
	});
};
