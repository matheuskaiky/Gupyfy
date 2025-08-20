package br.com.matheuskaiky.gupyfy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.hibernate.*;

@EnableScheduling
@SpringBootApplication
public class GupyfyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GupyfyApplication.class, args);
	}

}