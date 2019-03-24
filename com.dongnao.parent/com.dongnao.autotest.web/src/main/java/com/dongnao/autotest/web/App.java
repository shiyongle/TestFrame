package com.dongnao.autotest.web;

import javax.servlet.MultipartConfigElement;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * 
 * @author Richered
 *	应用程序启动类
 */
@SpringBootApplication	
@EnableWebMvc
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = { "com.dongnao.autotest.service" })
@ComponentScan(basePackageClasses = App.class)
@MapperScan(basePackages = { "com.dongnao.autotest.service.mapper" })
public class App {
	/**
	 * 应用入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize("256MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("1024MB");
		// Sets the directory location wherefiles will be stored.
		// factory.setLocation("路径地址");
		return factory.createMultipartConfig();

	}
}
