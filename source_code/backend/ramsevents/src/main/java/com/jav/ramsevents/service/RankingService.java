package com.jav.ramsevents.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jav.ramsevents.model.GrantPrizesVO;
import com.jav.ramsevents.model.RankingBaseInfoVO;
import com.jav.ramsevents.model.RankingSearchVO;
import com.jav.ramsevents.repository.RankingRepository;

/**
 * Ranking services
 * 
 * @author Javier Huang
 */
@Service()
public class RankingService {
	public static final Logger LOG = LoggerFactory.getLogger(RankingService.class);

	@Autowired
	private RankingRepository rankingRepository;

	/**
	 * Search rankings service
	 * @param data RankingSearchVO Rankings Search Criteria
	 * @return List<RankingBaseInfoVO> List of Ranking Information Value Objects
	 * @throws Exception
	 */
	public List<RankingBaseInfoVO> searchRankings(RankingSearchVO data) throws Exception {
		return rankingRepository.searchRankings(data);
	}

	/**
	 * Grant prizes service
	 * @param data GrantPrizesVO Grant Prizes Value Object
	 * @return Integer Total number of students' prizes has been updated
	 * @throws Exception
	 */
	public Integer grantPrizes(GrantPrizesVO data) throws Exception {
		return rankingRepository.grantPrizes(data);
	}
}
