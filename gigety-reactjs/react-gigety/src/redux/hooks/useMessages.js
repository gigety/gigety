import { useEffect } from 'react';
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
