package com.joken.base.intercepter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.joken.common.model.Page;
import com.joken.common.utils.StringUtils;

/**
 * mybatis分页拦截器实现类
 * 
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class PageIntercepter implements Interceptor {
	/**
	 * 匹配FROM子句
	 */
	private static final Pattern DETACH_FROM_CLAUSE_PATTERN = Pattern
			.compile(
					"(?:select[\\s]*[distinct|top\\s\\d]*\\s)([a-zA-Z0-9_\\.\\,\\s]*)(from[\\S\\s]*)",
					Pattern.CASE_INSENSITIVE);
	/**
	 * 匹配as字段
	 */
	private static final Pattern REMOVE_AS_CLAUSE_PATTERN = Pattern.compile(
			"([a-zA-Z]*\\(+[^\\(]*\\)+\\s*as\\s|\\'?[^,]*\\'?\\sas\\s)",
			Pattern.CASE_INSENSITIVE);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		String originalSql = boundSql.getSql().trim();
		Object parameterObject = boundSql.getParameterObject();

		Page page = null;
		if (parameterObject instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> params = (Map<String, Object>) parameterObject;
			// 检查并获取分页对象
			if (params != null && params.containsKey(Page.QUERY_REQUEST_KEY)) {
				page = (Page) params.get(Page.QUERY_REQUEST_KEY);
				if (page != null && page.getPageNo() > 0
						&& page.getPageSize() > 0) {
					// 获取count统计查询语句
					String countSql = getCountSql(originalSql);
					Connection connection = mappedStatement.getConfiguration()
							.getEnvironment().getDataSource().getConnection();
					PreparedStatement countStmt = connection
							.prepareStatement(countSql);
					BoundSql countBS = copyFromBoundSql(mappedStatement,
							boundSql, countSql);
					DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
							mappedStatement, parameterObject, countBS);
					parameterHandler.setParameters(countStmt);

					ResultSet rs = countStmt.executeQuery();

					int totalRecord = 0;
					if (rs.next()) {
						totalRecord = rs.getInt(1);
					}
					rs.close();
					countStmt.close();
					connection.close();

					// 设置查询数据总记录数
					page.setTotalRecord(totalRecord);

				}
			}
		}
		return invocation.proceed();
	}

	/**
	 * 
	 * 数据查询绑定
	 * 
	 * @version V1.0.0, 2016-3-15
	 * @author 欧阳增高
	 * @since V1.0.0
	 */
	public class BoundSqlSource implements SqlSource {

		/**
		 * 绑定的查询语句
		 */
		private BoundSql boundSql;

		/**
		 * 数据绑定构造方法
		 * 
		 * @param boundSql
		 *            需要绑定的查询语句
		 */
		public BoundSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	// private MappedStatement copyFromMappedStatement(MappedStatement ms,
	// SqlSource newSqlSource) {
	// Builder builder = new Builder(ms.getConfiguration(), ms.getId(),
	// newSqlSource, ms.getSqlCommandType());
	// builder.resource(ms.getResource());
	// builder.fetchSize(ms.getFetchSize());
	// builder.statementType(ms.getStatementType());
	// builder.keyGenerator(ms.getKeyGenerator());
	// String[] properties = ms.getKeyProperties();
	// String property = "";
	// if (properties != null) {
	// for (String string : properties) {
	// property += "," + string;
	// }
	// property.substring(1, property.length());
	// }
	//
	// builder.keyProperty(property);
	// builder.timeout(ms.getTimeout());
	// builder.parameterMap(ms.getParameterMap());
	// builder.resultMaps(ms.getResultMaps());
	// builder.resultSetType(ms.getResultSetType());
	// builder.cache(ms.getCache());
	// builder.flushCacheRequired(ms.isFlushCacheRequired());
	// builder.useCache(ms.isUseCache());
	// return builder.build();
	// }

	/**
	 * 复制填充绑定查询语句对象
	 * 
	 * @param ms
	 *            定义参数集合
	 * @param boundSql
	 *            绑定的查询语句
	 * @param sql
	 *            需要填充的查询语句
	 * @return 复制填充后的绑定查询
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
			String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
				boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop,
						boundSql.getAdditionalParameter(prop));
			}
		}

		return newBoundSql;
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 *            原始查询语句
	 * @return 计数统计查询语句
	 */
	private String getCountSql(String sql) {
		Matcher m = REMOVE_AS_CLAUSE_PATTERN.matcher(sql);
		// sql = sql.replaceAll("\r\n", " ");
		// Pattern p = Pattern.compile(
		// "([a-zA-Z]*\\(+[^\\(]*\\)+\\s*as\\s|\\'?[^,]*\\'?\\sas\\s)",
		// Pattern.CASE_INSENSITIVE);
		// Matcher m = p.matcher(sql);
		String newQuery = sql;
		while (m.find()) {
			String sf = m.group(0);
			String strField = sf.toLowerCase();
			if (strField.indexOf("select ") == 0) {
				sf = sf.substring(7);
			}
			newQuery = newQuery.replace(sf, "");
		}
		int s = newQuery.toLowerCase().indexOf("select");
		if (s > 10 || s == -1) {
			newQuery = "select " + newQuery;
		}
		m = DETACH_FROM_CLAUSE_PATTERN.matcher(newQuery);
		if (m.find()) {
			String field = m.group(1);
			if (!StringUtils.isEmpty(field)) {
				// String[] fields = field.split(",");
				String fromQuery = m.group(2).trim();
				Integer[] queryIndex = getQueryKeyIndex(fromQuery);
				int qLength = queryIndex[0];
				int sortIndex = queryIndex[2];
				String from = fromQuery.substring(0, sortIndex > 0 ? sortIndex
						: qLength);
				// return "SELECT COUNT(" + fields[0] + ") " + from.trim();
				return "SELECT COUNT(*) " + from.trim();
			}
		}
		Integer[] queryIndex = getQueryKeyIndex(sql);
		String from = sql.substring(0, queryIndex[2] > 0 ? queryIndex[2]
				: queryIndex[0]);
		return "SELECT COUNT(*) FROM (" + from.trim() + ") aliasForPage";
	}

	public static void main(String[] args) {
		String sql = "SELECT r.reserve_id as reserveId, g.food_group_code as foodGroupCode, g.food_group_name as foodGroupName, r.cabinet_id as cabinetId, s.market_name as marketName, r.reserve_door_num as reserveDoorNum, r.take_out_num as takeOutNum, r.starttime as starttime, r.endtime as endtime, r.reserve_status as reserveStatus, r.create_time as createTime FROM open_food_group_reserve r INNER JOIN open_food_group g ON g.food_group_code=r.food_group_code INNER JOIN supermarket s ON s.market_id=r.cabinet_id WHERE platform_id = ? order by r.reserve_id desc limit 0,20 ";
		PageIntercepter p = new PageIntercepter();
		System.out.println("\r\n");
		System.out.println(p.getCountSql(sql));

	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * 获取from子句，排序子句索引位置
	 * 
	 * @param query
	 *            查询语句
	 * @return 指定子语句所在位置集合
	 * @author Inkcar Jul 15, 2010 3:17:23 PM
	 */
	private Integer[] getQueryKeyIndex(String query) {
		String lowerSql = query.toLowerCase();
		int qLength = query.length();
		int fromIndex = lowerSql.lastIndexOf("from");
		int sortIndex = lowerSql.lastIndexOf("group by", qLength);
		int bracketIndex = lowerSql.lastIndexOf(")");
		int startBracketIndex = lowerSql.lastIndexOf("(");
		if (startBracketIndex < fromIndex && fromIndex < bracketIndex) {
			int tmpBracket = lowerSql.indexOf("(");
			fromIndex = lowerSql.indexOf("from");
			while (tmpBracket < fromIndex) {
				tmpBracket = lowerSql.indexOf("(", fromIndex);
				fromIndex = lowerSql.indexOf("from", fromIndex + 1);
			}
		}
		if (bracketIndex > sortIndex) {
			sortIndex = -1;
		}
		if (sortIndex < 0) {
			sortIndex = lowerSql.lastIndexOf("order by", qLength);
			if (bracketIndex > sortIndex) {
				sortIndex = -1;
			}
		}// if (sortIndex < 0)
		if (sortIndex < 0) {
			sortIndex = lowerSql.lastIndexOf("limit ", qLength);
		}
		if (sortIndex < fromIndex) {
			sortIndex = -1;
		}// if (sortIndex < fromIndex)
		return new Integer[] { qLength, fromIndex, sortIndex };
	}

}
