package com.demojava.redditdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RedditdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditdemoApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowCredentials(true)
						.allowedHeaders("*")
						.exposedHeaders("Authorization")
						.allowedMethods("*")
						.allowedOrigins("http://localhost:4200")
						.maxAge(3600);

			}
		};
	}
}
