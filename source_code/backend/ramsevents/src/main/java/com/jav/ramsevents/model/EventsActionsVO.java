package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Events actions value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class EventsActionsVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String eid = null;
	private String sid = null;
	private String schoolYear = null;
	private String quarter = null;
}
