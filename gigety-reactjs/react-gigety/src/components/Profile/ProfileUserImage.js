import React from 'react';
import { Image, Icon } from 'semantic-ui-react';
import PropTypes from 'prop-types';

const ProfileUserImage = ({ profile, size }) => {
	const imageIcon =
		profile && profile.userImageUrl ? (
			<Image avatar src={profile.userImageUrl} size={size} rounded />
		) : (
			<Icon color="blue" name="user circle" size={size} />
		);
	const userImage = profile ? <>{imageIcon}</> : <div></div>;
	return userImage;
};
ProfileUserImage.defaultProps = {
	profile: {
		profileImage: {
			image: {
				data: null,
			},
		},
	},
	size: 'medium',
};
ProfileUserImage.propTypes = {
	profile: PropTypes.object,
	size: PropTypes.string,
};
export default ProfileUserImage;
