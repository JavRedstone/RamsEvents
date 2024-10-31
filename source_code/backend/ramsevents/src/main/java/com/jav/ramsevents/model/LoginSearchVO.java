package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login search value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class LoginSearchVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email = null;
}
