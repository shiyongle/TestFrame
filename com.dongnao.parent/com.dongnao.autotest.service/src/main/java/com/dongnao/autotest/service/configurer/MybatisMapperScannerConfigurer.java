/**
 * @title MybatisMapperScannerConfigurer.java
 * @description TODO(这里用一句话描述这个文件的作用)
 * @package lm.pro.secureshell.cloud.provider
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年3月9日下午5:08:06
 * @version v1.0.0
 */
package com.dongnao.autotest.service.configurer;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: MybatisMapperScannerConfigurer
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mrluo735
 * @date 2017年3月9日 下午5:08:06
 * 
 */
// @Configuration
// TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
// @AutoConfigureAfter(MybatisConfigurer.class)
public class MybatisMapperScannerConfigurer {
	@Value("${mybatis.basePackage}")
	private String basePackage;

	/**
	 * 
	 * @return
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(basePackage);
		return mapperScannerConfigurer;
	}
}
