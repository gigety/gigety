import React from 'react';
import _ from 'lodash';
import ScrollToBottom from 'react-scroll-to-bottom';
import { List, ListItem } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { findMessagesFor121Chat } from 'redux/actions/messagesAction';
import { updateActiveContact } from 'redux/actions/contactsAction';
import { useAllNewUserMessages } from 'redux/hooks/useMessages';
const NotificationsScrollable = ({ giguser }) => {
	const allNewUserMessages = useAllNewUserMessages(giguser.id);
	//const messages = useAllNewUserMessages(giguser.id);
	const messageNotifications = _.uniqBy(allNewUserMessages, (message) => 'senderName');
	const dispatch = useDispatch();
	const getConversationMessages = ({ senderName, senderId }, event) => {
		event.preventDefault();
		//TODO: refactor Message to have a contact rather than senderId, senderName

		dispatch(findMessagesFor121Chat(giguser.id, senderId));
		const contact = { userId: giguser.id.toString(), contactId: senderId, contactName: senderName };
		dispatch(updateActiveContact(JSON.stringify(contact)));
	};
	return (
		<>
			<ScrollToBottom>
				<List>
					{messageNotifications
						? messageNotifications.map((message) => (
								<ListItem
									key={message.id}
									as={Link}
									to={'/'}
									onClick={(e) => getConversationMessages(message, e)}
								>
									<List.Content>
										<List.Header>{message.senderName}</List.Header>
										<List.Description>{message.content}</List.Description>
									</List.Content>
								</ListItem>
						  ))
						: ''}
				</List>
			</ScrollToBottom>
		</>
	);
};

export default NotificationsScrollable;
