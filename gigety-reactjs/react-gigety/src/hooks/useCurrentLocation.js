import { useState, useEffect } from 'react';
/**
 * Use CurrentLocation across the app to get users current location.
 * Becareful not to get location to often, . so probably will refactor
 * this to have latest location, mix that with watch location
 * @param {*} options
 */
export const useCurrentLocation = (options = {}) => {
	const [locationError, setLocationError] = useState(null);
	const handleSuccess = (position) => {
		const { latitude, longitude } = position.coords;
		setLocation({ lat: latitude, lng: longitude });
		const loc = {
			lat: latitude,
			lng: longitude,
		};
		localStorage.setItem('currentLocation', JSON.stringify(loc));
	};
	useEffect(() => {
		const currentLocation = localStorage.getItem('currentLocation');
		if (!currentLocation) {
			if (!navigator.geolocation) {
				setLocationError('GeoLocation not available from browser');
				return;
			}
			navigator.geolocation.getCurrentPosition(handleSuccess, handleError, options);
			return function cleanup() {
				console.log(
					'Just demonstrating where any clean would happen for an effect in react. This function will run after each rerender '
				);
			};
		} else {
			const loc = JSON.parse(currentLocation);
			setLocation({ lat: loc.lat, lng: loc.lng });
		}
	}, [options]);
	const [location, setLocation] = useState(null);

	const handleError = (error) => {
		setLocationError(error.message);
	};

	return { location, locationError };
};
