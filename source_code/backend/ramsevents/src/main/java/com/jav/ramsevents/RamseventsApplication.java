package com.jav.ramsevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Ramsevents application main class
 * 
 * @author Javier Huang
 */
@Configuration
@EnableAsync
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.jav.ramsevents.*"})
@SpringBootApplication
public class RamseventsApplication extends SpringBootServletInitializer{
	
	/**
	 * Main method
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SpringApplication.run(RamseventsApplication.class, args);
	}

}
