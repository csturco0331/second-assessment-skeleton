package com.cooksys.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.service.TagsService;

@RestController
@RequestMapping("tags")
public class TagsController {
	
	private TagsService tagsService;

	public TagsController(TagsService tagsService) {
		this.tagsService = tagsService;
	}

	@GetMapping
	public List<HashtagProjection> getHashtags() {
		return tagsService.getHashtags();
	}
	
	@GetMapping("/{label}")
	public List<TweetProjection> getLabel(@PathVariable String label) throws Exception {
		return tagsService.getLabel(label);
	}
	
}
