import React from 'react';
import { Icon, Card } from 'semantic-ui-react';
import UserImage from './UserImage';
const UserCard = ({ giguser }) => {
	return (
		<Card>
			<UserImage giguser={giguser} />
			<Card.Content>
				<Card.Header> {giguser.name}</Card.Header>
				<Card.Meta>Logged in via {giguser.provider}</Card.Meta>
				<Card.Meta>Joined on {giguser.createdAt ? giguser.createdAt.substring(0, 10) : ''}</Card.Meta>
			</Card.Content>
			<Card.Content extra>
				<Icon name="mail" /> {giguser.email}
			</Card.Content>
		</Card>
	);
};
export default UserCard;
