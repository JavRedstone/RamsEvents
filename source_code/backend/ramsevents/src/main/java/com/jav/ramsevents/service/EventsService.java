package com.jav.ramsevents.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jav.ramsevents.model.EventsActionsVO;
import com.jav.ramsevents.model.EventsAddVO;
import com.jav.ramsevents.model.EventsBaseInfoVO;
import com.jav.ramsevents.model.EventsDeleteVO;
import com.jav.ramsevents.model.EventsJoinedSearchVO;
import com.jav.ramsevents.model.EventsSearchVO;
import com.jav.ramsevents.model.EventsStudentsVO;
import com.jav.ramsevents.model.EventsUpdateVO;
import com.jav.ramsevents.repository.EventsRepository;

/**
 * Events services
 * 
 * @author Javier Huang
 */
@Service()
public class EventsService {
	public static final Logger LOG = LoggerFactory.getLogger(EventsService.class);
	
	@Autowired
	private EventsRepository eventsRepository;
	
	/**
	 * Search events service
	 * @param data EventsSearchVO Events Search Criteria
	 * @return List<EventsBaseInfoVO> List of Event Information Value Objects
	 * @throws Exception
	 */
	public List<EventsBaseInfoVO> searchEvents(EventsSearchVO data) throws Exception {
		return eventsRepository.searchEvents(data);
	}
	
	/**
	 * Search joined events service
	 * @param data EventsJoinedSearchVO Joined Events Search Criteria
	 * @return EventsJoinedSearchVO List of Joined Event Information Value Objects
	 * @throws Exception
	 */
	public List<EventsBaseInfoVO> searchJoinedEvents(EventsJoinedSearchVO data) throws Exception {
		return eventsRepository.searchJoinedEvents(data);
	}
	
	/**
	 * Join event service
	 * @param data EventsActionsVO Join Event Action Value Object
	 * @return Integer Number of Events Joined 
	 * @throws Exception
	 */
	public Integer joinEvent(EventsActionsVO data) throws Exception {
		return eventsRepository.joinEvent(data);
	}
	
	/**
	 * Leave event service
	 * @param data EventsActionsVO Leave Event Action Value Object
	 * @return Integer Number of events left
	 * @throws Exception
	 */
	public Integer leaveEvent(EventsActionsVO data) throws Exception {
		return eventsRepository.leaveEvent(data);
	}
	
	/**
	 * Add event service
	 * @param data EventsAddVO Add Event Value Object
	 * @return Integer Number of events added
	 * @throws Exception
	 */
	public Integer addEvent(EventsAddVO data) throws Exception {
		return eventsRepository.addEvent(data);
	}
	
	/**
	 * Update event service
	 * @param data EventsUpdateVO Event Update Value Object
	 * @return Integer Number of events updated
	 * @throws Exception
	 */
	public Integer updateEvent(EventsUpdateVO data) throws Exception {
		return eventsRepository.updateEvent(data);
	}
	
	/**
	 * Get joined students service
	 * @param data EventsDeleteVO Delete Event Value Object
	 * @return List<EventsStudentsVO> List of Events students value objects
	 * @throws Exception
	 */
	public List<EventsStudentsVO> getJoinedStudents(EventsDeleteVO data) throws Exception {
		return eventsRepository.getJoinedStudents(data);
	}
	
	/**
	 * Delete event service
	 * @param data EventsDeleteVO Delete Event Value Object
	 * @return Integer Number of events deleted
	 * @throws Exception
	 */
	public Integer deleteEvent(EventsDeleteVO data) throws Exception {
		return eventsRepository.deleteEvent(data);
	}
}
