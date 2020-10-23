import React from 'react';
import { Image, Icon } from 'semantic-ui-react';

const UserImage = ({ giguser }) => {
	const imageIcon =
		giguser && giguser.imageUrl ? (
			<Image src={giguser.imageUrl} size="medium" rounded />
		) : (
			<Icon color="blue" name="user circle" size="massive" />
		);
	const userImage = giguser ? <>{imageIcon}</> : <div></div>;
	return userImage;
};

export default UserImage;
