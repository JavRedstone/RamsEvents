package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Search events value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class EventsSearchVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ALL = "0";
	public static final String BY_EVENTNAME = "1";
	public static final String BY_MINPOINTS = "2";
	public static final String BY_CLUBNAME = "3";
	public static final String BY_CLUBLEADERID = "4";
	public static final String BY_ENDDATE = "5";
	
	private String searchType = null;
	private String schoolYear = null;
	private String quarter = null;
	private String eventName = null;
	private String minPoints = null;
	private String clubName = null;
	private String clubLeaderId = null;
	private String endDate = null;
}
