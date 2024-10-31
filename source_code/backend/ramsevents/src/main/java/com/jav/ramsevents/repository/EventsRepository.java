package com.jav.ramsevents.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.jav.ramsevents.exception.AppException;
import com.jav.ramsevents.model.EventsActionsVO;
import com.jav.ramsevents.model.EventsAddVO;
import com.jav.ramsevents.model.EventsBaseInfoVO;
import com.jav.ramsevents.model.EventsDeleteVO;
import com.jav.ramsevents.model.EventsStudentsVO;
import com.jav.ramsevents.model.EventsJoinedSearchVO;
import com.jav.ramsevents.model.EventsSearchVO;
import com.jav.ramsevents.model.EventsUpdateVO;


/**
 * Events Repository
 * 
 * @author Javier Huang
 */
@CrossOrigin
@Repository
public class EventsRepository {
	private final static Logger LOG = LoggerFactory.getLogger(EventsRepository.class);

	//Listing Events
	private static final String EVENTS_QUERY = "SELECT e.id AS eid, e.name AS event_name, e.description, e.min_points, e.max_points, e.start_date, e.end_date, c.name AS club_name, q.school_year, q.quarter FROM ((ramsevents.events e JOIN ramsevents.clubs c ON e.fk_club_id = c.id) JOIN ramsevents.quarters q ON e.fk_quarter_id = q.id) WHERE q.school_year = :schoolYear AND q.quarter = :quarter";
	private static final String EVENTS_JOINED_QUERY = "SELECT e.id AS eid, e.name AS event_name, e.description, e.min_points, e.max_points, e.start_date, e.end_date, c.name AS club_name, q.school_year, q.quarter FROM ((((ramsevents.event_trackings et JOIN ramsevents.events e ON et.fk_event_id = e.id) JOIN ramsevents.students s ON et.fk_student_id = s.id) JOIN ramsevents.clubs c ON e.fk_club_id = c.id) JOIN ramsevents.quarters q ON e.fk_quarter_id = q.id) WHERE s.id = :sid AND q.school_year = :schoolYear AND q.quarter = :quarter";
	private static final String AND_QUERY = " AND ";
	private static final String BY_EVENTNAME_QUERY = "LOWER(e.name) LIKE '%' || :eventName || '%'";
	private static final String BY_MINPOINTS = "e.min_points >= :minPoints";
	private static final String BY_CLUBNAME_QUERY = "LOWER(c.name) LIKE '%' || :clubName || '%'";
	private static final String BY_CLUBLEADERID_QUERY = "c.fk_leader_id = :clubLeaderId";
	private static final String BY_ENDDATE = "e.end_date >= TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS')";
	private static final String ORDER_BY_QUERY = " ORDER BY e.end_date DESC, e.max_points DESC, e.min_points DESC, e.name, c.name";

	//Join and Leave Event
	private static final String COUNT_QUERY = "SELECT COUNT(*) FROM ramsevents.event_trackings WHERE fk_event_id = :eid AND fk_student_id = :sid";
	private static final String COUNT_RANKING_QUERY = "SELECT COUNT(*) FROM ramsevents.point_trackings WHERE fk_student_id = :sid AND fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	private static final String INSERT_RANKING_QUERY = "INSERT INTO ramsevents.point_trackings VALUES (NEXTVAL('ramsevents.point_tracking_sequence'), 0, FALSE, FALSE, localtimestamp, localtimestamp, :sid, NULL, (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter))";
	private static final String JOIN_QUERY = "INSERT INTO ramsevents.event_trackings VALUES ( NEXTVAL('ramsevents.event_tracking_sequence'), (SELECT min_points FROM ramsevents.events WHERE id = :eid), localtimestamp, localtimestamp, localtimestamp, :eid, :sid )";
	private static final String LEAVE_QUERY = "DELETE FROM ramsevents.event_trackings WHERE fk_event_id = :eid AND fk_student_id = :sid";
	private static final String MIN_POINTS_QUERY = "SELECT min_points FROM ramsevents.events WHERE id = :eid";
	private static final String GET_POINTS_QUERY = "SELECT pt.points_earned FROM ramsevents.point_trackings pt WHERE pt.fk_student_id = :sid AND pt.fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	private static final String SET_POINTS_QUERY = "UPDATE ramsevents.point_trackings SET points_earned = :pointsEarned WHERE fk_student_id = :sid AND fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	private static final String DELETE_RANKING_QUERY = "DELETE FROM ramsevents.point_trackings WHERE fk_student_id = :sid AND fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	
	//Add, Delete and Update Event
	private static final String ADD_QUERY = "INSERT INTO ramsevents.events VALUES ( NEXTVAL('ramsevents.event_sequence'), :name, :description, :minPoints, :maxPoints, TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS'), localtimestamp, localtimestamp, (SELECT id FROM ramsevents.clubs WHERE id = :cid), (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter) )";
	private static final String DELETE_QUERY = "DELETE FROM ramsevents.events WHERE id = :eid";
	private static final String UPDATE_QUERY = "UPDATE ramsevents.events SET name = :name, description = :description, min_points = :minPoints, max_points = :maxPoints, start_date = TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS'), end_date = TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS'), update_date = localtimestamp, fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter) WHERE id = :eid";
	private static final String GET_JOINED_QUERY = "SELECT s.first_name, s.last_name, s.email FROM ramsevents.event_trackings et JOIN ramsevents.students s ON et.fk_student_id = s.id WHERE fk_event_id = :eid";
	
