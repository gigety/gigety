import React, { useState, useContext, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useGigUser } from 'redux/hooks/useGigUser';
import { useAllNewUserMessages } from 'redux/hooks/useMessages';
import { Container, Divider, Grid, GridColumn } from 'semantic-ui-react';
import ChatMessenger from '../ChatMessenger/ChatMessenger';
import ContactList from '../ContactList/ContactList';
import MessageInput from '../MessageInput/MessageInput';
import NotificationsScrollable from '../NotificationsScrollable/NotificationsScrollable';
import { StompClientContext } from 'contexts/StompClientContext';
import { useActiveContact, useContacts } from 'redux/hooks/useContacts';
const MessengerPage = () => {
	//const { giguser } = useSelector((state) => state.giguser);
	const giguser = useGigUser();
	console.log(giguser);

	const contacts = useContacts(giguser.id);
	const activeContact = useActiveContact();
	const { stompClient, sendChatMessage } = useContext(StompClientContext);

	const sendTheMessage = (msg) => {
		if (msg.trim() !== '') {
			const message = {
				senderId: giguser.id,
				recipientId: activeContact.userId,
				senderName: giguser.name,
				recipientName: activeContact.userName,
				content: msg,
				timestamp: new Date(),
			};
			console.log('sending msg: ', msg);
			sendChatMessage(message);
			console.log('msg sent', message);
		}
	};
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
