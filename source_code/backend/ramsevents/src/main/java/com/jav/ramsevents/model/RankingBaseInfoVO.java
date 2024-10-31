package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ranking base information value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class RankingBaseInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long sid = null;
	private Integer studentId = null;
	private String firstName = null;
	private String lastName = null;
	private Integer grade = null;
	private Integer pointsEarned = null;
	private Boolean isWinner = null;
	private Boolean isRandom = null;
	private String prizeName = null;
	private String prizeDescription = null;
	private String schoolYear = null;
	private Integer quarter = null;
}
