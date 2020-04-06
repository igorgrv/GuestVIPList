package com.vipguestlist.configuration;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class Configuration {

	public static void main(String[] args) {
		SpringApplication.run(Configuration.class, args);
	}
	
	/** JPA ConfigurationS
	 *  @bean it's necessary for Spring to "manage" the class
	 *  DriverManagerDataSource returns DataSource
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName("com.mysql.cj.jdbc.Driver");
		driver.setUrl("jdbc:mysql://localhost/viplist?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
		driver.setUsername("root");
		driver.setPassword("");
		
		return driver;
	}

}
