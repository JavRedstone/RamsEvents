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
import com.jav.ramsevents.model.PrizesBaseInfoVO;
import com.jav.ramsevents.model.PrizesSearchVO;
import com.jav.ramsevents.model.common.FailResponse;
import com.jav.ramsevents.model.common.SuccessResponse;
import com.jav.ramsevents.service.PrizesService;

/**
 * Controller for prize APIs
 * 
 * @author Javier Huang
 */
@CrossOrigin
@RestController
@RequestMapping("/prizes")
public class PrizesController {
	@Autowired
	private PrizesService prizesService;
	
	/**
	 * Search prizes API
	 * @param data PrizesSearchVO Prizes Search Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with list of Prize Base Information Value Objects
	 * @throws Exception
	 */
	@PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> searchPrizes(@RequestBody PrizesSearchVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<List<PrizesBaseInfoVO>>(prizesService.searchPrizes(data)), HttpStatus.OK);
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
