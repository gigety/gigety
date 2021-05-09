import React, { useRef, memo, useState, useCallback } from 'react';
import { GoogleMap, LoadScript, DrawingManager } from '@react-google-maps/api';
import PropTypes from 'prop-types';
import Locate from './Locate';
import mapStyles from '../Styles/mapStyles';
import '../Styles/MapHeaderStyle.css';
import Logo from '../Logos/Logo';
import { GOOGLE_GEOCODE_API_KEY, GOOGLE_MAP_LIBRARIES, METERS_PER_MILE } from '../../constants';
import { Segment, Loader, Dimmer } from 'semantic-ui-react';
import { useCurrentLocation } from '../../hooks/useCurrentLocation';
import AddressRadiusSelector from './AddressRadiusSelector';
/**
 * Location Map - A Map with options for configuration an extra features
 * @param {children} components to include in the map such as markers or
 * controllers.
 * @param {addSearch} -boolean to include a search component or not
 * @param {addLocator} - boolean to include a locator
 * @param {logo} - A logo to include in top left corner of map
 * @param {options} - google map api options
 * @param {containerStyle} - style for maps container
 * @param {googleAPIKey} - google maps api key
 * @param {zoom} - zoom level
 */
function LocationMap({
	children,
	addSearch,
	addLocator,
	logo,
	options,
	containerStyle,
	googleAPIKey,
	zoom,
	customComponents,
	onAddLocation,
	markers,
	setMarkers,
	circles,
	setCircles,
}) {
	const mapRef = useRef();

	const { location, error } = useCurrentLocation(null);
	const [selectedLocation, setSelectedLocation] = useState(location);

	const onSearchLocationSelected = useCallback((location) => {
		setSelectedLocation(location);
	}, []);
	const addMarker = (circles, markers) => (loc, miles, ref_id) => {
		const coords = { lat: loc.geometry.location.lat(), lng: loc.geometry.location.lng() };
		const marker = new window.google.maps.Marker({
			ref_id,
			position: coords,
			map: mapRef.current,
		});
		const infoWindow = new window.google.maps.InfoWindow({
			content: '<div><Input type="button"/></div>',
		});

		window.google.maps.event.addListener(marker, 'click', function () {
			infoWindow.open(mapRef.current, marker);
		});
		const milesRadius = new window.google.maps.Circle({
			ref_id,
			strokeColor: '#FF0000',
			strokeOpacity: 0.8,
			strokeWeight: 2,
			fillColor: '#FF0000',
			fillOpacity: 0.35,
			map: mapRef.current,
			center: coords,
			radius: METERS_PER_MILE * miles,
		});
		setCircles([...circles, milesRadius]);
		setMarkers([...markers, marker]);
		return marker;
	};
	const onMapLoad = (map) => {
		mapRef.current = map;
	};

	const panTo = ({ lat, lng }) => {
		mapRef.current.panTo({ lat, lng });
	};
	if (error) {
		return <div>Error Finding Current Location: {error}</div>;
	}
	return location ? (
		<Segment.Group>
			<LoadScript googleMapsApiKey={googleAPIKey} libraries={GOOGLE_MAP_LIBRARIES}>
				{addSearch || addLocator ? (
					<Segment>
						<div>
							{addSearch ? (
								<AddressRadiusSelector
									panTo={panTo}
									location={location}
									onSearchLocationSelected={onSearchLocationSelected}
									onAddLocation={(location, miles) => {
										const loc = onAddLocation(selectedLocation, miles);
										if (loc) {
											addMarker(circles, markers)(selectedLocation, miles, loc.ref_id);
										}
									}}
								/>
							) : (
								''
							)}

							{addLocator ? <Locate panTo={panTo} /> : ''}
						</div>
					</Segment>
				) : (
					<div></div>
				)}
				<Segment>
					{logo}

					{customComponents.map((component) => component)}

					<GoogleMap
						className="MapHeadStyle"
						mapContainerStyle={containerStyle}
						center={location}
						zoom={zoom}
						options={options}
						onLoad={onMapLoad}
					>
						{/* Child components, such as markers, info windows, etc. */}
						<DrawingManager />
					</GoogleMap>
				</Segment>
			</LoadScript>
		</Segment.Group>
	) : (
		<div style={defaultContainerStyle}>
			<Dimmer active>
				<Loader size="large" inline="centered">
					Loading
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
const defaultOptions = {
	styles: mapStyles,
	disableDefaultUI: true, //disable the controls
	zoomControl: true, //ad only controls we wanbt
};
const center = {
	lat: 28,
	lng: -81,
};

LocationMap.defaultProps = {
	zoom: 10,
	options: defaultOptions,
	center: center,
	containerStyle: defaultContainerStyle,
	googleAPIKey: GOOGLE_GEOCODE_API_KEY,
	logo: <></>,
	customComponents: [],
	customMarker: 'BasicMarker',
	infoWindowContent: `
		<div>
			<div>Default InfoWindow</div>
		</div>
	`,
	libraries: ['places', 'drawing'],
};

LocationMap.propTypes = {
	addSearch: PropTypes.bool,
	addLocator: PropTypes.bool,
	logo: PropTypes.objectOf(Logo),
	options: PropTypes.object,
	containerStyle: PropTypes.object,
	googleAPIKey: PropTypes.string,
	zoom: PropTypes.number,
	center: PropTypes.object,
	customComponents: PropTypes.any,
	customMarker: PropTypes.string,
	libraries: PropTypes.array,
};
export default memo(LocationMap);
