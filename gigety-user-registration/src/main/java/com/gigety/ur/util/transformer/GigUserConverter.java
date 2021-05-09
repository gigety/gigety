package com.gigety.ur.util.transformer;

import java.util.List;

import com.gigety.ur.db.model.GigUser;

public interface GigUserConverter {
	public List<GigUser> transformEmailsToGigUsers(List<String> emails);
}
