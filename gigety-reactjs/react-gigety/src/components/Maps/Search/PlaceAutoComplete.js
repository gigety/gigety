import React, { useEffect } from 'react';
import { Search } from 'semantic-ui-react';
import _ from 'lodash';
import usePlacesAutocomplete, { getGeocode, getLatLng } from 'use-places-autocomplete';
import PropTypes from 'prop-types';
import '../../Styles/MapHeaderStyle.css';
import { useCurrentAddress } from '../../../redux/hooks/useCurrentAddress';

function PlaceAutoComplete({ panTo, location, onSearchLocationSelected }) {
	const currentAddress = useCurrentAddress();
	useEffect(() => {
		if (currentAddress) {
			onSearchLocationSelected(currentAddress);
		}
	}, [onSearchLocationSelected, currentAddress]);
	const {
		ready,
		value,
		suggestions: { status, data },
		setValue,
		clearSuggestions,
	} = usePlacesAutocomplete({
		requestOptions: {
			location: { lat: () => location.lat, lng: () => location.lng },
			radius: 200,
		},
	});
	data.map((d) => {
		//adding required title property for search and key for list to avoid
		//console error logs
		d.key = _.random(98, 99, true);
		d.title = d.description;
		return d;
	});
	console.log('status: ', status);
	return ready && data ? (
		<Search
			className="input segment-ctl"
			onResultSelect={async (e, { result }) => {
				const { description } = result;
				const address = description;
				setValue(address, false);
				clearSuggestions();
				try {
					const results = await getGeocode({ address });
					const { lat, lng } = await getLatLng(results[0]);
					panTo({ lat, lng });
					onSearchLocationSelected(results[0]);
				} catch (error) {
					console.log('error searching places for autocomplete: ', error);
				}
			}}
			onSearchChange={(e) => {
				setValue(e.target.value);
			}}
			results={data ? data : {}}
			value={value}
			placeholder={currentAddress ? currentAddress.formatted_address : 'Search a location ...'}
		/>
	) : (
		<div></div>
	);
}
PlaceAutoComplete.propTypes = {
	location: PropTypes.shape({
		lat: PropTypes.number.isRequired,
		lng: PropTypes.number.isRequired,
	}),
	panTo: PropTypes.func,
};
PlaceAutoComplete.defaultProps = {
	location: {
		lat: 47.60621,
		lng: -122.33207,
	},
};
export default PlaceAutoComplete;