	//Error messages
	private static final String ERROR_MESSAGE_SEARCH_EVENTS = "Error encountered in EventsRepository(searchEvents(EventsSearchVO data)).";
	private static final String ERROR_MESSAGE_SEARCH_JOINED_EVENTS = "Error encountered in EventsRepository(searchJoinedEvents(EventsJoinedSearchVO data)).";
	private static final String ERROR_MESSAGE_JOIN_EVENT = "Error encountered in EventsRepository(joinEvent(EventsActionsVO data)).";
	private static final String ERROR_MESSAGE_LEAVE_EVENT = "Error encountered in EventsRepository(leaveEvent(EventsActionsVO data)).";
	private static final String ERROR_MESSAGE_ADD_EVENT = "Error encountered in EventsRepository(addEvent(EventsAddVO data)).";
	private static final String ERROR_MESSAGE_UPDATE_EVENT = "Error encountered in EventsRepository(updateEvent(EventsUpdateVO data)).";
	private static final String ERROR_MESSAGE_GET_JOINED_STUDENTS = "Error encountered in EventsRepository(getJoinedStudents(EventsDeleteVO data)).";
	private static final String ERROR_MESSAGE_DELETE_EVENT = "Error encountered in EventsRepository(deleteEvent(EventsDeleteVO data)).";
	private static final String MISSING_MESSAGE = " Missing search criteria: ";
	private static final String COULDNOTRETRIEVE_MESSAGE = ", could not retrieve Events";

	@PersistenceContext(unitName = "postgre")
	private EntityManager eventsEM;

