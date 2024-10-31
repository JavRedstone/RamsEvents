package com.jav.ramsevents.model.common;

import java.io.Serializable;

/**
 * Generic rest response
 * 
 * @author Javier Huang
 */
public class GenericRestResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Status status = new Status();
	
	/**
	 * GenericRestResponse constructor
	 */
	public GenericRestResponse() {
		super();
	}

	/**
	 * Get Status
	 * @return Status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Set Status
	 * @param status Status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
