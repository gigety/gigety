import React from 'react';
import { useGigUser } from 'redux/hooks/useGigUser';
import { Container, Divider, Grid, GridColumn } from 'semantic-ui-react';
import ChatMessenger from '../ChatMessenger/ChatMessenger';
import ContactList from '../ContactList/ContactList';
import NotificationsScrollable from '../NotificationsScrollable/NotificationsScrollable';
import { useActiveContact, useContacts } from 'redux/hooks/useContacts';
const MessengerPage = () => {
	//const { giguser } = useSelector((state) => state.giguser);
	const giguser = useGigUser();
	console.log(giguser);

	const contacts = useContacts(giguser.id);
	const activeContact = useActiveContact();

	return (
		<Container fluid>
			<Grid celled>
				<Grid.Row>
					<GridColumn width={4}>
						<Grid.Row>
							<NotificationsScrollable giguser={giguser} />
						</Grid.Row>
						<Divider />
						<Grid.Row>
							<ContactList contacts={contacts}></ContactList>
						</Grid.Row>
					</GridColumn>
					<GridColumn width={6}>
						<Grid.Row>
							<ChatMessenger activeContact={activeContact}></ChatMessenger>
						</Grid.Row>
						<Grid.Row></Grid.Row>
					</GridColumn>
				</Grid.Row>
				<Grid.Row>
					<Grid.Column width={4}></Grid.Column>
				</Grid.Row>
			</Grid>
		</Container>
	);
};

export default MessengerPage;
