package com.demo.springbootapis;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.demo.springbootapis.config.AppProperties;

@SpringBootApplication
@EntityScan(basePackageClasses = { 
		SpringBootApisApplication.class,
		Jsr310JpaConverters.class 
})
@EnableConfigurationProperties(AppProperties.class)
public class SpringBootApisApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApisApplication.class, args);
	}
}

