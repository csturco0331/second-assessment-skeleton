package com.cooksys.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Hashtag;
import com.cooksys.projections.HashtagProjection;
import com.cooksys.projections.TweetProjection;
import com.cooksys.service.TagsService;

/**
 * Created November 6, 2016. Intended use is to handle requests from
 * end user that deal with {@link Hashtag}s inside of the linked DataBase
 * @author Cory Scott Turco
 */
@RestController
@RequestMapping("tags")
public class TagsController {
	
	private TagsService tagsService;

	public TagsController(TagsService tagsService) {
		this.tagsService = tagsService;
	}

	/**
	 * Retrieves all {@link HashtagProjection}s tracked by the database.
	 * @return {@link List}<{@link HashtagProjection}>
	 */
	@GetMapping
	public List<HashtagProjection> getHashtags() {
		return tagsService.getHashtags();
	}
	
	/**
	 * Retrieves all (non-deleted) {@link TweetProjection}s tagged with the given {@link Hashtag} label. The tweets 
	 * should appear in reverse-chronological order. If no hashtag with the given label exists, an error should be 
	 * sent in lieu of a response. A tweet is considered "tagged" by a hashtag if the tweet has content and the hashtag's 
	 * label appears in that content following a #
	 * @param label {@link String}
	 * @return {@link List}<{@link TweetProjection}>
	 * @throws Exception
	 */
	@GetMapping("/{label}")
	public List<TweetProjection> getLabel(@PathVariable String label) throws Exception {
		return tagsService.getLabel(label);
	}
	
}
