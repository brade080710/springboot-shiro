package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;



@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@MapperScan("com.xm.shiro.admin.dao")
//@ComponentScan
public class Application {

	/**
	 * <p>
	 * 测试 RUN
	 * <br>
	 * 1、http://localhost:8080
	 * 2、http://localhost:8080/user/test1<br>
	 * </p>
	 */
	@Autowired
    Environment env;
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.addListeners(new ApplicationListener<ApplicationPreparedEvent>() {

			@Override
			public void onApplicationEvent(ApplicationPreparedEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		app.addListeners(new ApplicationPidFileWriter()); // write pid to application.pid
		app.run(args);
		
//		SpringApplication.run(Application.class, args);
	}
	

}
