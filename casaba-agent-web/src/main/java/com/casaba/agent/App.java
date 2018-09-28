package com.casaba.agent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@EnableAutoConfiguration
@SpringBootApplication
@MapperScan("com.casaba.dao.mapper")
@ComponentScan("com.casaba")//n指定扫描包路径
public class App {
	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(App.class, args);
	}


}