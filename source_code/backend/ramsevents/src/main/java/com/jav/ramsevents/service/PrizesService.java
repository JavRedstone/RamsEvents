package com.jav.ramsevents.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jav.ramsevents.model.PrizesBaseInfoVO;
import com.jav.ramsevents.model.PrizesSearchVO;
import com.jav.ramsevents.repository.PrizesRepository;

/**
 * Prizes services
 * 
 * @author Javier Huang
 */
@Service()
public class PrizesService {
	public static final Logger LOG = LoggerFactory.getLogger(PrizesService.class);
	
	@Autowired
	private PrizesRepository prizesRepository;
	
	/**
	 * Search prizes service
	 * @param data PrizesSearchVO Prizes Search Value Object
	 * @return List<PrizesBaseInfoVO List of Prize Base Information Value Objects
	 * @throws Exception
	 */
	public List<PrizesBaseInfoVO> searchPrizes(PrizesSearchVO data) throws Exception {
		return prizesRepository.searchPrizes(data);
	}
}
