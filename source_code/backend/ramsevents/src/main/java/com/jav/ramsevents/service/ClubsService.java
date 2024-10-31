package com.jav.ramsevents.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jav.ramsevents.model.ClubsBaseInfoVO;
import com.jav.ramsevents.model.ClubsSearchVO;
import com.jav.ramsevents.repository.ClubsRepository;

/**
 * Clubs services
 * 
 * @author Javier Huang
 */
@Service()
public class ClubsService {
	public static final Logger LOG = LoggerFactory.getLogger(ClubsService.class);
	
	@Autowired
	private ClubsRepository clubsRepository;
	
	/**
	 * Search clubs service
	 * @param data ClubsSearchVO Clubs Search Criteria
	 * @return List<ClubsBaseInfoVO> List of Clubs Info Value Objects
	 * @throws Exception
	 */
	public List<ClubsBaseInfoVO> searchClubs(ClubsSearchVO data) throws Exception {
		return clubsRepository.searchClubs(data);
	}
}
