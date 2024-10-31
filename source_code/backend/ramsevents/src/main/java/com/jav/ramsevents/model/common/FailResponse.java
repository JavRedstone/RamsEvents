package com.jav.ramsevents.model.common;

/**
 * Fail response
 * 
 * @author Javier Huang
 */
public class FailResponse extends GenericRestResponse {
	private static final long serialVersionUID = 1L;
	
	/**
	 * FailResponse constructor
	 */
	public FailResponse() {
		super();
		getStatus().setSuccess(false);
	}
	
	/**
	 * Fail Response
	 * @param code Integer error code
	 * @param err String error string
	 */
	public FailResponse(Integer code, String err) {
		super();
		getStatus().setSuccess(false);
		getStatus().setErrorCode(code);
		getStatus().setErrorCodeStr(err);
	}
}