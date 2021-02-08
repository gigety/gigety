package com.gigety.web.api.service;

import java.util.List;

import com.gigety.web.api.db.mongo.entity.Contact;

public interface ContactsService {

	List<Contact> findContactsForUser(String userId);
}
