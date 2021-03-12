import React, { useState } from 'react';
import { PropTypes } from 'prop-types';
import { useSelector } from 'react-redux';
import ScrollToBottom from 'react-scroll-to-bottom';
import { use121ChatMessages, useMessenger } from 'redux/hooks/useMessages';
import { List } from 'semantic-ui-react';
import ContactAvatar from '../ContactAvatar/ContactAvatar';
import MessageInput from '../MessageInput/MessageInput';
const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);
	activeContact = activeContact ? activeContact : {};
	const messages = use121ChatMessages(giguser.id, activeContact.contactId);

	useMessenger(giguser, activeContact);
	return (
		<>
			<ScrollToBottom className="messages">
				<List>
					{messages
						? messages.map((msg) => (
								<List.Item>
									<ContactAvatar size="med" contact={activeContact} />
									<List.Content>
										<List.Header as="a">{activeContact.contactName}</List.Header>
										<List.Description>{msg.content}</List.Description>
									</List.Content>
								</List.Item>
						  ))
						: ''}
				</List>
			</ScrollToBottom>
			<MessageInput activeContact={activeContact} giguser={giguser}></MessageInput>
		</>
	);
};

ChatMessenger.propTypes = {
	activeContact: {
		contactId: PropTypes.string,
	},
};
ChatMessenger.defaultProps = {
	activeContact: { contactId: '0' },
};
export default ChatMessenger;
