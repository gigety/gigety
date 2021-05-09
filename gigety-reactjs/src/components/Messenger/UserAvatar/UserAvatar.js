import React from 'react';
import { Image, Icon } from 'semantic-ui-react';
import PropTypes from 'prop-types';

const UserAvatar = ({ user, size }) => {
	console.log(user);
	const imageIcon =
		user && user.imageUrl ? (
			<Image avatar src={user.imageUrl} rounded />
		) : (
			<Icon color="blue" name="user circle" size={size} />
		);
	const userImage = user ? <>{imageIcon}</> : <div></div>;
	return userImage;
};
UserAvatar.defaultProps = {
	contact: {
		contactImageUrl: '',
	},
	size: 'medium',
};
UserAvatar.propTypes = {
	contact: PropTypes.object,
	size: PropTypes.string,
};
export default UserAvatar;
