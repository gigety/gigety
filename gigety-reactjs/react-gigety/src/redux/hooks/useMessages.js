import { StompClientContext } from 'contexts/StompClientContext';
import { useContext, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { findMessagesFor121Chat, findNewUserMessages, findUserMessageNotifications } from '../actions/messagesAction';

export const use121ChatMessages = (senderId, recipientId) => {
	const content = useSelector((state) => state.messages);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(findMessagesFor121Chat(senderId, recipientId));
	}, [dispatch, senderId, recipientId]);
	return content.messages;
};

export const useAllUserMessageNotifications = (userId) => {
	const content = useSelector((state) => state.messages);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(findUserMessageNotifications(userId));
	}, [dispatch, userId]);
	return content.messageNotifications;
};

export const useAllNewUserMessages = (userId) => {
	const content = useSelector((state) => state.messages);
	console.log(content);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(findNewUserMessages(userId));
	}, [dispatch, userId]);
	return content.newMessages;
};
export const useMessenger = (giguser, contact) => {
	const { stompClient } = useContext(StompClientContext);
	const dispatch = useDispatch();
	console.log('1111111111112222223333334344444444');
	useEffect(() => {
		if (stompClient.connected) {
			const onMessageRecieved = (msg) => {
				console.log('RECEIVED MESSAGE +++++++++++++++++++', contact);
				const notification = JSON.parse(msg.body);
				if (contact.contactId === notification.senderId) {
					console.log('WE FOUND A MATCH');
					dispatch(findMessagesFor121Chat(giguser.id, notification.senderId));
				}
			};
			const id = stompClient.subscribe(`/user/${giguser.id}/queue/messages`, onMessageRecieved);

			return () => {
				console.log(`here we unsubscibe to id ${id}, you best check this is proper way to unsubscribe`);
				stompClient.unsubscribe(id);
			};
		}
	}, [giguser, contact, stompClient, dispatch]);
};
