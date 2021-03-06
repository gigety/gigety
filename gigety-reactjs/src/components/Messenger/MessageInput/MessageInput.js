import React, { useState } from 'react';
import { Button, Input } from 'semantic-ui-react';

const MessageInput = ({ activeContact, giguser, sendChatMessage }) => {
	const [text, setText] = useState('');
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
			<input
				value={text}
				onChange={(e) => setText(e.target.value)}
				onKeyPress={(e) => {
					console.log(e.code);
					if (e.code === 'Enter') {
						sendTheMessage(text);
						setText('');
					}
				}}
			/>
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
