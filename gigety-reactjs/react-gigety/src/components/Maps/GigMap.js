import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Circle, Marker } from '@react-google-maps/api';
import { useCurrentLocation } from '../../hooks/useCurrentLocation';
import BaseMap from './BaseMap';
import { METERS_PER_MILE } from '../../constants';
function GigMap({ gigLocations }) {
	const { currentLocation, error } = useCurrentLocation(null);
	const zoomTo = gigLocations
		? { lat: gigLocations[0].location.lat, lng: gigLocations[0].location.lng }
		: currentLocation;
	return (
		<BaseMap center={zoomTo}>
			{gigLocations
				? gigLocations.map((gigLocation) => {
						const center = { lat: gigLocation.location.lat, lng: gigLocation.location.lng };
						return (
							<>
								<Marker position={center} />
								<Circle
									options={{ ...options, center, radius: METERS_PER_MILE * gigLocation.radius }}
								/>
							</>
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
GigMap.defaultProps = {
	gigLocations: null,
};
GigMap.propTypes = {
	gigLocations: PropTypes.array,
};

export default GigMap;
