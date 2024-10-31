package com.jav.ramsevents.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Events base information value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class EventsBaseInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long eid = null;
	private String eventName = null;
	private String description = null;
	private Integer minPoints = null;
	private Integer maxPoints = null;
	private Date startDate = null;
	private Date endDate = null;
	private String clubName = null;
	private String schoolYear = null;
	private Integer quarter = null;
}
