package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Delete events value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class EventsDeleteVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String eid = null;
}
