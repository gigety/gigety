import React from 'react';
import { Link } from 'react-router-dom';
import { Image, List } from 'semantic-ui-react';

const ContactList = ({ contacts }) => {
	return (
		<List>
			{contacts
				? contacts.map((contact) => (
						<List.Item
							key={contact.id}
							as={Link}
							to={'/'}
							onClick={(e) => {
								e.preventDefault();
							}}
						>
							<Image avatar src={contact.contactImageUrl} />
							<List.Content>
								<List.Header>{contact.contactName}</List.Header>
								<List.Description>... online</List.Description>
							</List.Content>
						</List.Item>
				  ))
				: ''}
		</List>
	);
};

export default ContactList;
