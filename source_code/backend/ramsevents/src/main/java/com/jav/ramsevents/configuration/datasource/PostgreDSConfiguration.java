package com.jav.ramsevents.configuration.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;

/**
 * Postgres data source configuration
 * 
 * @author Javier Huang
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {"com.jav.ramsevents.repository"},
	entityManagerFactoryRef = "postgreEntityManager",
	transactionManagerRef = "postgreTransactionManager"
)
public class PostgreDSConfiguration {
	
	/**
	 * Get data source
	 * @return DataSource postgreDataSource
	 */
	@Bean(name = "postgreDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	/**
	 * Get entity manager
	 * @param builder EntityManagerFactoryBuilder
	 * @param dataSource DataSource postgreDataSource
	 * @return LocalContainerEntityManagerFactoryBean postgreEntityManager Bean
	 */
	@Bean(name = "postgreEntityManager")
	@PersistenceContext(unitName = "postgre")
	public LocalContainerEntityManagerFactoryBean getPostgreEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("postgreDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.jav.ramsevents.repository").persistenceUnit("postgre").build();
	}
	
	/**
	 * Get transaction manager
	 * @param EntityManagerFactory factory
	 * @return PlatformTransactionManager postgreTransactionManager
	 */
	@Bean(name = "postgreTransactionManager")
	public PlatformTransactionManager getPostgreTransactionManager(@Qualifier("postgreEntityManager") EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}
}
