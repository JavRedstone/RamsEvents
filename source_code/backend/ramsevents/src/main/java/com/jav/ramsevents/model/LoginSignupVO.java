package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login sign up value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class LoginSignupVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uid = null;
	private String studentId = null;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private String email = null;
	private String grade = null;
	private String homeroom = null;
	private String accessLevel = null;
}
