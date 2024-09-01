package com.jinwei.springboot_web_jpa_mongodb;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootWebJpaMongodbApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringbootWebJpaMongodbApplication.class, args);




//		String subject = "DAPS-001";
//		JWTUtil jwtUtil = new JWTUtil();
//		String jwtoken = jwtUtil.genJWTToken(subject);
//
//		System.out.println("jwtoken = " + jwtoken);
//		System.out.println("jwtUtil.parseJWTToken(jwtoken) = " + jwtUtil.parseJWTToken(jwtoken));
//		System.out.println("jwtUtil.parseHeader(jwtoken) = " + jwtUtil.parseHeader(jwtoken));
//		System.out.println("jwtUtil.parsePayload(jwtoken) = " + jwtUtil.parsePayload(jwtoken));
//
//

	}

}
