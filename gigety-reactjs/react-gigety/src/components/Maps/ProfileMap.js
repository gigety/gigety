import React, { Fragment, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Circle, Marker } from '@react-google-maps/api';
import { useCurrentLocation } from '../../hooks/useCurrentLocation';
import BaseMap from './BaseMap';
import { METERS_PER_MILE } from '../../constants';
function ProfileMap({ profileLocations }) {
	const { currentLocation, error } = useCurrentLocation(null);
	const zoomTo = profileLocations
		? { lat: profileLocations[0].location.lat, lng: profileLocations[0].location.lng }
		: currentLocation;
	return (
		<BaseMap center={zoomTo}>
			{profileLocations
				? profileLocations.map((profileLocation) => {
						const center = { lat: profileLocation.location.lat, lng: profileLocation.location.lng };
						return (
							<Fragment key={profileLocation.id}>
								<Marker position={center} />
								<Circle
									options={{ ...options, center, radius: METERS_PER_MILE * profileLocation.radius }}
								/>
							</Fragment>
						);
				  })
				: null}
		</BaseMap>
	);
}
const options = {
	strokeColor: '#FF0000',
	strokeOpacity: 0.8,
	strokeWeight: 2,
	fillColor: '#FF0000',
	fillOpacity: 0.35,
};
ProfileMap.defaultProps = {
	profileLocations: null,
};
ProfileMap.propTypes = {
	profileLocations: PropTypes.array,
};

export default ProfileMap;
