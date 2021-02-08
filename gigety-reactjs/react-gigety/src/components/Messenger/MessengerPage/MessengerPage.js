import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useAllNewUserMessages } from 'redux/hooks/useMessages';
import { Container, Grid } from 'semantic-ui-react';
import ChatMessenger from '../ChatMessenger/ChatMessenger';
import ContactList from '../ContactList/ContactList';

const MessengerPage = () => {
	const { giguser } = useSelector((state) => state.giguser);
	const messages = useAllNewUserMessages(giguser.id);
	console.log('Founcd some messages :: ', messages);
	return (
		<Container fluid>
			<Grid celled>
				<Grid.Row>
					<Grid.Column width={4}>
						<ContactList></ContactList>
					</Grid.Column>
					<Grid.Column width={6}>
						<ChatMessenger></ChatMessenger>
					</Grid.Column>
				</Grid.Row>
			</Grid>
		</Container>
	);
};

export default MessengerPage;
