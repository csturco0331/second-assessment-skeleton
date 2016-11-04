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
	
	List<UserProjection> findLikesByLiked_Id(Long id);

	List<UserProjection> findMentionsByMentioned_Id(Long id);

	List<UserProjection> findFollowersByUsernameAndDeletedFlag(String username, boolean b);

	List<UserProjection> findFollowingByUsernameAndDeletedFlag(String username, boolean b);

	UserProjection findByUsernameAndCredentials_Password(String username, String password);

	User findFirstByUsername(String username);

	UserProjection findByUsernameAndDeletedFlag(String username, boolean b);

	List<UserProjection> findAllByDeletedFlag(boolean b);
	
}
