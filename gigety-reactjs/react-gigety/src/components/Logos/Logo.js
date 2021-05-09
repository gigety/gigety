import React from 'react';
import { Label } from 'semantic-ui-react';
import PropTypes from 'prop-types';
function Logo({ size }) {
	return (
		<h2>
			<Label as="a" size={size}>
				Shroom Map{' '}
				<span role="img" aria-label="tent">
					🐸
				</span>
			</Label>
		</h2>
	);
}

Logo.propTypes = {
	size: PropTypes.string,
};
Logo.defaultProps = {
	size: 'big',
};

export default Logo;
