package com.jav.ramsevents.rest.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jav.ramsevents.exception.AppException;
import com.jav.ramsevents.model.LoginBaseInfoVO;
import com.jav.ramsevents.model.LoginSearchVO;
import com.jav.ramsevents.model.LoginSignupVO;
import com.jav.ramsevents.model.common.FailResponse;
import com.jav.ramsevents.model.common.SuccessResponse;
import com.jav.ramsevents.service.LoginService;

/**
 * Controller for login APIs
 * 
 * @author Javier Huang
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	/**
	 * Login API
	 * @param data LoginSearchVO Login Search Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with list of Login Base Information Value Object
	 * @throws Exception
	 */
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> login(@RequestBody LoginSearchVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<List<LoginBaseInfoVO>>(loginService.login(data)), HttpStatus.OK);
	}
	
	/**
	 * Sign up API
	 * @param data LoginSignupVO Login Sign up Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of signed up students
	 * @throws Exception
	 */
	@PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> signup(@RequestBody LoginSignupVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<Integer>(loginService.signup(data)), HttpStatus.OK);
	}
	
	/**
	 * Exception handling for HttpStatus.INTERNAL_SERVER_ERROR
	 * @param ex Exception
	 * @return ResponseEntity<FailResponse>
	 */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<FailResponse> handleException(Exception ex) {
    	return new ResponseEntity<FailResponse>(new FailResponse(500, StringUtils.trimToEmpty("Something went wrong...Fatal Error!")), HttpStatus.OK);
    }
    
    /**
     * Exception handling for HttpStatus.BAD_REQUEST
     * @param ae AppException
     * @return ResponseEntity<FailResponse>
     */
    @ExceptionHandler({AppException.class})
    public ResponseEntity<FailResponse> handleException(AppException ae) {
    	return new ResponseEntity<FailResponse>(new FailResponse(400, StringUtils.trimToEmpty(ae.getMessage())), HttpStatus.OK);
    }
}
