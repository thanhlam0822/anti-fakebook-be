package com.project.antifakebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class AntiFakebookApplication {

	public static void main(String[] args) {

		System.err.println("test");
		SpringApplication.run(AntiFakebookApplication.class, args);
	}

}
