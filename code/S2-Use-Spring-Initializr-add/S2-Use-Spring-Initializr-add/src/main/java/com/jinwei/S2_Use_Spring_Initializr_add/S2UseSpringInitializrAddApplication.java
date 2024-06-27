package com.jinwei.S2_Use_Spring_Initializr_add;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 添加 Spring web 相关库
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 然后在@SpringBootApplication下方添加注解@RestController
@SpringBootApplication
@RestController

public class S2UseSpringInitializrAddApplication {

	public static void main(String[] args) {
		SpringApplication.run(S2UseSpringInitializrAddApplication.class, args);
	}

	// 添加注解 @GetMapping("/hello") 定义访问响应的url，并在下方定义具体的相应函数
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}
