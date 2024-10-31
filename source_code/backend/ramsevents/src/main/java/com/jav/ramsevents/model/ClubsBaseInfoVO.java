package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clubs base information value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class ClubsBaseInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long cid = null;
	private String clubName = null;
	private String description = null;
	private Long leaderId = null;
	private String firstName = null;
	private String lastName = null;
	private String email = null;
}
