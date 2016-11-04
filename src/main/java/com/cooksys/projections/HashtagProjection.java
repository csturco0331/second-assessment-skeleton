package com.cooksys.projections;

import java.sql.Timestamp;

public interface HashtagProjection {

	String getLabel();
	
	Timestamp getFirstUsed();
	
	Timestamp getLastUsed();
	
}
