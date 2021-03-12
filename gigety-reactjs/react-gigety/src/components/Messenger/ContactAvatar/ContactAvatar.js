import React from 'react';
import { Image, Icon } from 'semantic-ui-react';
import PropTypes from 'prop-types';

const ContactAvatar = ({ contact, size }) => {
	const imageIcon =
		contact && contact.contactImageUrl ? (
			<Image avatar src={contact.contactImageUrl} size={size} rounded />
		) : (
			<Icon color="blue" name="user circle" size={size} />
		);
	const userImage = contact ? <>{imageIcon}</> : <div></div>;
	return userImage;
};
ContactAvatar.defaultProps = {
	contact: {
		contactImageUrl: '',
	},
	size: 'medium',
};
ContactAvatar.propTypes = {
	contact: PropTypes.object,
	size: PropTypes.string,
};
export default ContactAvatar;
