package com.tico.pomoro_do;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄러 활성화
@EnableJpaAuditing  // JPA Auditing 활성화
@SpringBootApplication
public class PomoroDoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PomoroDoApplication.class, args);
	}

}
