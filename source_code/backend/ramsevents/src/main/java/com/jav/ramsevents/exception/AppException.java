package com.jav.ramsevents.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Application Exception
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppException extends Exception {
	private String message;
}
