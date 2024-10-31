package com.jav.ramsevents.model.common;

/**
 * Success response
 * 
 * @author Javier Huang
 */
public class SuccessResponse<T> extends GenericRestResponse {
	private static final long serialVersionUID = 1L;
	
	private final T body;
	
	/**
	 * SuccessResponse constructor
	 */
	public SuccessResponse() {
		super();
		getStatus().setSuccess(true);
		this.body = null;
	}
	
	/**
	 * SuccessResponse constructor
	 * @param data T
	 */
	public SuccessResponse(T data) {
		super();
		getStatus().setSuccess(true);
		this.body = data;
	}
	
	/**
	 * Get SuccessResponse body
	 * @return T
	 */
	public T getBody() {
		return this.body;
	}
}
