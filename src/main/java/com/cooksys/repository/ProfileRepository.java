package com.cooksys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
