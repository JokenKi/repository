package com.joken.base.spring.aop;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.joken.base.dao.BaseDao;
import com.joken.common.dblog.CalOprator;
import com.joken.common.dblog.DbOperator;
import com.joken.common.dblog.Logable;
import com.joken.common.utils.ObjectUtils;
import com.joken.common.utils.ReflectUtils;
import com.joken.common.utils.StringUtils;

/**
 * 日志切面,用于将敏感数据的更改记录到数据库
 * 
 * @author wangby
 * @date 2016年5月18日上午11:23:42
 */
@Component
@Aspect
public class LogableAop {

	private final static String OP_SUFFIX = "_OP";

	/*
	 * @Pointcut(value = "execution(* com.joken..dao..update* (..))") public void
	 * aspect() { }
	 */

	@Around("@annotation(logable)")
	public Object around(ProceedingJoinPoint joinPoint, Logable logable)
			throws Throwable {
		try {
			this.logWrite(joinPoint, logable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return joinPoint.proceed();
	}

	@SuppressWarnings({ "rawtypes" })
	public void logWrite(JoinPoint joinPoint, Logable logable) {
		Object target = joinPoint.getTarget();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (joinPoint.getArgs() == null || joinPoint.getArgs().length < 1) {
			return;
		}
		Object param = joinPoint.getArgs()[0];
		if (logable == null) {
			return;
		}
		BaseDao baseDao = null;
		if (target instanceof BaseDao) {
			baseDao = (BaseDao) target;
		}

		if (baseDao == null) {
			return;
		}

		SqlSession sqlSession = baseDao.getSqlSession();

		// k:数据库字段 v:实体属性字符串
		// Map<String, String> mapper = new HashMap<String, String>();
		// k:数据库字段 v:参数值
		Map<String, Object> argsMap = new HashMap<String, Object>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet resultSet = null;
		try {
			connection = sqlSession.getConfiguration().getEnvironment()
					.getDataSource().getConnection();
			String colName = null;
			Object oldValue = null;
			Object newValue = null;
			Object tmp = null;
			StringBuffer buffer = this.getQuerySql(logable, param, argsMap);
			if (buffer == null) {
				return;
			}
			String prepSql = buffer.toString();
			prepareStatement = connection.prepareStatement(prepSql);
			String[] whereFields = logable.whereFields();
			String sql = prepSql.substring(prepSql.indexOf("where") + 5);
			StringBuffer sBuffer = new StringBuffer(sql);
			int tmpNum = 0;
			for (int i = 0; i < whereFields.length; i++) {
				try {
					tmp = ReflectUtils.getFieldValue(whereFields[i], param);
				} catch (Exception e) {
				}
				if (tmp == null) {
					continue;
				}
				prepareStatement.setObject(i + 1, tmp);
				tmpNum = sBuffer.indexOf("?", tmpNum);
				sBuffer.replace(tmpNum, ++tmpNum, tmp.toString());
				tmpNum += tmp.toString().length();
			}
			resultSet = prepareStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = prepareStatement
					.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			prepSql = "insert into field_log(log_id,table_name,operation_field,old_value,new_value,where_term) values(?,?,?,?,?,?)";
			// connection2 = sqlSession.getConfiguration().getEnvironment()
			// .getDataSource().getConnection();
			prepareStatement2 = connection.prepareStatement(prepSql);
			CalOprator logFieldOp = null;
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					colName = resultSetMetaData.getColumnName(i);
					tmp = resultSet.getObject(i);
					if (tmp == null) {
						continue;
					}
					oldValue = tmp;
					tmp = argsMap.get(String.valueOf(colName + OP_SUFFIX));
					if (tmp == null) {
						tmp = argsMap.get(colName);
					} else {
						logFieldOp = (CalOprator) tmp;
						tmp = argsMap.get(colName);
						try {
							method = ObjectUtils.class.getMethod(
									logFieldOp.method(), Object.class,
									Object.class);
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						if (method != null) {
							try {
								tmp = method.invoke(null,
										resultSet.getObject(i), tmp);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

					if (tmp == null) {
						continue;
					}

					newValue = tmp;

					if (ObjectUtils.compare2Obj(oldValue, newValue)) {
						// System.out.println("相等");
						continue;
					}
					// System.out.println("不相等");
					prepareStatement2
							.setString(1, UUID.randomUUID().toString());
					prepareStatement2.setString(2, logable.tableName());
					prepareStatement2.setString(3, colName);
					prepareStatement2.setObject(4, oldValue);
					prepareStatement2.setObject(5, newValue);
					prepareStatement2.setString(6, sBuffer.toString());
					prepareStatement2.execute();

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (prepareStatement != null) {
					prepareStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (prepareStatement2 != null) {
					prepareStatement2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		// Object o = null;
		// Object o2 = null;
		// Integer i = 1;
		// o = 1;
		// i = 2;
		// o2 = i;
		// BigDecimal bigDecimal = new BigDecimal(2.003);
		// BigDecimal bigDecimal2 = new BigDecimal(4.003);
		//
		// o = bigDecimal;
		// o2 = bigDecimal2;
		// System.out.println(ObjectUtils.add(o, o2));
		// System.out.println(ObjectUtils.subtract(o, o2));
		// System.out.println(ObjectUtils.multiply(o, o2));
		// System.out.println(ObjectUtils.divide(o, o2));
		// System.out.println(o.getClass().);

	}

	private StringBuffer getQuerySql(Logable logable, Object param,
			Map<String, Object> argsMap) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ");
		Object tmp = null;

		// 实体属性字符串
		String fieldString = null;
		// 数据库字段字符串
		String propString = null;

		String[] logFields = logable.logFields();
		String[] whereFields = logable.whereFields();
		DbOperator[] whereFieldsOp = logable.whereFieldsOp();
		String tableName = logable.tableName();
		CalOprator[] logFieldsOp = logable.logFieldsOp();
		CalOprator logFieldOp = null;
		for (int i = 0; i < logFields.length; i++) {
			fieldString = logFields[i];
			propString = StringUtils.modelField2DBProp(fieldString);

			if (logFieldsOp == null || logFieldsOp.length - 1 < i) {
				logFieldOp = CalOprator.NONE;
			} else {
				logFieldOp = logFieldsOp[i];
			}

			if (i < logFields.length - 1) {
				sqlBuffer.append(propString).append(" , ");
			} else {
				sqlBuffer.append(propString);
			}

			try {
				tmp = ReflectUtils.getFieldValue(fieldString, param);
			} catch (Exception e) {
			}
			if (tmp == null) {
				continue;
			}
			argsMap.put(propString, tmp);
			if (logFieldOp == CalOprator.NONE) {
				continue;
			}
			argsMap.put(String.valueOf(propString + OP_SUFFIX), logFieldOp);
		}
		sqlBuffer.append(" from ").append(tableName);
		if (whereFields.length > 0) {
			sqlBuffer.append(" where 1=1 ");
		}

		DbOperator fieldOp = null;
		for (int i = 0; i < whereFields.length; i++) {
			fieldString = whereFields[i];
			if (whereFieldsOp == null || whereFieldsOp.length - 1 < i) {
				fieldOp = DbOperator.EQ;
			} else {
				fieldOp = whereFieldsOp[i];
			}

			try {
				tmp = ReflectUtils.getFieldValue(fieldString, param);
			} catch (RuntimeException e) {
			}

			if (tmp == null && i == 0) {
				return null;
			}

			if (tmp == null) {
				continue;
			}
			propString = StringUtils.modelField2DBProp(fieldString);
			argsMap.put(propString, tmp);
			if (i < whereFields.length - 1) {
				sqlBuffer.append("and ").append(propString)
						.append(getDbOpSign(fieldOp));
				continue;
			}
			sqlBuffer.append("and ").append(propString)
					.append(getDbOpSign(fieldOp));
		}
		// System.out.println(sqlBuffer.toString());
		return sqlBuffer;
	}

	public String getDbOpSign(DbOperator op) {
		if (op == null) {
			return "";
		}
		switch (op) {
		case EQ:
			return " = ? ";
		case NOT_EQ:
			return " <> ? ";
		case IN:
			return " in (?) ";
		case NOT_IN:
			return " not in (?) ";
		case GT:
			return " >? ";
		case GTE:
			return " >= ? ";
		case STE:
			return " <=? ";
		case ST:
			return " < ? ";
			/*
			 * case BETWEEN_END: return " between ? and ?";
			 */
		default:
			return "";
		}
	}
}
