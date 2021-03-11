import React, { useState, useContext, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useGigUser } from 'redux/hooks/useGigUser';
import { useAllNewUserMessages } from 'redux/hooks/useMessages';
import { Container, Grid, GridColumn } from 'semantic-ui-react';
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
	const messages = useAllNewUserMessages(giguser.id);
	console.log('messages :: ', messages);
	const [text, setText] = useState('');
	console.log('Founcd some messages :: ', messages);
	const dispatch = useDispatch();
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
						<NotificationsScrollable giguser={giguser} messages={messages} />
					</GridColumn>
					<GridColumn width={6}>
						<ChatMessenger activeContact={activeContact}></ChatMessenger>
					</GridColumn>
				</Grid.Row>
				<Grid.Row>
					<Grid.Column width={4}>
						<ContactList contacts={contacts}></ContactList>
					</Grid.Column>
					<Grid.Column width={6}>
						<MessageInput text={text} setText={setText}></MessageInput>
					</Grid.Column>
				</Grid.Row>
			</Grid>
		</Container>
	);
};

export default MessengerPage;
