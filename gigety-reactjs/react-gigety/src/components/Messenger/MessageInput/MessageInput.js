import React, { useContext, useState } from 'react';
import PropTypes from 'prop-types';
import { Button, Input } from 'semantic-ui-react';
import { StompClientContext } from 'contexts/StompClientContext';

const MessageInput = ({ activeContact, giguser }) => {
	const [text, setText] = useState('');
	const { sendChatMessage } = useContext(StompClientContext);
	const sendTheMessage = (msg) => {
		if (msg.trim() !== '') {
			const message = {
				senderId: giguser.id,
				recipientId: activeContact.contactId,
				senderName: giguser.name,
				recipientName: activeContact.contactName,
				content: msg,
				timestamp: new Date(),
			};
			console.log('sending msg: ', msg);
			sendChatMessage(message);
			console.log('msg sent', message);
		}
	};
	return (
		<Input placeholder="Enter Message" action fluid>
			<input value={text} onChange={(e) => setText(e.target.value)} />
			<Button
				onClick={() => {
					sendTheMessage(text);
					setText('');
				}}
			>
				Send Message
			</Button>
		</Input>
	);
};

MessageInput.propTypes = {};

export default MessageInput;
