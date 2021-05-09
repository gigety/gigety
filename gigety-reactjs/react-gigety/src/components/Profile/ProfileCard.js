import React from 'react';
import { Card } from 'semantic-ui-react';
import ProfileImage from './ProfileImage';
import PropTypes from 'prop-types';
function ProfileCard({ profile }) {
	return (
		<Card>
			<Card.Content>
				<Card.Header> {profile.title}</Card.Header>
			</Card.Content>
			<ProfileImage profile={profile} size="huge" />
		</Card>
	);
}

ProfileCard.propTypes = {
	profile: PropTypes.shape({
		title: '',
	}),
};
export default ProfileCard;
