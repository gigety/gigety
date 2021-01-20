import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { findMessagesFor121Chat } from '../actions/messagesAction';

export const use121ChatMessages = (senderId, recipientId) => {
	const content = useSelector((state) => state.messages);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(findMessagesFor121Chat(senderId, recipientId));
	}, [dispatch, senderId, recipientId]);
	console.log('content.messages :::: ', content.messages);
	return content.messages;
};
