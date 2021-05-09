import React from 'react';
import { Image, Icon } from 'semantic-ui-react';
import PropTypes from 'prop-types';
function ProfileImage({ profile, size }) {
	const imageIcon =
		profile && profile.profileImage ? (
			<Image src={`data:image/jpeg;base64,${profile.profileImage.image.data}`} size={size} rounded />
		) : (
			<Icon color="blue" name="user circle" size={size} />
		);
	const profileImage = profile ? <>{imageIcon}</> : <div></div>;
	return profileImage;
}

ProfileImage.defaultProps = {
	profile: {
		profileImage: {
			image: {
				data: null,
			},
		},
	},
	size: 'tiny',
};
ProfileImage.propTypes = {
	profile: PropTypes.object,
	size: PropTypes.string,
};
export default ProfileImage;
