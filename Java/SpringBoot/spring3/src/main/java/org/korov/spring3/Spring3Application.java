package org.korov.spring3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring3Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Spring3Application.class);
		springApplication.run(args);
	}

}
