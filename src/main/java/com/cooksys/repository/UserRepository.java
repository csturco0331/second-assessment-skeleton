package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.User;
import com.cooksys.projections.UserProjection;

public interface UserRepository extends JpaRepository<User, Long> {

	UserProjection findByUsername(String username);
	
	UserProjection findByUsernameAndDeletedFlagFalseAndCredentials_Password(String username, String password);
	
	List<UserProjection> findByDeletedFlagFalseAndUsernameContaining(String username);
	
	List<UserProjection> findLikesByLiked_IdAndDeletedFlagFalse(Long id);

	List<UserProjection> findMentionsByMentioned_IdAndDeletedFlagFalse(Long id);

	List<UserProjection> findFollowersByWhoIAmFollowing_UsernameAndDeletedFlagFalse(String username);

	List<UserProjection> findWhoIAmFollowingByFollowers_UsernameAndDeletedFlagFalse(String username);

	User findFirstByUsernameAndDeletedFlagFalse(String username);

	UserProjection findByUsernameAndDeletedFlag(String username, boolean b);

	List<UserProjection> findByDeletedFlagFalse();

	User findFirstByUsername(String username);

	List<User> findAllWhoIAmFollowingByFollowers_Username(String username);
	
}
