import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { findUserContacts, updateActiveContact } from 'redux/actions/contactsAction';

export const useActiveContact = (contactId) => {
	const content = useSelector((state) => state.contacts);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(updateActiveContact(contactId));
	}, [dispatch, contactId]);

	return content.activeContact;
};

export const useContacts = (userId) => {
	const content = useSelector((state) => state.contacts);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(findUserContacts(userId));
	}, [dispatch, userId]);

	return content.contacts;
};
