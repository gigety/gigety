import React from 'react';
import PropTypes from 'prop-types';
import { Label, Icon } from 'semantic-ui-react';
import _ from 'lodash';
function LocationList({ locations, onRemove }) {
	return (
		<>
			{locations
				? locations.map((location) => {
						return (
							<div key={_.random(3, 4, true)} style={{ padding: 2 }}>
								<Label size="large">
									<Icon name="map" color="blue" />
									Within {location.radius} miles of {location.address}
									<Icon name="delete" color="red" onClick={() => onRemove(location.ref_id)} />
								</Label>
							</div>
						);
				  })
				: ''}
		</>
	);
}

LocationList.propTypes = { locations: PropTypes.array, onRemove: PropTypes.func };

export default LocationList;
