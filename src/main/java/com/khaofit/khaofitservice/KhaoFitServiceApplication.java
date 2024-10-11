package com.khaofit.khaofitservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * This is the main class for Khao Fit Service.
 *
 * @author Kousik Manik
 */
@SpringBootApplication
@EnableScheduling
public class KhaoFitServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(KhaoFitServiceApplication.class, args);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
