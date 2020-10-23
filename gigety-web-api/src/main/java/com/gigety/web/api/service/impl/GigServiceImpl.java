package com.gigety.web.api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gigety.web.api.db.mongo.entity.Gig;
import com.gigety.web.api.db.mongo.repo.GigRepository;
import com.gigety.web.api.exception.GigetyException;
import com.gigety.web.api.exception.ResourceNotFoundException;
import com.gigety.web.api.service.GigService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GigServiceImpl implements GigService {

	private final GigRepository gigRepository;
	
	@Override
	public Gig createGig(Gig gig) {
		try {
			Gig gigCreated = gigRepository.insert(gig);
			log.debug("Gig created :: {}", gig.getTitle());
			return gigCreated;
		} catch (Exception e) {
			log.error("Error creating gig :: {}", e.getMessage(), e);
			throw new GigetyException(e.getMessage());
		}
	}

	@Override
	public Page<Gig> searchGigs(String searchTerm, Pageable pageable) {
		Page<Gig> results = gigRepository.regexTitleDescriptionEmailIgnoreCase(searchTerm, pageable);
		log.debug("Found {} gigs for searchTerm {}", results.getSize(), searchTerm);
		return results;
	}

	@Override
	public Gig findById(String id) {
		return gigRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("gig", "id", id));
	}

}
