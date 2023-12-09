package vn.hcmute;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import vn.hcmute.model.UserAcountModel;

@SpringBootApplication
@EnableScheduling
public class AlohcmuteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlohcmuteApplication.class, args);
	}

	@Bean(name = "UserAcount_Bean")
	public UserAcountModel setAcountModel() {
		UserAcountModel u = new UserAcountModel();
		u.setEmail("admin@gmail.com");
		u.setPass("123");
		return u;
	}

	

}