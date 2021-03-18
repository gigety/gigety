package com.gigety.web.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gigety.web.api.db.mongo.entity.Contact;
import com.gigety.web.api.db.mongo.entity.UserAccount;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;
import com.gigety.web.api.service.ContactsService;
import com.gigety.web.api.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactsController {

	private final ContactsService contactsService;
	private final UserAccountService userAccountService;
	@GetMapping("/{userId}")
	public List<Contact> findContactsForUser(@PathVariable(value="userId") String userId){
		
		return contactsService.findContactsForUser(userId);
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE})
	public Contact addContact(@CurrentUser UserPrincipal principal, @RequestBody Contact contact) {
		return contactsService.addContact(contact);
	}
	
	@PostMapping("/setActive")
	public Contact setActive(@CurrentUser UserPrincipal principal, @RequestBody Contact contact) {
		UserAccount ua = userAccountService.setActiveContact(String.valueOf(principal.getId()), contact);
		return contact;
	}

}
