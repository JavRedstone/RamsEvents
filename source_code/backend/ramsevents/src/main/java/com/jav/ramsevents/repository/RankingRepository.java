package com.jav.ramsevents.repository;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.jav.ramsevents.exception.AppException;
import com.jav.ramsevents.model.GrantPrizesVO;
import com.jav.ramsevents.model.PrizesBaseInfoVO;
import com.jav.ramsevents.model.PrizesSearchVO;
import com.jav.ramsevents.model.RankingBaseInfoVO;
import com.jav.ramsevents.model.RankingSearchVO;

/**
 * Ranking repository
 * 
 * @author Javier Huang
 */
@CrossOrigin
@Repository
public class RankingRepository {
	private final static Logger LOG = LoggerFactory.getLogger(RankingRepository.class);

	private static final String RANKINGS_QUERY = "SELECT s.id AS sid, s.student_id, s.first_name, s.last_name, s.grade, pt.points_earned, pt.is_winner, pt.is_random, p.name AS prize_name, p.description AS prize_description, q.school_year, q.quarter FROM (((ramsevents.point_trackings pt JOIN ramsevents.students s ON pt.fk_student_id = s.id) LEFT JOIN ramsevents.prizes p ON pt.fk_prize_id = p.id) JOIN ramsevents.quarters q ON pt.fk_quarter_id = q.id) WHERE q.school_year = :schoolYear AND q.quarter = :quarter";
	private static final String AND_QUERY = " AND ";
	private static final String BY_FIRSTNAME_QUERY = "LOWER(s.first_name) LIKE '%' || :firstName || '%'";
	private static final String BY_LASTNAME_QUERY = "LOWER(s.last_name) LIKE '%' || :lastName || '%'";
	private static final String BY_STUDENTID_QUERY = "CAST(s.student_id as VARCHAR(7)) LIKE '%' || :studentId || '%'";
	private static final String BY_GRADE_QUERY = "s.grade = :grade";
	private static final String BY_POINTS_QUERY = "pt.points_earned >= :pointsEarned";
	private static final String ORDER_BY_QUERY = " ORDER BY pt.points_earned DESC, s.grade, s.first_name, s.last_name";

