import React from 'react';
import { useSelector } from 'react-redux';
import { useActiveContact } from 'redux/hooks/useContacts';
import { use121ChatMessages } from 'redux/hooks/useMessages';

const ChatMessenger = ({ activeContact }) => {
	const { giguser } = useSelector((state) => state.giguser);

	//const messages = use121ChatMessages(giguser.id, activeContact.contactId);
	return <div></div>;
};

export default ChatMessenger;
