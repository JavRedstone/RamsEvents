package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Prizes base information value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class PrizesBaseInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long pid = null;
	private String name = null;
	private String description = null;
	private String type = null;
	private Short minPoints = null;
}
