import React, { useRef } from 'react';
import PropTypes from 'prop-types';
import { GoogleMap, useLoadScript } from '@react-google-maps/api';
import { GOOGLE_GEOCODE_API_KEY } from '../../constants';
import { Dimmer, Loader } from 'semantic-ui-react';

function BaseMap({ googleMapsApiKey, containerStyle, zoom, children, center }) {
	const onMapLoad = (map) => {
		mapRef.current = map;
	};
	const { isLoaded, loadError } = useLoadScript({
		googleMapsApiKey: googleMapsApiKey,
	});
	const mapRef = useRef();
	const options = {
		zoomControlOptions: {
			// ,
		},
	};

	const renderMap = (
		<GoogleMap
			className="MapHeadStyle"
			mapContainerStyle={containerStyle}
			center={center}
			zoom={zoom}
			options={options}
			onLoad={onMapLoad}
		>
			{children}
		</GoogleMap>
	);

	if (loadError) {
		return <div>Map cannot be loaded right now, sorry.</div>;
	}

	return isLoaded ? (
		renderMap
	) : (
		<div style={defaultContainerStyle}>
			<Dimmer active>
				<Loader size="large" inline="centered">
					Loading...
				</Loader>
			</Dimmer>
		</div>
	);
}
const defaultContainerStyle = {
	position: 'relative',
	width: '100%',
	height: '400px',
};
BaseMap.defaultProps = {
	googleMapsApiKey: GOOGLE_GEOCODE_API_KEY,
	containerStyle: defaultContainerStyle,
	zoom: 10,
	center: {
		lat: 32,
		lng: -81,
	},
};
BaseMap.propTypes = {
	googleMapsApiKey: PropTypes.string,
	containerStyle: PropTypes.object,
	zoom: PropTypes.number,
	center: PropTypes.shape({
		lat: PropTypes.number.isRequired,
		lng: PropTypes.number.isRequired,
	}),
};

export default BaseMap;
