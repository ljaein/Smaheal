package com.ssafy.smaheal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// 밑에 있는 어노테이션은 당장 JDBC 설정이 필요 없고 어떤 DB를 사용할지 몰라서 넣어놈.
// application.properties가 비어있기 때문에 spring이 실행이 안되기 때문.
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SmahealApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmahealApplication.class, args);
	}

}
