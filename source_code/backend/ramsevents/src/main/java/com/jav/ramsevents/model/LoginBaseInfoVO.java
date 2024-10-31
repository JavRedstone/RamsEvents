package com.jav.ramsevents.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login base information value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class LoginBaseInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long sid = null;
	private Integer studentId = null;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private String email = null;
	private Integer grade = null;
	private String homeroom = null;
	private String groupName = null;
	private String groupDescription = null;
	private String accessLevel = null;
}
