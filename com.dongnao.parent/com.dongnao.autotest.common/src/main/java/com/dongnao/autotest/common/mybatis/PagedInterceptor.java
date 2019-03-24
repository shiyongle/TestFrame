package com.dongnao.autotest.common.mybatis;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.SQLUtils;

import com.dongnao.autotest.common.JavaUtil;
import com.dongnao.autotest.common.JsonUtil;
import com.dongnao.autotest.common.MapUtil;
import com.dongnao.autotest.common.Pager;

/**
 * mybatis 分页拦截器
 * 
 * @author easy<br>
 * @date 2017年9月13日 下午4:27:58
 */
// @formatter:off
@Intercepts({ 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }),
	@Signature(type = ParameterHandler.class, method = "getParameterObject", args = {}),
	@Signature(type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class }),
	@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) 
})
// @formatter:on
public class PagedInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(PagedInterceptor.class);
	/**
	 * 数据库方言
	 */
	private String dialect = "mysql";
	/**
	 * mapper.xml中需要拦截的id(正则匹配)
	 */
	private String sqlIdPattern = ".*selectPaged$";

	private boolean isLogger = true;

	/**
	 * 设置是否打印日志
	 * 
	 * @param isLogger
	 */
	public void setIsLogger(boolean isLogger) {
		this.isLogger = isLogger;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object result = null;
		Object target = invocation.getTarget();
		Method method = invocation.getMethod();
		if (target instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
					.getValue("delegate.mappedStatement");
			String mapperId = mappedStatement.getId();
			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			String sql = boundSql.getSql();
			Object parameterObject = boundSql.getParameterObject();
			if (mapperId.matches(sqlIdPattern)) {
				Connection connection = (Connection) invocation.getArgs()[0];
				// 分页参数作为参数对象parameterObject的一个属性
				Map<String, Object> pagerMap = (Map<String, Object>) (boundSql.getParameterObject());
				// 获取分页sql
				long count = this.getPagedCount(connection, mappedStatement, boundSql, pagerMap, sql);
				pagerMap.put(Pager.COUNT, count);
				// 获取分页Sql语句
				String pagedSql = this.getMysqlPageSql(pagerMap, sql);
				metaStatementHandler.setValue("delegate.boundSql.sql", pagedSql);
			}
			long start = System.currentTimeMillis();
			result = invocation.proceed();
			long end = System.currentTimeMillis();

			String msg = String.format("Mapper方法[%s]执行了[%s]操作, 耗时[%s]ms, sql语句如下:%s", mapperId, method.getName(),
					end - start, JavaUtil.getLineSeparator());
			msg += SQLUtils.format(sql, this.dialect);
			msg += JavaUtil.getLineSeparator() + "参数信息如下:" + JavaUtil.getLineSeparator();
			msg += JsonUtil.toJsonUseJackson(parameterObject);
			this.printLog(msg);
		} else if (target instanceof ParameterHandler) {
			DefaultParameterHandler dph = (DefaultParameterHandler) target;
			result = invocation.proceed();
			this.printLog("ParameterHandler, " + dph.toString());
		} else if (target instanceof ResultSetHandler) {
			result = invocation.proceed();
			this.printLog("ResultSetHandler");
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param pagerMap
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return Mysql数据库分页语句
	 */
	private String getMysqlPageSql(Map<String, Object> pagerMap, String sql) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		StringBuffer sqlBuffer = new StringBuffer(sql);
		int pageIndex = MapUtil.getInteger(pagerMap, Pager.PAGEINDEX, 1);
		int pageSize = MapUtil.getInteger(pagerMap, Pager.PAGESIZE, 20);
		int offset = pageIndex * pageSize - pageSize;
		sqlBuffer.append(" LIMIT ").append(offset).append(",").append(pageSize);
		return sqlBuffer.toString();
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数
	 * 
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param pagerMap
	 * @param sql
	 * @return
	 */
	private long getPagedCount(Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
			Map<String, Object> pagerMap, String sql) {
		boolean isStatCount = MapUtil.getBoolean(pagerMap, Pager.ISSTATCOUNT, true);
		if (!isStatCount)
			return 0;
		// 构造count sql语句
		// int fromIndex = sql.toUpperCase().indexOf("FROM");
		// String countSql = "SELECT COUNT(1) " + sql.substring(fromIndex);
		String countSql = "SELECT COUNT(1) FROM (" + sql + ") t";

		// 通过BoundSql获取对应的参数映射
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings,
				pagerMap);
		// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, pagerMap, countBoundSql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long count = 0L;
		try {
			pstmt = connection.prepareStatement(countSql);
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (SQLException e) {
			logger.error("Ignore this exception" + e);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception" + e);
			}
		}
		return count;
	}

	/**
	 * 打印日志
	 * 
	 * @param content
	 */
	private void printLog(String content) {
		if (!this.isLogger)
			return;
		logger.info(content);
	}
}
