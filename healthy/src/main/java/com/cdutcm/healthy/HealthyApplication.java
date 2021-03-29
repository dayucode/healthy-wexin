package com.cdutcm.healthy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.cdutcm.healthy.dataobject.mapper")
@SpringBootApplication
@EnableSwagger2
public class HealthyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthyApplication.class, args);
	}
}
