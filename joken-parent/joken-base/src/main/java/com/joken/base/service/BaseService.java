package com.joken.base.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.joken.common.model.BaseModel;
import com.joken.common.model.Page;
import com.joken.common.model.RequestModel;
import com.joken.common.model.ResponseModel;

/**
 * 基础服务抽象接口
 * 
 * @author 欧阳增高
 * 
 * @param <T>
 */
public interface BaseService<T extends BaseModel> {

	/**
	 * 设置业务数据操作类
	 * 
	 * @param dao
	 *            业务操作dao
	 */
	// void setDao(BaseDAO<T> dao);

	/**
	 * 获取泛型实体实例对象
	 * 
	 * @return T
	 */
	T getEntityInstance();

	/**
	 * 获取实体对象类名称
	 * 
	 * @return String
	 */
	// String getEntityName();

	/**
	 * 获取实体对象主键属性名称
	 * 
	 * @return String
	 */
	String getEntityKeyField();

	/**
	 * 获取业务实体对象主键值
	 * 
	 * @return Object
	 */
	Object getEntityKeyId(T bean);

	/**
	 * 设置实体对象ID值
	 * 
	 * @param bean
	 *            实体对象
	 * @param id
	 *            设置的ID值
	 */
	void setEntityKeyId(T bean, Object id);

	/**
	 * 保存实体数据
	 * 
	 * @param bean
	 *            需要保存的实体数据
	 * @param model
	 *            请求参数模型实体对象
	 * @return String
	 */
	ResponseModel save(T bean, RequestModel model);

	/**
	 * 保存实体数据
	 * 
	 * @param model
	 *            请求参数模型实体对象
	 * @return String
	 */
	ResponseModel save(RequestModel model);

	/**
	 * 通用插入操作
	 * 
	 * @param bean
	 *            需要插入的实体对象
	 * @return int
	 */
	int insert(T bean);

	/**
	 * 根据实体对象插入实体数据
	 * 
	 * @param bean
	 *            实体对象列表
	 * @return　int
	 */
	int insertBatch(List<T> bean);

	/**
	 * 通用更新
	 * 
	 * @param bean
	 *            需要更新的实体对象
	 * @return int
	 */
	int update(T bean);

	/**
	 * 通用删除
	 * 
	 * @param bean
	 *            需要删除的实体对象
	 * @return int
	 */
	int delete(T bean);

	/**
	 * 根据实体keyId/或Map参数删除实体数据
	 * 
	 * @param model
	 *            需要做删除操作的参数，可以为实体keyId或Map键值
	 * @return String
	 */
	ResponseModel deleteByParams(RequestModel model);

	/**
	 * 获取指定id值的实体对象
	 * 
	 * @param id
	 *            实体id值
	 * @return T
	 */
	T getById(Object id);

	/**
	 * 通过请求实体模型对象获取实体及实体相关数据
	 * 
	 * @param model
	 * @return 实体对象对应Map对象
	 * @date 2015-9-15 下午1:49:32
	 */
	Map<String, Object> getMap(RequestModel model);

	/**
	 * 通过指定参数集查询唯一实体对象
	 * 
	 * @param params
	 *            实体查询参数值,多参数请以Map传入
	 * @return T
	 */
	T query(Object params);

	/**
	 * 通过指定参数集查询唯一实体对象之后事件
	 * 
	 * @param bean
	 *            实体对象
	 * @param model
	 *            请求参数模型
	 * @return Object 处理结果
	 */
	// Map<String, Object> queryAfter(Object bean, RequestModel model);

	/**
	 * 获取指定条件的实体对象
	 * 
	 * @param params
	 *            查询条件
	 * @return List<T>
	 */
	List<T> selectList(Map<String, Object> params);
	
	/**
	 * 
	 * <p>Description: 获取分页的实体对象</p>
	 * @param params
	 * @param sqlId
	 * @param page
	 * @return
	 * @author wangby
	 * @date 2016年4月12日   下午4:51:09
	 */
	List<Object> selectListPage(Map<String, Object> params,String sqlId,Page page);
	
	/**
	 * 
	 * <p>Description: 获取分页的实体对象,使用默认的sqlId</p>
	 * @param params
	 * @param page
	 * @return
	 * @author wangby
	 * @date 2016年4月12日   下午4:51:18
	 */
	List<Object> selectListPage(Map<String, Object> params,Page page);

	/**
	 * 获取指定条件实体数量
	 * 
	 * @param params
	 *            查询条件
	 * @return long
	 */
	long selectCount(Map<String, Object> params);

	/**
	 * 获取指定条件的实体对象JSON数据
	 * 
	 * @param model
	 *            查询条件
	 * @return String
	 */
	String getJson(RequestModel model);

	/**
	 * 通过指定ID查询语句获取数据，可分页
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数,需要分页时，传入rows值
	 * @return Object
	 */
	JSONObject selectListPage(String sqlId, Map<String, Object> params);

	/**
	 * 通过指定ID查询语句获取数据
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	List<Object> selectList(String sqlId, Object params);

	/**
	 * 通过指定ID查询语句获取数据
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	// Object selectOne(String sqlId, Object params);

	/**
	 * 通过指定ID查询语句获取数据数据量
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	// long selectCount(String sqlId, Object params);

}
