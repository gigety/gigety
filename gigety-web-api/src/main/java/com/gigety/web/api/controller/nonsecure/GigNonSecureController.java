package com.gigety.web.api.controller.nonsecure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gigety.web.api.db.mongo.entity.Gig;
import com.gigety.web.api.service.GigService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/pnode/gigs")
public class GigNonSecureController {

	private final GigService gigService;
	
	@PostMapping("/{size}/{page}")
	public List<Gig> searchGigs( @PathVariable int size, @PathVariable int page, @RequestBody String searchTerm){
		log.debug("Searching All Gigs on public node, this will be moved to node server in future. searchTerm: {0}", searchTerm);
		JsonObject jsonObject = new JsonParser().parse(searchTerm).getAsJsonObject();
		JsonElement jsonElement = jsonObject.get("searchTerm");
		String  term = jsonElement.getAsString();

		
		Pageable pageable = PageRequest.of(page, size);
		Page<Gig> profiles = gigService.searchGigs(term, pageable);
		log.debug("Found {} profiles", profiles.getNumberOfElements());
		return profiles.getContent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Gig> getGigById(@PathVariable(value="id") String id){
		Gig gig = gigService.findById(id);
		return ResponseEntity.ok().body(gig);
	}
}
