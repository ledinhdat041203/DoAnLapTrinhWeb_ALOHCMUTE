package vn.iostar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;


import vn.iostar.model.UserAcountModel;

@SpringBootApplication
public class DoAnLapTrinhWebAlohcmute1Application {

	public static void main(String[] args) {
		SpringApplication.run(DoAnLapTrinhWebAlohcmute1Application.class, args);
	}
	@Bean(name="UserAcount_Bean")
	public UserAcountModel setAcountModel()
	{
		UserAcountModel u = new UserAcountModel();
		u.setEmail("admin@gmail.com");
		u.setPass("123");
		return u;
	}

}