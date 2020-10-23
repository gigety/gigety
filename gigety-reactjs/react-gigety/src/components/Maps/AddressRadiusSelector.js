import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Input, Button } from 'semantic-ui-react';
import PlaceAutoComplete from './Search/PlaceAutoComplete';

function AddressRadiusSelector({ panTo, location, onSearchLocationSelected, onAddLocation, defaultMiles }) {
	const [miles, setMiles] = useState(defaultMiles);
	const onUpdateMiles = (e) => {
		setMiles(e.target.value);
	};
	return (
		<div>
			Within &nbsp; &nbsp;
			<Input type="text" placeholder={miles} size="small" onBlur={onUpdateMiles} /> &nbsp; miles of&nbsp;
			<PlaceAutoComplete panTo={panTo} location={location} onSearchLocationSelected={onSearchLocationSelected} />
			<Button
				primary
				content="Add Location to Profile"
				icon="add"
				onClick={() => onAddLocation(location, miles)}
			></Button>
		</div>
	);
}

AddressRadiusSelector.propTypes = {
	location: PropTypes.shape({
		lat: PropTypes.number.isRequired,
		lng: PropTypes.number.isRequired,
	}),
	panTo: PropTypes.func,
	defaultMiles: PropTypes.number,
};
AddressRadiusSelector.defaultProps = {
	location: {
		lat: 47.60621,
		lng: -122.33207,
	},
	defaultMiles: 20,
};
export default AddressRadiusSelector;
