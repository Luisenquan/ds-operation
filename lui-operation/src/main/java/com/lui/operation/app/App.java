package com.lui.operation.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时器
@ComponentScan(basePackages = { "com.lui.operation" })
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
