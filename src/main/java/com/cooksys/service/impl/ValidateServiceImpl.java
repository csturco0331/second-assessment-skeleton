package com.cooksys.service.impl;

import org.springframework.stereotype.Service;

import com.cooksys.repository.HashtagRepository;
import com.cooksys.repository.UserRepository;
import com.cooksys.service.ValidateService;

@Service
public class ValidateServiceImpl implements ValidateService {

	HashtagRepository hashtagRepo;
	UserRepository userRepo;
	
	public ValidateServiceImpl(HashtagRepository hashtagRepo, UserRepository userRepo) {
		this.hashtagRepo = hashtagRepo;
		this.userRepo = userRepo;
	}
	
	@Override
	public Boolean hashtagExists(String label) {
		return hashtagRepo.findByLabel(label) != null ? true : false;
	}

	@Override
	public Boolean usernameExists(String username) {
		return userRepo.findDeletedFlagByUsername(username) == false ? true : false;
	}

	@Override
	public Boolean usernameAvailable(String username) {
		return userRepo.findDeletedFlagByUsername(username) == null ? true : false;
	}

}
