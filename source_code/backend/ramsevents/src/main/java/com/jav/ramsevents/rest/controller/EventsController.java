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
import com.jav.ramsevents.model.EventsActionsVO;
import com.jav.ramsevents.model.EventsAddVO;
import com.jav.ramsevents.model.EventsBaseInfoVO;
import com.jav.ramsevents.model.EventsDeleteVO;
import com.jav.ramsevents.model.EventsJoinedSearchVO;
import com.jav.ramsevents.model.EventsSearchVO;
import com.jav.ramsevents.model.EventsStudentsVO;
import com.jav.ramsevents.model.EventsUpdateVO;
import com.jav.ramsevents.model.common.FailResponse;
import com.jav.ramsevents.model.common.SuccessResponse;
import com.jav.ramsevents.service.EventsService;

/**
 * Controller for events APIs
 * 
 * @author Javier Huang
 */
@CrossOrigin
@RestController
@RequestMapping("/events")
public class EventsController {
	@Autowired
	private EventsService eventsService;
	
	/**
	 * Search events API
	 * @param data EventsSearchVO Events Search Criteria
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with list of Event Information Value Objects
	 * @throws Exception
	 */
	@PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> searchEvents(@RequestBody EventsSearchVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<List<EventsBaseInfoVO>>(eventsService.searchEvents(data)), HttpStatus.OK);
	}
	
	/**
	 * Search joined events API
	 * @param data EventsJoinedSearchVO Joined Events Search Criteria
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with List of Joined Event Information Value Objects
	 * @throws Exception
	 */
	@PostMapping(path = "/searchjoined", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> searchJoinedEvents(@RequestBody EventsJoinedSearchVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<List<EventsBaseInfoVO>>(eventsService.searchJoinedEvents(data)), HttpStatus.OK);
	}
	
	/**
	 * Join event API
	 * @param data EventsActionsVO Join Event Action Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of Events Joined
	 * @throws Exception
	 */
	@PostMapping(path = "/join", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> joinEvent(@RequestBody EventsActionsVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<Integer>(eventsService.joinEvent(data)), HttpStatus.OK);
	}
	
	/**
	 * Leave event API
	 * @param data EventsActionsVO Leave Event Action Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of events left
	 * @throws Exception
	 */
	@PostMapping(path = "/leave", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> leaveEvent(@RequestBody EventsActionsVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<Integer>(eventsService.leaveEvent(data)), HttpStatus.OK);
	}
	
	/**
	 * Add event API
	 * @param data EventsAddVO Add Event Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of events added
	 * @throws Exception
	 */
	@PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> addEvent(@RequestBody EventsAddVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<Integer>(eventsService.addEvent(data)), HttpStatus.OK);
	}	
	
	/**
	 * Update event API
	 * @param data EventsUpdateVO Event Update Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of events updated
	 * @throws Exception
	 */
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> updateEvent(@RequestBody EventsUpdateVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<Integer>(eventsService.updateEvent(data)), HttpStatus.OK);
	}
	
	/**
	 * Delete event API
	 * @param data EventsDeleteVO Delete Event Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of events deleted
	 * @throws Exception
	 */
	/**
	 * Get joined students API
	 * @param data EventsDeleteVO Delete Event Value Object
	 * @return ResponseEntity<List<EventsStudentsVO>> SuccessResponse with list of events students value objects
	 * @throws Exception
	 */
	@PostMapping(path = "/getJoined", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> getJoinedStudents(@RequestBody EventsDeleteVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<List<EventsStudentsVO>>(eventsService.getJoinedStudents(data)), HttpStatus.OK);
	}
	
	/**
	 * Delete event API
	 * @param data EventsDeleteVO Delete Event Value Object
	 * @return ResponseEntity<SuccessResponse> SuccessResponse with number of events deleted
	 * @throws Exception
	 */
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> deleteEvent(@RequestBody EventsDeleteVO data) throws Exception {
		return new ResponseEntity<SuccessResponse>(new SuccessResponse<Integer>(eventsService.deleteEvent(data)), HttpStatus.OK);
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
