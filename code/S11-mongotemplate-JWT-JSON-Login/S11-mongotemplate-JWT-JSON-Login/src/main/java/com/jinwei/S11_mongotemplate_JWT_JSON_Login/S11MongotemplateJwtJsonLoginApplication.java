package com.jinwei.S11_mongotemplate_JWT_JSON_Login;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class S11MongotemplateJwtJsonLoginApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(S11MongotemplateJwtJsonLoginApplication.class, args);

//		User user = new User("user2", "123");
//		String subject = new Gson().toJson(user);
////
//		JWTUtil jwtUtil = new JWTUtil();
////		String jwtoken = jwtUtil.genJWTToken(subject);
//
//		String jwtoken = "eyJ0eXBlIjoiSldUIiwiYWxnbyI6IkhTNTEyIiwiYWxnIjoiSFM1MTIifQ.eyJwYXlsb2FkIjoie309PT0xMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MCIsImp0aSI6ImE0MThhNzY1LWU2ZDktNDBiNC05MTFjLTAwOTU5NjVkZDI1YyIsImV4cCI6MTcyNDk1MDk3OSwiaWF0IjoxNzI0MDg2OTc5LCJzdWIiOiJTVUJKRUNUX3podXRpIiwiaXNzIjoiSVNTX3FpYW5mYWZhbmcifQ.4BOMKBjL4FEH3alLLHi1mFM5WReED7_Ip1oW9OkobDaezwWoXLwA2xd8jzUw-zA85WNRBvbBcfWCSaDvdM_86Q";
//
//		System.out.println("jwtoken = " + jwtoken);
//		System.out.println("jwtUtil.parseJWTToken(jwtoken) = " + jwtUtil.parseJWTToken(jwtoken));
//		System.out.println("jwtUtil.parseHeader(jwtoken) = " + jwtUtil.parseHeader(jwtoken));
//		System.out.println("jwtUtil.parsePayload(jwtoken) = " + jwtUtil.parsePayload(jwtoken));


	}

}
