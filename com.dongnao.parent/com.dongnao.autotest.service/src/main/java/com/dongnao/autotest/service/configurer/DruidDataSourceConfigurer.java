/**
 * @title DruidDataSourceBean.java
 * @description TODO(这里用一句话描述这个文件的作用)
 * @package lm.pro.secureshell.cloud.provider
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年3月8日下午3:28:04
 * @version v1.0.0
 */
package com.dongnao.autotest.service.configurer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.PasswordCallback;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.lob.LobHandler;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.DruidLobHandler;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

/**
 * @ClassName: DruidDataSourceBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author easy
 * @date 2017年3月8日 下午3:28:04
 * 
 */
@Configuration
public class DruidDataSourceConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(DruidDataSourceConfigurer.class);
	
	@Value("${jdbc.driver}")
	private String driver = "";
	@Value("${jdbc.url}")
	private String url = "";
	@Value("${jdbc.username}")
	private String username = "";
	@Value("${jdbc.password}")
	private String password = "";
	@Value("${jdbc.initialSize}")
	private int initialSize = 0;
	@Value("${jdbc.maxActive}")
	private int maxActive = 100;
	@Value("${jdbc.maxIdle}")
	private int maxIdle = 20;
	@Value("${jdbc.minIdle}")
	private int minIdle = 1;
	@Value("${jdbc.maxWait}")
	private long maxWait = 60;

	@Value("${jdbc.druid.isPasswordCallback}")
	private boolean isPasswordCallback = true;
	@Value("${jdbc.druid.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis = 60000;
	@Value("${jdbc.druid.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis = 300000;
	@Value("${jdbc.druid.validationQuery}")
	private String validationQuery = "SELECT 'x'";
	@Value("${jdbc.druid.testWhileIdle}")
	private boolean testWhileIdle = true;
	@Value("${jdbc.druid.testOnBorrow}")
	private boolean testOnBorrow = false;
	@Value("${jdbc.druid.testOnReturn}")
	private boolean testOnReturn = false;
	@Value("${jdbc.druid.poolPreparedStatements}")
	private boolean poolPreparedStatements = true;
	@Value("${jdbc.druid.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize = 20;
	@Value("${jdbc.druid.useGlobalDataSourceStat}")
	private boolean useGlobalDataSourceStat = true;

	@Value("${jdbc.druid.statFilter.slowSqlMillis}")
	private long slowSqlMillis = 3000;
	@Value("${jdbc.druid.statFilter.logSlowSql}")
	private boolean logSlowSql = true;
	@Value("${jdbc.druid.statFilter.mergeSql}")
	private boolean mergeSql = true;

	@Value("${jdbc.druid.logFilter.statementLogEnabled}")
	private boolean statementLogEnabled = true;
	@Value("${jdbc.druid.logFilter.statementLoggerName}")
	private String statementLoggerName = "";

	@Value("${jdbc.druid.wallFilterConfig.dir}")
	private String dir = "META-INF/druid/wall/mysql";
	@Value("${jdbc.druid.wallFilterConfig.commentAllow}")
	private boolean commentAllow = true;
	@Value("${jdbc.druid.wallFilterConfig.multiStatementAllow}")
	private boolean multiStatementAllow = true;
	@Value("${jdbc.druid.wallFilterConfig.noneBaseStatementAllow}")
	private boolean noneBaseStatementAllow = true;
	@Value("${jdbc.druid.wallFilterConfig.selectWhereAlwayTrueCheck}")
	private boolean selectWhereAlwayTrueCheck = false;
	@Value("${jdbc.druid.wallFilterConfig.conditionAndAlwayTrueAllow}")
	private boolean conditionAndAlwayTrueAllow = true;

	@Value("${jdbc.druid.wallFilter.dbType}")
	private String dbType = "mysql";
	@Value("${jdbc.druid.wallFilter.logViolation}")
	private boolean logViolation = true;
	@Value("${jdbc.druid.wallFilter.throwException}")
	private boolean throwException = true;

	@Value("${jdbc.druid.allow}")
	private String druidAllow = "127.0.0.1";
	@Value("${jdbc.druid.deny}")
	private String druidDeny = "";
	@Value("${jdbc.druid.loginUsername}")
	private String druidLoginUsername = "admin";
	@Value("${jdbc.druid.loginPassword}")
	private String druidLoginPassword = "123456";
	@Value("${jdbc.druid.resetEnable}")
	private String druidResetEnable = "true";

//	@Autowired
//	private PasswordCallback passwordCallback;
//	@Autowired
//	private FilterAdapter sqlFilter;
	@Autowired
	private StatFilter statFilter;
	@Autowired
	private LogFilter logFilter;
	@Autowired
	private WallFilter wallFilter;

	/**
	 * dataSource
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	@Bean(name = { "dataSource", "druidDataSource" }, initMethod = "init", destroyMethod = "close")
	@Primary
	public DataSource druidDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		// druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setConnectionProperties("password=" + password);
//		if (isPasswordCallback)
//			druidDataSource.setPasswordCallback(passwordCallback);
		// 配置初始化大小、最小、最大
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setMinIdle(minIdle);
		// 配置获取连接等待超时的时间
		druidDataSource.setMaxWait(maxWait);
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);

		// 打开PSCache，并且指定每个连接上PSCache的大小
		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		// 合并统计多个数据源
		druidDataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);

		try {
			// 配置监控统计拦截的filters，去掉后监控界面sql无法统计
			druidDataSource.setFilters("stat, wall");

			List<Filter> filters = new ArrayList<>();
//			filters.add(this.sqlFilter);
			filters.add(this.statFilter);
			filters.add(this.logFilter);
			filters.add(this.wallFilter);
			druidDataSource.setProxyFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		logger.info("※※※※※※※※※※ Druid数据源连接成功！ ※※※※※※※※※※");
		return druidDataSource;
	}

//	@Bean
//	public FilterAdapter sqlFilter() {
//		return new lm.com.framework.druid.CustomFilterAdapter();
//	}

	@Bean
	public StatFilter statFilter() {
		StatFilter statFilter = new StatFilter();
		// 慢SQL统计，如果SQL执行时间超过一定时间则记录为慢SQL
		statFilter.setSlowSqlMillis(slowSqlMillis);
		// 慢SQL统计日志输出
		statFilter.setLogSlowSql(logSlowSql);
		// 合并SQL统计 例如select * from table a where a.id =1，会被变为select * from table
		// a where a.id = ? 来统计
		statFilter.setMergeSql(mergeSql);
		return statFilter;
	}

	@Bean
	public LogFilter logFilter() {
		Slf4jLogFilter logFilter = new Slf4jLogFilter();
		logFilter.setStatementLogEnabled(statementLogEnabled);
		logFilter.setStatementLoggerName(statementLoggerName);
		return logFilter;
	}

	@Bean
	public WallFilter wallFilter() {
		WallConfig wallConfig = new WallConfig();
		// 指定配置装载的目录
		wallConfig.setDir(dir);
		wallConfig.setCommentAllow(commentAllow);
		wallConfig.setMultiStatementAllow(multiStatementAllow);
		wallConfig.setNoneBaseStatementAllow(noneBaseStatementAllow);
		wallConfig.setSelectWhereAlwayTrueCheck(selectWhereAlwayTrueCheck);
		wallConfig.setConditionAndAlwayTrueAllow(conditionAndAlwayTrueAllow);

		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType(dbType);
		wallFilter.setConfig(wallConfig);
		wallFilter.setLogViolation(logViolation);
		wallFilter.setThrowException(throwException);
		return wallFilter;
	}

	@Bean
	public LobHandler lobHandler() {
		return new DruidLobHandler();
	}

	/************************************
	 * 监控界面配置 *******************************************=/ /**
	 * 注册一个StatViewServlet
	 * 
	 * @return
	 */
//	@Bean
//	public ServletRegistrationBean DruidStatViewServle2() {
//		// org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
//		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
//				"/druid/*");
//		// 浏览url: http://localhost:7020/druid/login.html
//		// 添加初始化参数：initParams
//		// 白名单：
//		servletRegistrationBean.addInitParameter("allow", druidAllow);
//		// IP黑名单 (共同存在时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
//		// permitted to view this page.
//		// servletRegistrationBean.addInitParameter("deny",druidDeny);
//		// 登录查看信息的账号密码.
//		servletRegistrationBean.addInitParameter("loginUsername", druidLoginUsername);
//		servletRegistrationBean.addInitParameter("loginPassword", druidLoginPassword);
//		// 是否能够重置数据.
//		servletRegistrationBean.addInitParameter("resetEnable", druidResetEnable);
//		return servletRegistrationBean;
//	}

	/**
	 * 注册一个：filterRegistrationBean
	 * 
	 * @return
	 */
//	@Bean
//	public FilterRegistrationBean druidStatFilter2() {
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//		// 添加过滤规则.
//		filterRegistrationBean.addUrlPatterns("/*");
//
//		// 添加不需要忽略的格式信息.
//		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//		return filterRegistrationBean;
//	}
}
