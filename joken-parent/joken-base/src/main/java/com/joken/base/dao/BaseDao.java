/**
 * 
 */
package com.joken.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.joken.common.model.BaseModel;

/***
 * 类描述：数据操作基础接口
 * 
 * @author 欧阳增高
 * @date 下午05:09:12
 * @since 1.0
 */
public interface BaseDao<T extends BaseModel> {

	/**
	 * 获取数据操作session
	 * 
	 * @return SqlSession
	 */
	SqlSession getSqlSession();

	/**
	 * 根据实体对象插入实体数据
	 * 
	 * @param entity
	 *            实体对象
	 * @return　操作处理数量
	 */
	int insert(T entity);

	/**
	 * 根据实体对象插入实体数据
	 * 
	 * @param entitys
	 *            实体对象列表
	 * @return　操作处理数量
	 */
	int insertBatch(List<T> entitys);

	/**
	 * 根据实体对象更新实体数据
	 * 
	 * @param entity
	 *            实体对象
	 * @return　操作处理数量
	 */
	int update(T entity);

	/**
	 * 根据实体对象删除实体数据
	 * 
	 * @param entity
	 *            实体对象
	 * @return　操作处理数量
	 */
	int delete(T entity);

	/**
	 * 根据实体对象删除实体数据
	 * 
	 * @param params
	 *            需要传入的参数，多参数请使用map传入
	 * @return 操作处理数量
	 */
	int deleteByParams(Object params);

	/**
	 * 通过实体id获取实体对象
	 * 
	 * @param id
	 * @return T
	 */
	T getById(Object id);

	/**
	 * 获取实体对象列表
	 * 
	 * @param params
	 *            需要传入的参数值，多参数请以Map键值对应传入
	 * @return List<T>
	 */
	List<T> selectList(Map<String, Object> params);
	
	/**
	 * 通过指定ID查询语句获取数据列表
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	List<Object> selectList(String sqlId, Object params);

	/**
	 * 获取指定参数实体数量
	 * 
	 * @param params
	 *            需要传入的参数值，多参数请以Map键值对应传入
	 * @return long
	 */
	long selectCount(Map<String, Object> params);

}
