import gigety from 'apis/gigety';
import { GET_ERRORS, UPDATE_ACTIVE_CONTACT, UPDATE_CONTACT_LIST } from './types';

export const findUserContacts = (userId) => async (dispatch) => {
	try {
		const response = await gigety.get(`/contacts/${userId}`);
		dispatch({
			type: UPDATE_CONTACT_LIST,
			payload: response.data,
		});
	} catch (error) {
		console.error('ERROR :: ', error);
		dispatch({
			type: GET_ERRORS,
			payload: error,
		});
	}
};

export const updateActiveContact = (contact) => async (dispatch, getState) => {
	try {
		const { contacts } = getState().contacts;
		contact = JSON.parse(contact);
		let activeContact = contacts.find((c) => {
			console.log(c.contactId);
			console.log(contact['contactId']);
			console.log(c.contactId === contact.contactId);
			return c.contactId === contact.contactId;
		});
		console.log('AcCCCCCCCCC ', activeContact);
		console.log('found active contact :: ', activeContact);
		if (!activeContact || activeContact.length === 0) {
			try {
				const response = await gigety.post('/contacts', contact);
				activeContact = response.data;
			} catch (error) {
				console.warn(error);
			}
		}
		dispatch({
			type: UPDATE_ACTIVE_CONTACT,
			payload: activeContact,
		});
	} catch (error) {
		console.error('ERROR :: ', error);
		dispatch({
			type: GET_ERRORS,
			payload: error,
		});
	}
};
