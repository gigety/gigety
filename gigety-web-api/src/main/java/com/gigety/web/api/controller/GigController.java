package com.gigety.web.api.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigety.web.api.db.mongo.entity.Gig;
import com.gigety.web.api.exception.GigetyException;
import com.gigety.web.api.security.CurrentUser;
import com.gigety.web.api.security.UserPrincipal;
import com.gigety.web.api.service.GigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gigs")
public class GigController {

	private final GigService gigService;

	@PostMapping(value = "/create")
	public Gig createGig(@CurrentUser UserPrincipal principal, @RequestBody Gig gig, BindingResult bindingResult) {
		try {
			//Gig gig = mapGig(gigJSON);
			gig.setEmail(principal.getEmail());
			Gig createdGig = gigService.createGig(gig);
			
			
			return createdGig;
		} catch (Exception e) {
			log.error("Error Creating Gig {}", e.getMessage());
			throw new GigetyException(e.getMessage());
		}

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> removeGig(@CurrentUser UserPrincipal principal, @PathVariable String id){
		return ResponseEntity.ok().body("Gig Removed");
	}

	private Gig mapGig(String gigJSON) {

		Gig gig = new Gig();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			gig = objectMapper.readValue(gigJSON, Gig.class);
		} catch (IOException err) {
			log.error("Error: {}", err.getMessage());
			throw new GigetyException(err.getMessage());
		}

		return gig;

	}

}
