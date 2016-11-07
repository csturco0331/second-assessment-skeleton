package com.cooksys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.repository.HashtagRepository;
import com.cooksys.repository.UserRepository;
import com.cooksys.service.ValidateService;

@Service
public class ValidateServiceImpl implements ValidateService {
	@Autowired
	HashtagRepository hashtagRepo;
	@Autowired
	UserRepository userRepo;
	
	@Override
	public Boolean hashtagExists(String label) {
		return hashtagRepo.findByLabelIgnoreCase(label) != null ? true : false;
	}

	@Override
	public Boolean usernameExists(String username) {
		return userRepo.findByUsernameAndDeletedFlag(username, false) != null ? true : false;
	}

	@Override
	public Boolean usernameAvailable(String username) {
		return userRepo.findByUsername(username) == null ? true : false;
	}

}
