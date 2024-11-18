package com.gr25.thinkpro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class ThinkproApplication {

	public static void main(String[] args) {
		Environment env = SpringApplication.run(ThinkproApplication.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name");
		if (appName != null) {
			appName = appName.toUpperCase();
		}
		String port = env.getProperty("server.port");
		log.info("-------------------------START " + appName
				+ " Application------------------------------");
		log.info("   Application         : " + appName);
		log.info("   Client UI           : http://localhost:" + port);
		log.info("-------------------------START SUCCESS " + appName
				+ " Application------------------------------");
	}

}
