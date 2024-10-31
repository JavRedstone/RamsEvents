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
import com.jav.ramsevents.model.PrizesBaseInfoVO;
import com.jav.ramsevents.model.PrizesSearchVO;

/**
 * Prize Repository
 * @author Javier Huang
 */
@CrossOrigin
@Repository
public class PrizesRepository {
	private final static Logger LOG = LoggerFactory.getLogger(RankingRepository.class);

	private static final String PRIZES_QUERY = "SELECT p.id AS pid, p.name, p.description, p.type, p.min_points FROM ramsevents.prizes p JOIN ramsevents.quarters q ON p.fk_quarter_id = q.id WHERE q.school_year = :schoolYear AND q.quarter = :quarter ORDER BY p.min_points DESC";

	private static final String ERROR_MESSAGE_SEARCH_PRIZES = "Error encountered in PrizesRepository(searchPrizes(PrizesSearchVO data)).";
	private static final String MISSING_MESSAGE = " Missing search criteria: ";
	private static final String COULDNOTRETRIEVE_MESSAGE = ", could not retrieve Prizes";

	@PersistenceContext(unitName = "postgre")
	private EntityManager prizesEM;

	/**
	 * Search Prizes
	 * @param data PrizesSearchVO Prizes Search Value Object
	 * @return List<PrizesBaseInfoVO> List of Prize Base Information Value Objects
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, transactionManager = "postgreTransactionManager")
	public List<PrizesBaseInfoVO> searchPrizes(PrizesSearchVO data) throws Exception {
		Query aQuery = null;
		PrizesBaseInfoVO prizesInfoVO = null;
		List<PrizesBaseInfoVO> response = new LinkedList<>();

		try {
			if (StringUtils.hasText(data.getSchoolYear()) && StringUtils.hasText(data.getQuarter())) {
				aQuery = prizesEM.createNativeQuery(PRIZES_QUERY)
						.setParameter("schoolYear", data.getSchoolYear())
						.setParameter("quarter", Short.parseShort(data.getQuarter()));
			}
			else {
				LOG.error(ERROR_MESSAGE_SEARCH_PRIZES + MISSING_MESSAGE + "schoolYear or quarter" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "schoolYear or quarter");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_SEARCH_PRIZES + " Could not retrieve Prizes: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_SEARCH_PRIZES + " Could not retrieve Prizes: " + ex.getMessage());
			throw ex;
		}

		List<Object[]> prizes = aQuery.getResultList();
		for (Object[] item : prizes) {
			prizesInfoVO = populatePrizesInfoVO(item);
			response.add(prizesInfoVO);
		}
		return response;
	}

	/**
	 * Populate Prize Information Value Object 
	 * @param item Array of Objects of prize information
	 * @return PrizesBaseInfoVO Prize Information Value Object
	 */
	private PrizesBaseInfoVO populatePrizesInfoVO(Object[] item) {
		PrizesBaseInfoVO prizesBaseInfoVO = new PrizesBaseInfoVO();
		prizesBaseInfoVO.setPid(item[0] != null ? (Long) item[0] : 0L);
		prizesBaseInfoVO.setName(item[1] != null ? (String) item[1] : "");
		prizesBaseInfoVO.setDescription(item[2] != null ? (String) item[2] : "");
		prizesBaseInfoVO.setType(item[3] != null ? (String) item[3] : "");
		prizesBaseInfoVO.setMinPoints(item[4] != null ? (Short) item[4] : 0);
		
		return prizesBaseInfoVO;
	}
}
