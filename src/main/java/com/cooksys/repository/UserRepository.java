package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.User;
import com.cooksys.projections.UserProjection;

public interface UserRepository extends JpaRepository<User, Long> {

	UserProjection findByUsername(String username);

	Boolean findDeletedFlagByUsername(String username);
	
	List<UserProjection> findAllProjectedBy();
	
	List<UserProjection> findLikesByLiked_Id(Long id);

	List<UserProjection> findMentionsByMentioned_Id(Long id);

	List<UserProjection> findFollowersByUsername(String username);

	List<UserProjection> findFollowingByUsername(String username);

	UserProjection findByUsernameAndCredentials_Password(String username, String password);
	
}
