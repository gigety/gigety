package com.gigety.web.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gigety.web.api.db.mongo.entity.Gig;

public interface GigService {

	Gig createGig(Gig gig);
	Page<Gig> searchGigs(String searchTerm, Pageable pageable);
	Gig findById(String id);
}
