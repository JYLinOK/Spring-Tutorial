package com.jinwei.S8_mongotemplate;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import java.io.IOException;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class S8MongotemplateApplication {
	public static void main(String[] args) throws IOException {
//		SpringApplication.run(S8MongotemplateApplication.class, args);


		User user = new User("user2", "123");
		String subject = new Gson().toJson(user);

		JWTUtil jwtUtil = new JWTUtil();
		String jwtoken = jwtUtil.genJWTToken(subject);

		System.out.println("jwtoken = " + jwtoken);
		System.out.println("jwtUtil.parseClaim(jwtoken) = " + jwtUtil.parseClaim(jwtoken));
		System.out.println("jwtUtil.parsePayload(jwtoken) = " + jwtUtil.parsePayload(jwtoken));


	}

}
