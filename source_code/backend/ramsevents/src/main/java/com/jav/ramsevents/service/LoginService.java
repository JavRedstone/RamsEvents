package com.jav.ramsevents.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jav.ramsevents.model.LoginBaseInfoVO;
import com.jav.ramsevents.model.LoginSearchVO;
import com.jav.ramsevents.model.LoginSignupVO;
import com.jav.ramsevents.repository.LoginRepository;

/**
 * Login services
 * 
 * @author Javier Huang
 */
@Service()
public class LoginService {
	public static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private LoginRepository loginRepository;
	
	/**
	 * Login service
	 * @param data LoginSearchVO Login Search Value Object
	 * @return List<LoginBaseInfoVO> List of Login Base Information Value Object
	 * @throws Exception
	 */
	public List<LoginBaseInfoVO> login(LoginSearchVO data) throws Exception {
		return loginRepository.login(data);
	}

	/**
	 * Sign up service
	 * @param data LoginSignupVO Login Sign up Value Object
	 * @return Integer Number of signed up students
	 * @throws Exception
	 */
	public Integer signup(LoginSignupVO data) throws Exception {
		return loginRepository.signup(data);
	}
}
