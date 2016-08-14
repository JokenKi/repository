package com.joken.base.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.joken.common.model.BaseModel;

/**
 * 数据操作默认抽象实现类
 * 
 * @version V1.0.0, 2015-8-17 上午9:06:16
 * @author 欧阳增高
 * @since V1.0.0
 * @param <T>
 *            数据操作实体类
 */
public abstract class DefaultBaseDao<T extends BaseModel> implements BaseDao<T> {

	/**
	 * 注入sqlSession操作类
	 */
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	/**
	 * @param sqlSession
	 *            the sqlSession to set
	 */
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * @return the sqlSession
	 */
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	/**
	 * 实例化数据操作对象
	 * 
	 * @param clzss
	 *            数据操作对象接口类
	 * @author 欧阳增高
	 * @return 绑定Mapper操作实例
	 * 
	 */
	protected BaseDao<T> getDaoInstance(Class<? extends BaseDao<T>> clzss) {
		return getSqlSession().getMapper(clzss);
	}

	/**
	 * 获取数据操作类
	 * 
	 * @return E extends BaseDao<T>
	 * 
	 */
	public abstract BaseDao<T> getDao();

	@Override
	public int insert(T entity) {
		return getDao().insert(entity);
	}

	@Override
	public int insertBatch(List<T> entitys) {
		return getDao().insertBatch(entitys);
	}

	@Override
	public int update(T entity) {
		return getDao().update(entity);
	}

	@Override
	public int delete(T entity) {
		return getDao().delete(entity);
	}

	@Override
	public int deleteByParams(Object params) {
		return getDao().deleteByParams(params);
	}

	@Override
	public T getById(Object id) {
		return getDao().getById(id);
	}

	@Override
	public List<T> selectList(Map<String, Object> params) {
		return getDao().selectList(params);
	}

	@Override
	public long selectCount(Map<String, Object> params) {
		return getDao().selectCount(params);
	}

	/**
	 * 通过指定ID查询语句获取数据列表
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	public List<Object> selectList(String sqlId, Object params) {
		return getSqlSession().selectList(sqlId, params);
	}

}
