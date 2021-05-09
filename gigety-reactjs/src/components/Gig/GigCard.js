import React from 'react';
import PropTypes from 'prop-types';
import { Card } from 'semantic-ui-react';

const GigCard = ({ gig }) => {
	return (
		<>
			<Card>
				<Card.Content>
					<Card.Header>{gig.title}</Card.Header>
					<Card.Description>{gig.description}</Card.Description>
				</Card.Content>
			</Card>
		</>
	);
};

GigCard.propTypes = {};

export default GigCard;
