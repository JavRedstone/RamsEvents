package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Grant prizes value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class GrantPrizesVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String schoolYear = null;
	private String quarter = null;
}
