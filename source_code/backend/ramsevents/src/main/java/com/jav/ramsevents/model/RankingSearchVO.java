package com.jav.ramsevents.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Search ranking value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class RankingSearchVO {
	private static final long serialVersionUID = 1L;
	
	public static final String ALL = "0";
	public static final String BY_NAME = "1";
	public static final String BY_STUDENTID = "2";
	public static final String BY_GRADE = "3";
	public static final String BY_POINTSEARNED = "4";
	
	private String searchType = null;
	private String schoolYear = null;
	private String quarter = null;
	private String firstName = null;
	private String lastName = null;
	private String studentId = null;
	private String grade = null;
	private String pointsEarned = null;
}
