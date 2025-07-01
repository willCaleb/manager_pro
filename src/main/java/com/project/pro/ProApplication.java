package com.project.pro;

import com.project.pro.Testes.Teste;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.project.pro.*")
public class ProApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProApplication.class, args);
	}

}
