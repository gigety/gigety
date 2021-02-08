import React from 'react';
import { Image, List } from 'semantic-ui-react';

const ContactList = ({ contacts }) => {
	return (
		<List>
			sadfadfasdfasdf
			{contacts
				? contacts.map((contact) => (
						<List.Item>
							<Image avatar src={contact.userImageUrl} />
							<List.Content>
								<List.Header as="a">{contact.userName}</List.Header>
								<List.Description>... online</List.Description>
							</List.Content>
						</List.Item>
				  ))
				: ''}
		</List>
	);
};

export default ContactList;
