import React from 'react';
import PropTypes from 'prop-types';
import { Label, Image, Card, CardHeader } from 'semantic-ui-react';
function UserLabel({ email, userImageUrl }) {
	return (
		<Card>
			<Card.Content>
				<Label as="button" image>
					<Image src={userImageUrl} size="medium" rounded />
					{email}
				</Label>
			</Card.Content>
		</Card>
	);
}

UserLabel.propTypes = {};

export default UserLabel;
