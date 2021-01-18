import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import UserLabel from '../../User/UserLabel';
import ScrollToBottom from 'react-scroll-to-bottom';
import { GIGETY_MESSENGER_URL } from '../../../constants';
import { Button, Card, Input } from 'semantic-ui-react';
import './Chat.css';
let stompClient = null;
const Chat = ({ profile }) => {
	console.log(profile);
	const { giguser } = useSelector((state) => state.giguser);

	const [messages, setMessages] = useState([]);
	const [text, setText] = useState('');
	useEffect(() => {
		connect();
	}, []);
	const connect = () => {
		const stomp = require('stompjs');
		let SockJS = require('sockjs-client');
		SockJS = new SockJS(GIGETY_MESSENGER_URL + '/ws');
		//SockJS = new SockJS('http://localhost:7070/messenger/ws', null, {});
		console.log('sockjs::::');
		console.log(SockJS);
		stompClient = stomp.over(SockJS);
		console.log('stomClient::: ');
		console.log(stompClient);
		stompClient.connect({}, onConnected, onError);
		console.log('client after connect');
		console.log(stompClient);
	};
	const onConnected = () => {
		console.log('SockJS COnnected to STOMP protocol');
		console.log(giguser);
		stompClient.subscribe(`/messenger/user/${giguser.id}/queue/messages`, onMessageRecieved);
	};
	const onMessageRecieved = (msg) => {
		console.log('received');
		//const notification = JSON.parse(msg.body);
		//console.log(`Notification :: ${notification}`);
	};
	const onError = (error) => {
		console.error(error);
	};
	const sendMessage = (msg) => {
		if (msg.trim() !== '') {
			console.log('stompClient::::');
			console.log(stompClient);
			const message = {
				senderId: giguser.id,
				recipientId: 1,
				senderName: giguser.name,
				recipientName: profile.email,
				content: msg,
				timestamp: new Date(),
			};
			stompClient.send('/messenger/msg/chat', {}, JSON.stringify(message));
		}
	};
	const messageSent = () => {};

	return (
		<Popup
			trigger={
				<div>
					<UserLabel userImageUrl={profile.userImageUrl} email={profile.email} />
				</div>
			}
			modal
			nested
		>
			{(close) => (
				<div className="modal">
					<div className="header">Send Direct Message to {profile.email} </div>
					<div className="content">
						<ScrollToBottom className="messages"></ScrollToBottom>

						<Input placeholder="Enter Message" action fluid>
							<input value={text} onChange={(e) => setText(e.target.value)} />
							<Button
								onClick={() => {
									sendMessage(text);
									setText('');
								}}
							>
								Send Message
							</Button>
						</Input>
					</div>
					<div className="actions">
						<button className="button"> Go to Messages </button>
						<button
							className="button"
							onClick={() => {
								console.log('modal closed ');
								close();
							}}
						>
							close modal
						</button>
					</div>
				</div>
			)}
		</Popup>
	);
};

export default Chat;
