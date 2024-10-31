package com.jav.ramsevents.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jav.ramsevents.model.common.SuccessResponse;

/**
 * Controller for test APIs
 * 
 * @author Javier Huang
 */
@RestController
@RequestMapping("/test")
public class TestController {
	
	/**
	 * Test application health API
	 * @return ResponseEntity<SuccessResponse>
	 * @throws Exception
	 */
	@GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> getHealth() throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<String>("Server is up"), HttpStatus.OK);
	}
}
