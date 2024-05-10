package com.youth;

import com.youth.utils.AppStartUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan("com.youth.dao")
public class YouthApplication {
	public static void main(String[] args) {
//		SpringApplication.run(YouthApplication.class, args);
		SpringApplication app = new SpringApplication(YouthApplication.class);
		Environment env = app.run(args).getEnvironment();
		app.setBannerMode(Banner.Mode.CONSOLE);
		AppStartUtil.logApplicationStartup(env);
	}
}
