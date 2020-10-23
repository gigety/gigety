import React from 'react';
import PropTypes from 'prop-types';
import BaseMap from './BaseMap';
import { Circle, Marker } from '@react-google-maps/api';
import { METERS_PER_MILE } from '../../constants';
import _ from 'lodash';
const ProfilesMap = ({ profiles }) => {
	return (
		<BaseMap>
			{profiles
				? profiles.map((profile) => {
						return profile.profileLocations
							? profile.profileLocations.map((profileLocation) => {
									const center = {
										lat: profileLocation.location.lat,
										lng: profileLocation.location.lng,
									};
									return (
										<>
											<Marker position={center} />
											<Circle
												options={{
													...options,
													center,
													radius: METERS_PER_MILE * profileLocation.radius,
												}}
											/>
										</>
									);
							  })
							: null;
				  })
				: null}
		</BaseMap>
	);
};
const options = {
	strokeColor: '#FF0000',
	strokeOpacity: 0.8,
	strokeWeight: 2,
	fillColor: '#FF0000',
	fillOpacity: 0.35,
};
ProfilesMap.propTypes = {};

export default ProfilesMap;
