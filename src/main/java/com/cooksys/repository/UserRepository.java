package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.User;
import com.cooksys.projections.UserProjection;

public interface UserRepository extends JpaRepository<User, Long> {

	//consider replacing all instances with findbyusernameanddeletedflag
	UserProjection findByUsername(String username);

	Boolean findDeletedFlagByUsername(String username);
	
	//consider replacing all instances with findallprojectedby
	List<UserProjection> findAllProjectedBy();
	
	List<UserProjection> findLikesByLiked_IdAndDeletedFlag(Long id, boolean b);

	List<UserProjection> findMentionsByMentioned_IdAndDeletedFlag(Long id, boolean b);

	List<UserProjection> findFollowersByWhoIAmFollowing_UsernameAndDeletedFlag(String username, boolean b);

	List<UserProjection> findWhoIAmFollowingByFollowers_UsernameAndDeletedFlag(String username, boolean b);

	UserProjection findByUsernameAndCredentials_Password(String username, String password);

	User findFirstByUsernameAndDeletedFlag(String username, boolean b);

	UserProjection findByUsernameAndDeletedFlag(String username, boolean b);

	List<UserProjection> findAllByDeletedFlag(boolean b);

	User findFirstByUsername(String username);

	List<User> findAllWhoIAmFollowingByFollowers_UsernameAndDeletedFlag(String username, boolean b);

	List<User> findAllWhoIAmFollowingByFollowers_Username(String username);
	
}
