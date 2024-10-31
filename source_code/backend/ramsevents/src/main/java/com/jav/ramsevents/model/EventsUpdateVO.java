package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update events value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class EventsUpdateVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String eid = null;
	private String name = null;
	private String description = null;
	private String minPoints = null;
	private String maxPoints = null;
	private String startDate = null;
	private String endDate = null;
	private String schoolYear = null;
	private String quarter = null;
}
