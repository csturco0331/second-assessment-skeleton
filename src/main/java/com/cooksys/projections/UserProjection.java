package com.cooksys.projections;

import java.sql.Timestamp;

public interface UserProjection {

	String getUsername();
	
	ProfileProjection getProfile();
	
	Timestamp getJoined();
}