	/**
	 * Search Events
	 * @param data EventsSearchVO Events Search Criteria
	 * @return List<EventsBaseInfoVO> List of Event Information Value Objects
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, transactionManager = "postgreTransactionManager")
	public List<EventsBaseInfoVO> searchEvents(EventsSearchVO data) throws Exception {
		Query aQuery = null;
		EventsBaseInfoVO eventsInfoVO = null;
		List<EventsBaseInfoVO> response = new LinkedList<>();

		try {
			if (StringUtils.hasText(data.getSchoolYear()) && StringUtils.hasText(data.getQuarter())) {
				if (EventsSearchVO.ALL.equalsIgnoreCase(data.getSearchType())) {
					aQuery = eventsEM.createNativeQuery(EVENTS_QUERY + ORDER_BY_QUERY)
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter()));
				} else if (EventsSearchVO.BY_EVENTNAME.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getEventName())) {
						aQuery = eventsEM
								.createNativeQuery(EVENTS_QUERY + AND_QUERY + BY_EVENTNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("eventName", data.getEventName().toLowerCase());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "eventName" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "eventName");
					}
				} else if (EventsSearchVO.BY_MINPOINTS.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getMinPoints())) {
						aQuery = eventsEM.createNativeQuery(EVENTS_QUERY + AND_QUERY + BY_MINPOINTS + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("minPoints", Short.parseShort(data.getMinPoints()));
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "minPoints" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "minPoints");
					}
				} else if (EventsSearchVO.BY_CLUBNAME.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getClubName())) {
						aQuery = eventsEM
								.createNativeQuery(EVENTS_QUERY + AND_QUERY + BY_CLUBNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("clubName", data.getClubName().toLowerCase());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "clubName" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "clubName");
					}
				} else if (EventsSearchVO.BY_CLUBLEADERID.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getClubLeaderId())) {
						aQuery = eventsEM
								.createNativeQuery(EVENTS_QUERY + AND_QUERY + BY_CLUBLEADERID_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("clubLeaderId", Long.parseLong(data.getClubLeaderId()));
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "clubName" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "clubName");
					}
				} else if (EventsSearchVO.BY_ENDDATE.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getEndDate())) {
						aQuery = eventsEM.createNativeQuery(EVENTS_QUERY + AND_QUERY + BY_ENDDATE + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("endDate", data.getEndDate());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "endDate" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "endDate");
					}
				} else {
					LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "search type" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "search type");
				}
			} else {
				LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + MISSING_MESSAGE + "schoolYear or quarter" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "schoolYear or quarter");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + " Could not retrieve Events: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_SEARCH_EVENTS + " Could not retrieve Events: " + ex.getMessage());
			throw ex;
		}

		List<Object[]> events = aQuery.getResultList();
		for (Object[] item : events) {
			eventsInfoVO = populateEventsBaseInfoVO(item);
			response.add(eventsInfoVO);
		}
		return response;
	}

	/**
	 * Search Joined Events
	 * @param data EventsJoinedSearchVO Joined Events Search Criteria
	 * @return List<EventsBaseInfoVO> List of Joined Event Information Value Objects
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public List<EventsBaseInfoVO> searchJoinedEvents(EventsJoinedSearchVO data) throws Exception {
		Query aQuery = null;
		EventsBaseInfoVO eventsBaseInfoVO = null;
		List<EventsBaseInfoVO> response = new LinkedList<>();

		try {
			if (StringUtils.hasText(data.getSid()) && StringUtils.hasText(data.getSchoolYear())
					&& StringUtils.hasText(data.getQuarter())) {
				if (EventsJoinedSearchVO.ALL.equalsIgnoreCase(data.getSearchType())) {
					aQuery = eventsEM.createNativeQuery(EVENTS_JOINED_QUERY + ORDER_BY_QUERY)
							.setParameter("sid", Long.parseLong(data.getSid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter()));
				} else if (EventsJoinedSearchVO.BY_EVENTNAME.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getEventName())) {
						aQuery = eventsEM
								.createNativeQuery(
										EVENTS_JOINED_QUERY + AND_QUERY + BY_EVENTNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("sid", Long.parseLong(data.getSid()))
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("eventName", data.getEventName().toLowerCase());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + MISSING_MESSAGE + "eventName" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "eventName");
					}
				} else if (EventsJoinedSearchVO.BY_MINPOINTS.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getMinPoints())) {
						aQuery = eventsEM
								.createNativeQuery(EVENTS_JOINED_QUERY + AND_QUERY + BY_MINPOINTS + ORDER_BY_QUERY)
								.setParameter("sid", Long.parseLong(data.getSid()))
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("minPoints", Short.parseShort(data.getMinPoints()));
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + MISSING_MESSAGE + "minPoints" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "minPoints");
					}
				} else if (EventsJoinedSearchVO.BY_CLUBNAME.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getClubName())) {
						aQuery = eventsEM
								.createNativeQuery(EVENTS_JOINED_QUERY + AND_QUERY + BY_CLUBNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("sid", Long.parseLong(data.getSid()))
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("clubName", data.getClubName().toLowerCase());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + MISSING_MESSAGE + "clubName" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "clubName");
					}
				} else if (EventsJoinedSearchVO.BY_ENDDATE.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getEndDate())) {
						aQuery = eventsEM
								.createNativeQuery(EVENTS_JOINED_QUERY + AND_QUERY + BY_ENDDATE + ORDER_BY_QUERY)
								.setParameter("sid", Long.parseLong(data.getSid()))
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("endDate", data.getEndDate());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + MISSING_MESSAGE + "endDate" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "endDate");
					}
				} else {
					LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + MISSING_MESSAGE + "search type" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "search type");
				}
			} else {
				LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + MISSING_MESSAGE + "sid or schoolYear or quarter" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "sid or schoolYear or quarter");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + " Could not retrieve Events: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_SEARCH_JOINED_EVENTS + " Could not retrieve Events: " + ex.getMessage());
			throw ex;
		}

		List<Object[]> events = aQuery.getResultList();
		for (Object[] item : events) {
			eventsBaseInfoVO = populateEventsBaseInfoVO(item);
			response.add(eventsBaseInfoVO);
		}
		return response;
	}

	/**
	 * Join Event
	 * @param data EventsActionsVO Join Event Action Value Object
	 * @return Integer Number of Events Joined 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer joinEvent(EventsActionsVO data) throws Exception {
		Integer response = null;

		try {
			if (StringUtils.hasText(data.getEid()) && StringUtils.hasText(data.getSid())) {
				//Counting if the user joined the event or not
				Long count = (Long) eventsEM.createNativeQuery(COUNT_QUERY)
						.setParameter("eid", Long.parseLong(data.getEid()))
						.setParameter("sid", Long.parseLong(data.getSid())).getSingleResult();
				if (count == 0) {
					//Joining the event
					response = eventsEM.createNativeQuery(JOIN_QUERY).setParameter("eid", Long.parseLong(data.getEid()))
							.setParameter("sid", Long.parseLong(data.getSid())).executeUpdate();
					//Counting if the user has a ranking
					Long rankingCount = (Long) eventsEM.createNativeQuery(COUNT_RANKING_QUERY)
							.setParameter("sid", Long.parseLong(data.getSid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter())).getSingleResult();
					if (rankingCount == 0) {
						//Insert a new ranking if the user does not have one
						Integer rankingResult = eventsEM.createNativeQuery(INSERT_RANKING_QUERY)
								.setParameter("sid", Long.parseLong(data.getSid()))
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
					}
					//Get the minimum number of points of the event
					Integer minPoints = ((Short) eventsEM.createNativeQuery(MIN_POINTS_QUERY)
							.setParameter("eid", Long.parseLong(data.getEid())).getSingleResult()).intValue();
					//Get the amount of points earned accumulated
					Integer pointsEarned = ((Short) eventsEM.createNativeQuery(GET_POINTS_QUERY)
							.setParameter("sid", Long.parseLong(data.getSid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter()))
							.getSingleResult()).intValue();
					//Add it by the minimum points of the event
					pointsEarned += minPoints;
					//Update the ranking of the user
					Integer pointResult = eventsEM.createNativeQuery(SET_POINTS_QUERY)
							.setParameter("pointsEarned", pointsEarned.shortValue())
							.setParameter("sid", Long.parseLong(data.getSid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
				} else {
					LOG.error(ERROR_MESSAGE_JOIN_EVENT + " Student cannot join the same Event twice");
					throw new AppException("Student cannot join the same Event twice");
				}
			} else {
				LOG.error(ERROR_MESSAGE_JOIN_EVENT + MISSING_MESSAGE + "eid or sid" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "eid or sid");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_JOIN_EVENT + " Could not join Event: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_JOIN_EVENT + " Could not join Event: " + ex.getMessage());
			throw ex;
		}

		return response;
	}
	
	/**
	 * Leave Event
	 * @param data EventsActionsVO Leave Event Action Value Object
	 * @return Integer Number of events left
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer leaveEvent(EventsActionsVO data) throws Exception {
		Integer response = null;

		try {
			if (StringUtils.hasText(data.getEid()) && StringUtils.hasText(data.getSid())) {
				response = eventsEM.createNativeQuery(LEAVE_QUERY).setParameter("eid", Long.parseLong(data.getEid()))
						.setParameter("sid", Long.parseLong(data.getSid())).executeUpdate();
				Integer minPoints = ((Short) eventsEM.createNativeQuery(MIN_POINTS_QUERY)
						.setParameter("eid", Long.parseLong(data.getEid())).getSingleResult()).intValue();
				Integer pointsEarned = ((Short) eventsEM.createNativeQuery(GET_POINTS_QUERY)
						.setParameter("sid", Long.parseLong(data.getSid()))
						.setParameter("schoolYear", data.getSchoolYear())
						.setParameter("quarter", Short.parseShort(data.getQuarter())).getSingleResult()).intValue();
				pointsEarned -= minPoints;
				if (pointsEarned == 0) {
					Integer rankingDelete = eventsEM.createNativeQuery(DELETE_RANKING_QUERY)
							.setParameter("sid", Long.parseLong(data.getSid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
				}
				else {
					Integer pointResult = eventsEM.createNativeQuery(SET_POINTS_QUERY)
							.setParameter("pointsEarned", pointsEarned.shortValue())
							.setParameter("sid", Long.parseLong(data.getSid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
				}
			} else {
				LOG.error(ERROR_MESSAGE_LEAVE_EVENT + MISSING_MESSAGE + "eid or sid" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "eid or sid");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_LEAVE_EVENT + " Could not leave Event: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_LEAVE_EVENT + " Could not leave Event: " + ex.getMessage());
			throw ex;
		}

		return response;
	}

	/**
	 * Add Event
	 * @param data EventsAddVO Add Event Value Object
	 * @return Integer Number of events added
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer addEvent(EventsAddVO data) throws Exception {
		Integer response = null;

		try {
			if (StringUtils.hasText(data.getName()) && StringUtils.hasText(data.getDescription())
					&& StringUtils.hasText(data.getMinPoints()) && StringUtils.hasText(data.getMaxPoints())
					&& StringUtils.hasText(data.getStartDate()) && StringUtils.hasText(data.getEndDate())
					&& StringUtils.hasText(data.getCid()) && StringUtils.hasText(data.getSchoolYear())
					&& StringUtils.hasText(data.getQuarter())) {
				if (Integer.parseInt(data.getMinPoints()) < 5 && Integer.parseInt(data.getMaxPoints()) < 10
						&& Integer.parseInt(data.getMaxPoints()) >= Integer.parseInt(data.getMinPoints())) {
					response = eventsEM.createNativeQuery(ADD_QUERY).setParameter("name", data.getName())
							.setParameter("description", data.getDescription())
							.setParameter("minPoints", Short.parseShort(data.getMinPoints()))
							.setParameter("maxPoints", Short.parseShort(data.getMaxPoints()))
							.setParameter("startDate", data.getStartDate()).setParameter("endDate", data.getEndDate())
							.setParameter("cid", Long.parseLong(data.getCid()))
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
				} else {
					LOG.error(ERROR_MESSAGE_ADD_EVENT + " minPoints < 5, maxPoints < 10, minPoints <= maxPoints");
					throw new AppException("minPoints < 5, maxPoints < 10, minPoints <= maxPoints");
				}
			} else {
				LOG.error(ERROR_MESSAGE_ADD_EVENT + MISSING_MESSAGE + "required values" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "required values");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_ADD_EVENT + " Could not add Event: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_ADD_EVENT + " Could not add Event: " + ex.getMessage());
			throw ex;
		}

		return response;
	}

	/**
	 * Update Event
	 * @param data EventsUpdateVO Event Update Value Object
	 * @return Integer Number of events updated
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer updateEvent(EventsUpdateVO data) throws Exception {
		Integer response = null;

		try {
			if (StringUtils.hasText(data.getEid()) && StringUtils.hasText(data.getName()) && StringUtils.hasText(data.getDescription())
					&& StringUtils.hasText(data.getMinPoints()) && StringUtils.hasText(data.getMaxPoints())
					&& StringUtils.hasText(data.getStartDate()) && StringUtils.hasText(data.getEndDate())
					&& StringUtils.hasText(data.getSchoolYear()) && StringUtils.hasText(data.getQuarter())) {
				if (Integer.parseInt(data.getMinPoints()) < 5 && Integer.parseInt(data.getMaxPoints()) < 10
						&& Integer.parseInt(data.getMaxPoints()) >= Integer.parseInt(data.getMinPoints())) {
					response = eventsEM.createNativeQuery(UPDATE_QUERY)
							.setParameter("eid", Long.parseLong(data.getEid()))
							.setParameter("name", data.getName())
							.setParameter("description", data.getDescription())
							.setParameter("minPoints", Short.parseShort(data.getMinPoints()))
							.setParameter("maxPoints", Short.parseShort(data.getMaxPoints()))
							.setParameter("startDate", data.getStartDate())
							.setParameter("endDate", data.getEndDate())
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
				} else {
					LOG.error(ERROR_MESSAGE_UPDATE_EVENT + " minPoints < 5, maxPoints < 10, minPoints <= maxPoints");
					throw new AppException("minPoints < 5, maxPoints < 10, minPoints <= maxPoints");
				}
			} else {
				LOG.error(ERROR_MESSAGE_UPDATE_EVENT + MISSING_MESSAGE + "required values" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "required values");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_UPDATE_EVENT + " Could not update Event: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_UPDATE_EVENT + " Could not update Event: " + ex.getMessage());
			throw ex;
		}

		return response;
	}
	
	/**
	 * Get the list of Students participating in the Event to be deleted
	 * @param data EventsDeleteVO Value Object of Event to be deleted
	 * @return List<EventsStudentsVO> List of students who has joined the Event to be deleted
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public List<EventsStudentsVO> getJoinedStudents(EventsDeleteVO data) throws Exception {
		Query aQuery = null;
		EventsStudentsVO eventsStudentsVO = null;
		List<EventsStudentsVO> response = new LinkedList<>();
		
		try {
			if (StringUtils.hasText(data.getEid())) {
				
				aQuery = eventsEM.createNativeQuery(GET_JOINED_QUERY)
						.setParameter("eid", Long.parseLong(data.getEid()));
			} else {
				LOG.error(ERROR_MESSAGE_GET_JOINED_STUDENTS + MISSING_MESSAGE + "eid" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "eid");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_GET_JOINED_STUDENTS + " Could not retrieve students who joined the Events: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_GET_JOINED_STUDENTS + " Could not delete Event: " + ex.getMessage());
			throw ex;
		}
		
		List<Object[]> students = aQuery.getResultList();
		for (Object[] item : students) {
			eventsStudentsVO = populateEventsStudentsVO(item);
			response.add(eventsStudentsVO);
		}
		return response;
	}

	/**
	 * Delete Event
	 * @param data EventsDeleteVO Delete Event Value Object
	 * @return Integer Number of events deleted
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer deleteEvent(EventsDeleteVO data) throws Exception {
		Integer response = null;

		try {
			if (StringUtils.hasText(data.getEid())) {
				List<EventsStudentsVO> joinedStudents = getJoinedStudents(data);
				if (joinedStudents.size() == 0) {
					response = eventsEM.createNativeQuery(DELETE_QUERY).setParameter("eid", Long.parseLong(data.getEid()))
							.executeUpdate();
				}
				else {
					LOG.error(ERROR_MESSAGE_DELETE_EVENT + " You cannot delete this Event as there are students who have already joined it!");
					throw new AppException(MISSING_MESSAGE + "eid");
				}
			} else {
				LOG.error(ERROR_MESSAGE_DELETE_EVENT + MISSING_MESSAGE + "eid" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "eid");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_DELETE_EVENT + " Could not delete Event: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_DELETE_EVENT + " Could not delete Event: " + ex.getMessage());
			throw ex;
		}

		return response;
	}

	/**
	 * Populate Events Base Information Value Object
	 * @param item Object[] Array of Objects of event base information
	 * @return EventsBaseInfoVO Event Base Information Value Object
	 */
	private EventsBaseInfoVO populateEventsBaseInfoVO(Object[] item) {
		EventsBaseInfoVO eventsBaseInfoVO = new EventsBaseInfoVO();

		eventsBaseInfoVO.setEid(item[0] != null ? (Long) item[0] : 0);
		eventsBaseInfoVO.setEventName(item[1] != null ? (String) item[1] : "");
		eventsBaseInfoVO.setDescription(item[2] != null ? (String) item[2] : "");
		eventsBaseInfoVO.setMinPoints(item[3] != null ? ((Short) item[3]).intValue() : 0);
		eventsBaseInfoVO.setMaxPoints(item[4] != null ? ((Short) item[4]).intValue() : 0);
		eventsBaseInfoVO.setStartDate(item[5] != null ? (Date) item[5] : new Date());
		eventsBaseInfoVO.setEndDate(item[6] != null ? (Date) item[6] : new Date());
		eventsBaseInfoVO.setClubName(item[7] != null ? (String) item[7] : "");
		eventsBaseInfoVO.setSchoolYear(item[8] != null ? (String) item[8] : "");
		eventsBaseInfoVO.setQuarter(item[9] != null ? ((Short) item[9]).intValue() : 0);

		return eventsBaseInfoVO;
	}

	/**
	 * Populate Events Students Value Object
	 * @param item Object[] Array of Objects of student information
	 * @return EventsStudentsVO Events students value object
	 */
	private EventsStudentsVO populateEventsStudentsVO(Object[] item) {
		EventsStudentsVO eventsStudentsVO = new EventsStudentsVO();

		eventsStudentsVO.setFirstName(item[0] != null ? (String) item[0] : "");
		eventsStudentsVO.setLastName(item[1] != null ? (String) item[1] : "");
		eventsStudentsVO.setEmail(item[2] != null ? (String) item[2] : "");
		
		return eventsStudentsVO;
	}
}
