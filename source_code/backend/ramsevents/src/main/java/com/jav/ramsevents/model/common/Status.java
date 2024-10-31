package com.jav.ramsevents.model.common;

import java.io.Serializable;

/**
 * Response status
 * 
 * @author Javier Huang
 */
public class Status implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Boolean success = false;
	private Integer errorCode = null;
	private Integer errorSubCode = null;
	private String errorCodeStr = null;
	private String errorSubCodeStr = null;
	private String message = null;
	
	/**
	 * Status constructor
	 */
	public Status() {
		super();
	}

	/**
	 * Get success status
	 * @return Boolean Value to indicate success status
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * Set success status
	 * @param success Boolean Value to indicate success status
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * Get Error Code
	 * @return Integer Error Code
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * Set Error Code
	 * @param errorCode Integer Error Code
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Get Error Sub Code
	 * @return Integer Error Sub Code
	 */
	public Integer getErrorSubCode() {
		return errorSubCode;
	}

	/**
	 * Set Error Sub Code
	 * @param errorSubCode Integer Error Sub Code
	 */
	public void setErrorSubCode(Integer errorSubCode) {
		this.errorSubCode = errorSubCode;
	}
    
	/**
	 * Get Error Code String
	 * @return String Error Code String
	 */
	public String getErrorCodeStr() {
		return errorCodeStr;
	}

	/**
	 * Set Error Code String
	 * @param errorCodeStr String Error Code String
	 */
	public void setErrorCodeStr(String errorCodeStr) {
		this.errorCodeStr = errorCodeStr;
	}

	/**
	 * Get Sub Error Code String
	 * @return String Sub Error Code String
	 */
	public String getErrorSubCodeStr() {
		return errorSubCodeStr;
	}

	/**
	 * Set Sub Error Code String
	 * @param errorSubCodeStr String Sub Error Code String
	 */
	public void setErrorSubCodeStr(String errorSubCodeStr) {
		this.errorSubCodeStr = errorSubCodeStr;
	}

	/**
	 * Get Error Message
	 * @return String Error Message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set Error Message
	 * @param message String Error Message
	 */
	public void setMessage(String message) {
		this.message = message;
	}	
}
