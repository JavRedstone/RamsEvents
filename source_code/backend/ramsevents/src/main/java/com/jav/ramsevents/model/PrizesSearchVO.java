package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Search prizes value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class PrizesSearchVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String schoolYear = null;
	private String quarter = null;
}
