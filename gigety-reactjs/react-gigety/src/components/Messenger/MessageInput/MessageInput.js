import React from 'react';
import PropTypes from 'prop-types';
import { Button, Input } from 'semantic-ui-react';

const MessageInput = ({ text, setText, sendTheMessage }) => {
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
