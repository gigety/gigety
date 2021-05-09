import React from 'react';
import PropTypes from 'prop-types';
import { List } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
const GigLabel = ({ gig, linkTo }) => {
	return (
		<List.Item as={Link} to={linkTo}>
			<List.Content>
				<List.Header>{gig.title}</List.Header>
				<List.Description>{gig.description}</List.Description>
			</List.Content>
		</List.Item>
	);
};

GigLabel.propTypes = { gig: PropTypes.object, linkTo: PropTypes.string };

export default GigLabel;
