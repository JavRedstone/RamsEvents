package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clubs search value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class ClubsSearchVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ALL = "0";
	public static final String BY_CLUBNAME = "1";
	public static final String BY_DESCRIPTION = "2";
	public static final String BY_LEADERNAME = "3";
	public static final String BY_LEADERID = "4";
	
	private String searchType = null;
	private String clubName = null;
	private String description = null;
	private String firstName = null;
	private String lastName = null;
	private String leaderId = null;
}
