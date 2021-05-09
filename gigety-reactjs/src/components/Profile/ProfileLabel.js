import React from 'react';
import PropTypes from 'prop-types';
import { List } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import ProfileImage from './ProfileImage';
export const ProfileLabel = ({ profile, linkTo }) => {
	return (
		<List.Item as={Link} to={linkTo}>
			<ProfileImage profile={profile} />
			<List.Content>
				<List.Header>{profile.title}</List.Header>
				<List.Description>{profile.description}</List.Description>
			</List.Content>
		</List.Item>
	);
};

ProfileLabel.propTypes = {
	profile: PropTypes.object,
};

export default ProfileLabel;
