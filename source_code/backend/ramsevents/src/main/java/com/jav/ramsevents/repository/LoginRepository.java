package com.jav.ramsevents.repository;

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
import com.jav.ramsevents.model.LoginBaseInfoVO;
import com.jav.ramsevents.model.LoginSearchVO;
import com.jav.ramsevents.model.LoginSignupVO;

/**
 * Login Repository
 * @author Javier Huang
 */
@CrossOrigin
@Repository
public class LoginRepository {
	private final static Logger LOG = LoggerFactory.getLogger(LoginRepository.class);

	private static final String LOGIN_QUERY = "SELECT s.id AS sid, s.student_id, s.first_name, s.middle_name, s.last_name, s.email, s.grade, s.homeroom, g.name AS group_name, g.description AS group_description, g.access_level FROM ramsevents.students s JOIN ramsevents.groups g ON s.fk_group_id = g.id WHERE s.email = :email";
	private static final String SIGNUP_QUERY = "INSERT INTO ramsevents.students VALUES (NEXTVAL('ramsevents.student_sequence'), :uid, :studentId, :firstName, :middleName, :lastName, :email, :grade, :homeroom, localtimestamp, localtimestamp, (SELECT id FROM ramsevents.groups WHERE access_level = :accessLevel))";
	
	private static final String ERROR_MESSAGE_LOGIN = "Error encountered in LoginRepository(login(LoginSearchVO data)).";
	private static final String ERROR_MESSAGE_SIGNUP = "Error encountered in LoginRepository(signup(LoginSignupVO data)).";
	private static final String MISSING_MESSAGE = " Missing search criteria: ";
	private static final String COULDNOTRETRIEVE_MESSAGE = ", could not retrieve Login";

	@PersistenceContext(unitName = "postgre")
	private EntityManager loginEM;

	/**
	 * Login
	 * @param data LoginSearchVO Login Search Value Object
	 * @return List<LoginBaseInfoVO> List of Login Base Information Value Object
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, transactionManager = "postgreTransactionManager")
	public List<LoginBaseInfoVO> login(LoginSearchVO data) throws Exception {
		Query aQuery = null;
		LoginBaseInfoVO loginInfoVO = null;
		List<LoginBaseInfoVO> response = new LinkedList<>();

		try {
			if (StringUtils.hasText(data.getEmail())) {
				aQuery = loginEM.createNativeQuery(LOGIN_QUERY)
						.setParameter("email", data.getEmail());
			} else {
				LOG.error(ERROR_MESSAGE_LOGIN + MISSING_MESSAGE + "email" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "email");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_LOGIN + " Could not retrieve students who login: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_LOGIN + " Could not retrieve students who login: " + ex.getMessage());
			throw ex;
		}

		List<Object[]> login = aQuery.getResultList();
		for (Object[] item : login) {
			loginInfoVO = populateLoginInfoVO(item);
			response.add(loginInfoVO);
		}
		return response;
	}
	
	/**
	 * Sign UP
	 * @param data LoginSignupVO Login Sign up Value Object
	 * @return Integer Number of signed up students 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer signup(LoginSignupVO data) throws Exception {
		Integer response = null;

		try {
			if (StringUtils.hasText(data.getUid()) && StringUtils.hasText(data.getStudentId()) && StringUtils.hasText(data.getFirstName()) && StringUtils.hasText(data.getLastName()) && StringUtils.hasText(data.getEmail()) && StringUtils.hasText(data.getGrade()) && StringUtils.hasText(data.getHomeroom()) && StringUtils.hasText(data.getAccessLevel())) {
				response = loginEM.createNativeQuery(SIGNUP_QUERY)
						.setParameter("uid", data.getUid())
						.setParameter("studentId", Integer.parseInt(data.getStudentId()))
						.setParameter("firstName", data.getFirstName())
						.setParameter("middleName", data.getMiddleName())
						.setParameter("lastName", data.getLastName())
						.setParameter("email", data.getEmail())
						.setParameter("grade", Short.parseShort(data.getGrade()))
						.setParameter("homeroom", data.getHomeroom())
						.setParameter("accessLevel", data.getAccessLevel()).executeUpdate();
			} else {
				LOG.error(ERROR_MESSAGE_SIGNUP + MISSING_MESSAGE + "required values" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "required values");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_SIGNUP + " Could not signup: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_SIGNUP + " Could not signup: " + ex.getMessage());
			throw ex;
		}

		return response;
	}

	/**
	 * Populate Login Information Value Object
	 * @param item Object[] Array of Objects of Login information
	 * @return
	 */
	private LoginBaseInfoVO populateLoginInfoVO(Object[] item) {
		LoginBaseInfoVO loginInfoVO = new LoginBaseInfoVO();
		
		loginInfoVO.setSid(item[0] != null ? (Long) item[0] : 0);
		loginInfoVO.setStudentId(item[1] != null ? (Integer) item[1] : 0);
		loginInfoVO.setFirstName(item[2] != null ? (String) item[2] : "");
		loginInfoVO.setMiddleName(item[3] != null ? (String) item[3] : "");
		loginInfoVO.setLastName(item[4] != null ? (String) item[4] : "");
		loginInfoVO.setEmail(item[5] != null ? (String) item[5] : "");
		loginInfoVO.setGrade(item[6] != null ? ((Short) item[6]).intValue() : 0);
		loginInfoVO.setHomeroom(item[7] != null ? ((Character) item[7]).toString() : "" );
		loginInfoVO.setGroupName(item[8] != null ? (String) item[8] : "");
		loginInfoVO.setGroupDescription(item[9] != null ? (String) item[9] : "");
		loginInfoVO.setAccessLevel(item[10] != null ? (String) item[10] : "");
		
		return loginInfoVO;
	}
}