	//Granting prizes
	private static final String CLEAR_PRIZES_QUERY = "UPDATE ramsevents.point_trackings SET fk_prize_id = null, is_winner = false, is_random = false WHERE fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	private static final String TOP_STUDENT_QUERY = "SELECT s.id AS sid, pt.points_earned FROM ramsevents.point_trackings pt JOIN ramsevents.students s ON pt.fk_student_id = s.id WHERE points_earned = (SELECT MAX(points_earned) FROM ramsevents.point_trackings WHERE fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)) AND fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	private static final String TOP_SID_QUERY = "SELECT s.id FROM ramsevents.point_trackings pt JOIN ramsevents.students s ON pt.fk_student_id = s.id WHERE points_earned = (SELECT MAX(points_earned) FROM ramsevents.point_trackings WHERE fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)) AND fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";
	private static final String LIST_BY_GRADE_QUERY = "SELECT s.id AS sid, pt.points_earned FROM ramsevents.point_trackings pt JOIN ramsevents.students s ON pt.fk_student_id = s.id WHERE fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter) AND s.grade = :grade AND fk_student_id NOT IN (" + TOP_SID_QUERY + ")";
	private static final String UPDATE_STUDENT_PRIZE_QUERY = "UPDATE ramsevents.point_trackings SET fk_prize_id = :pid, is_winner = :isWinner, is_random = :isRandom WHERE fk_student_id = :sid AND fk_quarter_id = (SELECT id FROM ramsevents.quarters WHERE school_year = :schoolYear AND quarter = :quarter)";

	private static final String ERROR_MESSAGE_SEARCH_RANKINGS = "Error encountered in RankingRepository(searchRankings(RankingSearchVO data)).";
	private static final String ERROR_MESSAGE_GRANT_PRIZES = "Error encountered in RankingRepository(grantPrizes(GrantPrizesVO data)).";

	private static final String MISSING_MESSAGE = " Missing search criteria: ";
	private static final String COULDNOTRETRIEVE_MESSAGE = ", could not retrieve Rankings";

	@PersistenceContext(unitName = "postgre")
	private EntityManager rankingsEM;

	@Autowired
	private PrizesRepository prizesRepository;

	/**
	 * Search Rankings
	 * @param data RankingSearchVO Rankings Search Criteria
	 * @return List<RankingBaseInfoVO> List of Ranking Information Value Objects
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS, transactionManager = "postgreTransactionManager")
	public List<RankingBaseInfoVO> searchRankings(RankingSearchVO data) throws Exception {
		Query aQuery = null;
		RankingBaseInfoVO rankingBaseInfoVO = null;
		List<RankingBaseInfoVO> response = new LinkedList<>();

		try {
			if (StringUtils.hasText(data.getSchoolYear()) && StringUtils.hasText(data.getQuarter())) {
				if (RankingSearchVO.ALL.equalsIgnoreCase(data.getSearchType())) {
					aQuery = rankingsEM.createNativeQuery(RANKINGS_QUERY + ORDER_BY_QUERY)
							.setParameter("schoolYear", data.getSchoolYear())
							.setParameter("quarter", Short.parseShort(data.getQuarter()));
				} else if (RankingSearchVO.BY_NAME.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getFirstName()) && !StringUtils.hasText(data.getLastName())) {
						aQuery = rankingsEM
								.createNativeQuery(RANKINGS_QUERY + AND_QUERY + BY_FIRSTNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("firstName", data.getFirstName().toLowerCase());
					} else if (!StringUtils.hasText(data.getFirstName()) && StringUtils.hasText(data.getLastName())) {
						aQuery = rankingsEM
								.createNativeQuery(RANKINGS_QUERY + AND_QUERY + BY_LASTNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("lastName", data.getLastName().toLowerCase());
					} else if (StringUtils.hasText(data.getFirstName()) && StringUtils.hasText(data.getLastName())) {
						aQuery = rankingsEM
								.createNativeQuery(RANKINGS_QUERY + AND_QUERY + BY_FIRSTNAME_QUERY + AND_QUERY
										+ BY_LASTNAME_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("firstName", data.getFirstName().toLowerCase())
								.setParameter("lastName", data.getLastName().toLowerCase());
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + MISSING_MESSAGE + "name" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "name");
					}
				} else if (RankingSearchVO.BY_STUDENTID.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getStudentId())) {
						aQuery = rankingsEM
								.createNativeQuery(RANKINGS_QUERY + AND_QUERY + BY_STUDENTID_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("studentId", StringUtils.trimAllWhitespace(data.getStudentId()));
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + MISSING_MESSAGE + "studentId" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "studentId");
					}
				} else if (RankingSearchVO.BY_GRADE.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getGrade())) {
						aQuery = rankingsEM
								.createNativeQuery(RANKINGS_QUERY + AND_QUERY + BY_GRADE_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("grade", Integer.parseInt(data.getGrade()));
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + MISSING_MESSAGE + "grade" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "grade");
					}
				} else if (RankingSearchVO.BY_POINTSEARNED.equalsIgnoreCase(data.getSearchType())) {
					if (StringUtils.hasText(data.getPointsEarned())) {
						aQuery = rankingsEM
								.createNativeQuery(RANKINGS_QUERY + AND_QUERY + BY_POINTS_QUERY + ORDER_BY_QUERY)
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter()))
								.setParameter("pointsEarned", Integer.parseInt(data.getPointsEarned()));
					} else {
						LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + MISSING_MESSAGE + "points" + COULDNOTRETRIEVE_MESSAGE);
						throw new AppException(MISSING_MESSAGE + "points");
					}
				} else {
					LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + MISSING_MESSAGE + "search type" + COULDNOTRETRIEVE_MESSAGE);
					throw new AppException(MISSING_MESSAGE + "search type");
				}
			} else {
				LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + MISSING_MESSAGE + "schoolYear or quarter" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "schoolYear or quarter");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + " Could not retrieve Rankings: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_SEARCH_RANKINGS + " Could not retrieve Rankings: " + ex.getMessage());
			throw ex;
		}

		List<Object[]> rankings = aQuery.getResultList();
		for (Object[] item : rankings) {
			rankingBaseInfoVO = populateRankingInfoVO(item);
			response.add(rankingBaseInfoVO);
		}
		return response;
	}

	/**
	 * Grant Prizes
	 * @param data GrantPrizesVO Grant Prizes Value Object
	 * @return Integer Total number of students' prizes has been updated
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "postgreTransactionManager")
	public Integer grantPrizes(GrantPrizesVO data) throws Exception {
		Integer response = 0;

		try {
			if (StringUtils.hasText(data.getSchoolYear()) && StringUtils.hasText(data.getQuarter())) {
				//Clear prizes so that you don't keep assigning prizes to more and more students
				Integer clearPrizes = rankingsEM.createNativeQuery(CLEAR_PRIZES_QUERY)
						.setParameter("schoolYear", data.getSchoolYear())
						.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
				//Get all prizes
				PrizesSearchVO prizesSearchVO = new PrizesSearchVO();
				prizesSearchVO.setSchoolYear(data.getSchoolYear());
				prizesSearchVO.setQuarter(data.getQuarter());
				List<PrizesBaseInfoVO> prizes = prizesRepository.searchPrizes(prizesSearchVO);
				//Select top student prize; Get top student [id and points]
				List<Object[]> topStudents = rankingsEM.createNativeQuery(TOP_STUDENT_QUERY)
						.setParameter("schoolYear", data.getSchoolYear())
						.setParameter("quarter", Short.parseShort(data.getQuarter())).getResultList();
				if (topStudents != null && topStudents.size() > 0) {
					// Get the prize for the top student
					PrizesBaseInfoVO topPrize = getPrize(prizes, topStudents.get(0));

					// Update the prize for the top winner student
					for (Object[] topStudent : topStudents) {
						response += this.rankingsEM.createNativeQuery(UPDATE_STUDENT_PRIZE_QUERY)
								.setParameter("pid", topPrize.getPid()).setParameter("isWinner", true)
								.setParameter("isRandom", false).setParameter("sid", (Long) topStudent[0])
								.setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
					}

					// Select random student prize; Get list of all students for each grade [id and points]
					for (int grade = 9; grade <= 12; grade++) {
						List<Object[]> listByGrade = rankingsEM.createNativeQuery(LIST_BY_GRADE_QUERY)
								.setParameter("grade", grade).setParameter("schoolYear", data.getSchoolYear())
								.setParameter("quarter", Short.parseShort(data.getQuarter())).getResultList();
						if (listByGrade != null && listByGrade.size() > 0) {
							// Pick a random student for each grade
							Object[] randomStudent = listByGrade
									.get((int) Math.floor(Math.random() * listByGrade.size()));
							// Get the prize for the random students
							PrizesBaseInfoVO randomPrize = getPrize(prizes, randomStudent);
							response += this.rankingsEM.createNativeQuery(UPDATE_STUDENT_PRIZE_QUERY)
									// Update the prize for the random winner students
									.setParameter("pid", randomPrize.getPid()).setParameter("isWinner", false)
									.setParameter("isRandom", true).setParameter("sid", (Long) randomStudent[0])
									.setParameter("schoolYear", data.getSchoolYear())
									.setParameter("quarter", Short.parseShort(data.getQuarter())).executeUpdate();
						}
					}
				}
			} else {
				LOG.error(ERROR_MESSAGE_GRANT_PRIZES + MISSING_MESSAGE + "schoolYear or quarter" + COULDNOTRETRIEVE_MESSAGE);
				throw new AppException(MISSING_MESSAGE + "schoolYear or quarter");
			}
		} catch (AppException ae) {
			LOG.error(ERROR_MESSAGE_GRANT_PRIZES + " Could not grant Prizes: " + ae.getMessage());
			throw ae;
		} catch (Exception ex) {
			LOG.error(ERROR_MESSAGE_GRANT_PRIZES + " Could not grant Prizes: " + ex.getMessage());
			throw ex;
		}

		return response;
	}

	/**
	 * Choose the most relevant prize
	 * @param prizes List<PrizesBaseInfoVO> List of prizes
	 * @param student Object[] List of students
	 * @return PrizesBaseInfoVO The selected prize
	 */
	private PrizesBaseInfoVO getPrize(List<PrizesBaseInfoVO> prizes, Object[] student) {
		Short pointsEarned = (Short) student[1];
		PrizesBaseInfoVO selectedPrize = null;
		for (PrizesBaseInfoVO prize : prizes) {
			if (pointsEarned >= prize.getMinPoints()) {
				selectedPrize = prize;
				break;
			}
		}
		return selectedPrize;
	}
	
	/**
	 * Populate Ranking Information Value Object
	 * @param item Object[] Array of Object of ranking information
	 * @return RankingBaseInfoVO Ranking Information Value Object
	 */
	private RankingBaseInfoVO populateRankingInfoVO(Object[] item) {
		RankingBaseInfoVO rankingInfoVO = new RankingBaseInfoVO();
		rankingInfoVO.setSid(item[0] != null ? (Long) item[0] : 0);
		rankingInfoVO.setStudentId(item[1] != null ? (Integer) item[1] : 0);
		rankingInfoVO.setFirstName(item[2] != null ? (String) item[2] : "");
		rankingInfoVO.setLastName(item[3] != null ? (String) item[3] : "");
		rankingInfoVO.setGrade(item[4] != null ? ((Short) item[4]).intValue() : 0);
		rankingInfoVO.setPointsEarned(item[5] != null ? ((Short) item[5]).intValue() : 0);
		rankingInfoVO.setIsWinner(item[6] != null ? (Boolean) item[6] : false);
		rankingInfoVO.setIsRandom(item[7] != null ? (Boolean) item[7] : false);
		rankingInfoVO.setPrizeName(item[8] != null ? (String) item[8] : "");
		rankingInfoVO.setPrizeDescription(item[9] != null ? (String) item[9] : "");
		rankingInfoVO.setSchoolYear(item[10] != null ? (String) item[10] : "");
		rankingInfoVO.setQuarter(item[11] != null ? ((Short) item[11]).intValue() : 0);

		return rankingInfoVO;
	}
}
