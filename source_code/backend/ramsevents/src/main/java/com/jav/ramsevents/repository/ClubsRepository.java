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
import com.jav.ramsevents.model.ClubsBaseInfoVO;
import com.jav.ramsevents.model.ClubsSearchVO;

/**
 * Clubs Repository
 * 
 * @author Javier Huang
 */
@CrossOrigin
@Repository
public class ClubsRepository {
	private final static Logger LOG = LoggerFactory.getLogger(RankingRepository.class);

	private static final String CLUBS_QUERY = "SELECT c.id AS cid, c.name AS club_name, c.description, s.id AS leader_id, s.first_name, s.last_name, s.email FROM ramsevents.clubs c JOIN ramsevents.students s ON c.fk_leader_id = s.id";
	private static final String WHERE_QUERY = " WHERE ";
	private static final String AND_QUERY = " AND ";
	private static final String BY_CLUBNAME_QUERY = "LOWER(c.name) LIKE '%' || :clubName || '%'";
	private static final String BY_DESCRIPTION_QUERY = "LOWER(c.description) LIKE '%' || :description || '%'";
	private static final String BY_FIRSTNAME_QUERY = "LOWER(s.first_name) LIKE '%' || :firstName || '%'";
	private static final String BY_LASTNAME_QUERY = "LOWER(s.last_name) LIKE '%' || :lastName || '%'";
	private static final String BY_LEADERID_QUERY = "s.id = :leaderId";
	private static final String ORDER_BY_QUERY = " ORDER BY c.name";

	private static final String ERROR_MESSAGE = "Error encountered in ClubsRepository(searchClubs(ClubsSearchVO data)).";
	private static final String MISSING_MESSAGE = " Missing search criteria: ";
	private static final String COULDNOTRETRIEVE_MESSAGE = ", could not retrieve Rankings";

	@PersistenceContext(unitName = "postgre")
	private EntityManager clubsEM;

	/**
	 * Search Clubs
	 * @param data ClubsSearchVO Clubs Search Criteria
	 * @return List<ClubsBaseInfoVO> List of Clubs Info Value Objects
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, transactionManager = "postgreTransactionManager")
	public List<ClubsBaseInfoVO> searchClubs(ClubsSearchVO data) throws Exception {
		Query aQuery = null;
		ClubsBaseInfoVO clubsBaseInfoVO = null;
		List<ClubsBaseInfoVO> response = new LinkedList<>();

		try {
			if (ClubsSearchVO.ALL.equalsIgnoreCase(data.getSearchType())) {
				aQuery = clubsEM.createNativeQuery(CLUBS_QUERY + ORDER_BY_QUERY);
			} else if (ClubsSearchVO.BY_CLUBNAME.equalsIgnoreCase(data.getSearchType())) {
				if (StringUtils.hasText(data.getClubName())) {
					aQuery = clubsEM
							.createNativeQuery(CLUBS_QUERY + WHERE_QUERY + BY_CLUBNAME_QUERY + ORDER_BY_QUERY)
							.setParameter("clubName", data.getClubName().toLowerCase());
				} else {
					LOG.error(ERROR_MESSAGE + MISSING_MESSAGE + "clubName" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "clubName");
				}
			} else if (ClubsSearchVO.BY_DESCRIPTION.equalsIgnoreCase(data.getSearchType())) {
				if (StringUtils.hasText(data.getDescription())) {
					aQuery = clubsEM.createNativeQuery(CLUBS_QUERY + WHERE_QUERY + BY_DESCRIPTION_QUERY + ORDER_BY_QUERY)
							.setParameter("description", data.getDescription().toLowerCase());
				} else {
					LOG.error(ERROR_MESSAGE + MISSING_MESSAGE + "description" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "description");
				}
			} else if (ClubsSearchVO.BY_LEADERNAME.equalsIgnoreCase(data.getSearchType())) {
				if (StringUtils.hasText(data.getFirstName()) && !StringUtils.hasText(data.getLastName())) {
					aQuery = clubsEM.createNativeQuery(CLUBS_QUERY + WHERE_QUERY + BY_FIRSTNAME_QUERY + ORDER_BY_QUERY)
							.setParameter("firstName", data.getFirstName().toLowerCase());
				} else if (!StringUtils.hasText(data.getFirstName()) && StringUtils.hasText(data.getLastName())) {
					aQuery = clubsEM.createNativeQuery(CLUBS_QUERY + WHERE_QUERY + BY_LASTNAME_QUERY + ORDER_BY_QUERY)
							.setParameter("lastName", data.getLastName().toLowerCase());
				} else if (StringUtils.hasText(data.getFirstName()) && StringUtils.hasText(data.getLastName())) {
					aQuery = clubsEM
							.createNativeQuery(CLUBS_QUERY + WHERE_QUERY + BY_FIRSTNAME_QUERY + AND_QUERY
									+ BY_LASTNAME_QUERY + ORDER_BY_QUERY)
							.setParameter("firstName", data.getFirstName().toLowerCase())
							.setParameter("lastName", data.getLastName().toLowerCase());
				} else {
					LOG.error(ERROR_MESSAGE + MISSING_MESSAGE + "leaderName" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "leaderName");
				}
			} else if (ClubsSearchVO.BY_LEADERID.equalsIgnoreCase(data.getSearchType())) {
				if (StringUtils.hasText(data.getLeaderId())) {
					aQuery = clubsEM.createNativeQuery(CLUBS_QUERY + WHERE_QUERY + BY_LEADERID_QUERY + ORDER_BY_QUERY)
							.setParameter("leaderId", Long.parseLong(data.getLeaderId()));
				} else {
					LOG.error(ERROR_MESSAGE + MISSING_MESSAGE + "leaderId" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "leaderId");
				}
			} else {
				LOG.error(ERROR_MESSAGE + MISSING_MESSAGE + "search type" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "search type");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE + " Could not retrieve Clubs: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE + " Could not retrieve Clubs: " + ex.getMessage());
			throw ex;
		}

		List<Object[]> clubs = aQuery.getResultList();
		for (Object[] item : clubs) {
			clubsBaseInfoVO = populateClubsInfoVO(item);
			response.add(clubsBaseInfoVO);
		}
		return response;
	}

	/**
	 * Populate Clubs Information Value Object
	 * @param item Object[] Array of Object of club information
	 * @return ClubsBaseInfoVO
	 */
	private ClubsBaseInfoVO populateClubsInfoVO(Object[] item) {
		ClubsBaseInfoVO clubsBaseInfoVO = new ClubsBaseInfoVO();
		clubsBaseInfoVO.setCid(item[0] != null ? (Long) item[0] : 0);
		clubsBaseInfoVO.setClubName(item[1] != null ? (String) item[1] : "");
		clubsBaseInfoVO.setDescription(item[2] != null ? (String) item[2] : "");
		clubsBaseInfoVO.setLeaderId(item[3] != null ? (Long) item[3] : 0);
		clubsBaseInfoVO.setFirstName(item[4] != null ? (String) item[4] : "");
		clubsBaseInfoVO.setLastName(item[5] != null ? (String) item[5] : "");
		clubsBaseInfoVO.setEmail(item[6] != null ? (String) item[6] : "");
		
		return clubsBaseInfoVO;
	}
}
