package com.gigety.web.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gigety.web.api.db.mongo.entity.Contact;
import com.gigety.web.api.db.mongo.repo.ContactsRepository;
import com.gigety.web.api.service.ContactsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService{

	private final ContactsRepository contactsRepository;

	@Override
	public List<Contact> findContactsForUser(String userId) {
		return contactsRepository.findByUserId(userId);
	}

	@Override
	public Contact addContact(Contact contact) {
		return contactsRepository.save(contact);
		
	}
	
	
}

