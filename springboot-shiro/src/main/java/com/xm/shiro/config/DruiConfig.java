package com.xm.shiro.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
 
//@Configuration
public class DruiConfig {
	
//	@Value("${spring.datasource.url}")
//	private String dbUrl;
// 
//	@Value("${spring.datasource.username}")
//	private String username;
// 
//	@Value("${spring.datasource.password}")
//	private String password;
// 
//	@Value("${spring.datasource.driver-class-name}")
//	private String driverClassName;
// 
//	@Value("${spring.datasource.initialSize}")
//	private int initialSize;
// 
//	@Value("${spring.datasource.minIdle}")
//	private int minIdle;
// 
//	@Value("${spring.datasource.maxActive}")
//	private int maxActive;
// 
//	@Value("${spring.datasource.maxWait}")
//	private int maxWait;
// 
//	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
//	private int timeBetweenEvictionRunsMillis;
// 
//	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
//	private int minEvictableIdleTimeMillis;
// 
//	@Value("${spring.datasource.validationQuery}")
//	private String validationQuery;
// 
//	@Value("${spring.datasource.testWhileIdle}")
//	private boolean testWhileIdle;
// 
//	@Value("${spring.datasource.testOnBorrow}")
//	private boolean testOnBorrow;
// 
//	@Value("${spring.datasource.testOnReturn}")
//	private boolean testOnReturn;
// 
//	@Value("${spring.datasource.poolPreparedStatements}")
//	private boolean poolPreparedStatements;
// 
//	@Value("${spring.datasource.filters}")
//	private String filters;
//	
//	@Value("${spring.datasource.logSlowSql}")
//	private String logSlowSql;
//	private final static Logger logger = LoggerFactory.getLogger(DruiConfig.class);
//	@Bean
//	@Primary
//	public DataSource dataSource(){
//		//@Primary 注解作用是当程序选择dataSource时选择被注解的这个
//		DruidDataSource datasource = new DruidDataSource();
//		datasource.setUrl(dbUrl);
//		datasource.setUsername(username);
//		datasource.setPassword(password);
//		datasource.setDriverClassName(driverClassName);
//		datasource.setInitialSize(initialSize);
//		datasource.setMinIdle(minIdle);
//		datasource.setMaxActive(maxActive);
//		datasource.setMaxWait(maxWait);
//		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		datasource.setValidationQuery(validationQuery);
//		datasource.setTestWhileIdle(testWhileIdle);
//		datasource.setTestOnBorrow(testOnBorrow);
//		datasource.setTestOnReturn(testOnReturn);
//		datasource.setPoolPreparedStatements(poolPreparedStatements); 
//		try {
//			datasource.setFilters(filters);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return datasource;
//	}
//	
//	  @Bean
//	    public ServletRegistrationBean druidServlet() {
//		  
//		  logger.info("init Druid Servlet Configuration ");
//	        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//	        servletRegistrationBean.setServlet(new StatViewServlet());
//	        servletRegistrationBean.addUrlMappings("/druid/*");
//	        Map<String, String> initParameters = new HashMap<String, String>();
//	        initParameters.put("loginUsername", username);// 用户名
//	        initParameters.put("loginPassword", password);// 密码
//	        initParameters.put("logSlowSql", logSlowSql);
//	        
//	        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
//	        //initParameters.put("allow", allowIp); // IP白名单 (没有配置或者为空，则允许所有访问)
//	        //initParameters.put("deny", "");// IP黑名单 (存在共同时，deny优先于allow)
//	        servletRegistrationBean.setInitParameters(initParameters);
//	        return servletRegistrationBean;
//		  
//		  
//		  
////	        ServletRegistrationBean reg = new ServletRegistrationBean();
////	        reg.setServlet(new StatViewServlet());
////	        reg.addUrlMappings("/druid/*");
////	        reg.addInitParameter("loginUsername", username);
////	        reg.addInitParameter("loginPassword", password);
////	        reg.addInitParameter("logSlowSql", logSlowSql);
////	        reg.addInitParameter("resetEnable", "false"); //禁用HTML页面上的“Reset All”功能
//////	        //initParameters.put("allow", allowIp); // IP白名单 (没有配置或者为空，则允许所有访问)
//////	        //initParameters.put("deny", "");// IP黑名单 (存在共同时，deny优先于allow)
//////	        servletRegistrationBean.setInitParameters(initParameters);
////	        return reg;
//	    }
// 
//	    @Bean
//	    public FilterRegistrationBean filterRegistrationBean() {
//	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//	        filterRegistrationBean.setFilter(new WebStatFilter());
//	        filterRegistrationBean.addUrlPatterns("/*");
//	        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//	        filterRegistrationBean.addInitParameter("profileEnable", "true");
//	        return filterRegistrationBean;
//	    }
}
