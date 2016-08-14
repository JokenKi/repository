/**
 * 
 */
package com.joken.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joken.base.dao.BaseDao;
import com.joken.common.cache.RedisTemplate;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.model.BaseModel;
import com.joken.common.model.Page;
import com.joken.common.model.RequestModel;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.BeanHelper;
import com.joken.common.utils.JSONUtils;
import com.joken.common.utils.MapUtils;
import com.joken.common.utils.StringUtils;

/**
 * @author 欧阳增高
 */
@SuppressWarnings("unchecked")
public abstract class DefaultBaseService<T extends BaseModel> implements
		BaseService<T> {
	private static Logger logger = LoggerFactory.getLogger("service");

	/**
	 * 注入redis缓存操作类
	 */
	@Autowired(required = false)
	protected RedisTemplate redisTemplate;

	/*
	 * 操作实体类Clazz
	 */
	// private Class<T> entityClazz;

	// /*
	// * 实体关联DAO操作类Clazz
	// */
	// private Class<E> daoClazz;

	/**
	 * 默认构造
	 */
	// public DefaultBaseService() {
	// try {
	// /**
	// * 通过反射获取绑定的DAO实现接口
	// */
	// Type type = getClass().getGenericSuperclass();
	// Type[] types = ((ParameterizedType) type).getActualTypeArguments();
	// if (types.length > 0) {
	// entityClazz = (Class<T>) types[0];
	// // if (types.length > 1) {
	// // daoClazz = (Class<E>) types[1];
	// // }
	// }
	// } catch (Exception e) {
	// }
	// }

	/**
	 * @return the redisTemplate
	 */
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 注入sqlSession操作类
	 */
	// @Resource(name = "sqlSession")
	// private SqlSession sqlSession;
	//
	// /**
	// * @param sqlSession
	// * the sqlSession to set
	// */
	// public void setSqlSession(SqlSession sqlSession) {
	// this.sqlSession = sqlSession;
	// }
	//
	// /**
	// * 当前业务接口Dao操作类,使用泛型反射方式实例化
	// */
	// private E dao;
	//
	// protected E getDao() {
	// if (dao == null) {
	// dao = sqlSession.getMapper(daoClazz);
	// }
	// return dao;
	// }

	public abstract BaseDao<T> getDao();

	/**
	 * 获取泛型实体实例对象
	 * 
	 * @return T
	 */
	// public abstract T getEntityInstance();

	/**
	 * 获取实体对象类名称
	 * 
	 * @return String
	 */
	public abstract String getEntityName();

	// /**
	// * 设置数据操作类
	// *
	// * @param dao
	// */
	// protected void setDao(E dao) {
	// this.dao = dao;
	// }

	// @Override
	// public T getEntityInstance() {
	// try {
	// return entityClazz.newInstance();
	// } catch (InstantiationException e) {
	// } catch (IllegalAccessException e) {
	// }
	// return null;
	// }
	//
	// /**
	// * 获取实体对象类名称
	// *
	// * @return String
	// */
	// public String getEntityName() {
	// return entityClazz.getSimpleName();
	// }

	/**
	 * 实体数据保存前事件，需要实体业务操作类自实现
	 * 
	 * @param tmpBean
	 *            数据实体对象
	 * @param inserted
	 *            是否为新添加
	 * @param model
	 *            请求模型对象
	 * @return boolean,为false时，将不进行数据保存操作
	 */
	protected boolean saveEntityBefore(T tmpBean, boolean inserted,
			RequestModel model) {
		return true;
	}

	/**
	 * 实体数据保存后事件，需要实体业务操作类自实现
	 * 
	 * @param tmpBean
	 *            数据实体对象
	 * @param json
	 *            数据实体JSON对象,方便方法内容添加响应数据
	 * @param inserted
	 *            是否为新添加
	 * @param model
	 *            请求模型对象
	 */
	protected void saveEntityAfter(T tmpBean, JSONObject json,
			boolean inserted, RequestModel model) {
	}

	/**
	 * 将请求数据转换为操作业务实体对象
	 * 
	 * @param model
	 *            请求模型对象
	 * @return T
	 */
	protected T fillRequestData(Map<String, Object> params) {
		// 将request map参数转为bean实体数据
		T tmpBean = this.getEntityInstance();
		BeanHelper.mapToBean(tmpBean, params);

		// JSONObject json = JSONUtils.parseObject(params);
		// System.out.println(json);
		//
		// T tmpBean = (T) JSONUtils.toBean(json, this.getEntityInstance()
		// .getClass());

		return tmpBean;
	}

	/**
	 * 保存提交请求到实体中
	 */
	public ResponseModel save(T bean, RequestModel model) {
		// 检查请求中是否传入了实体对象主键值，如无传入则向数据库添加新记录
		boolean inserted = StringUtils.isEmpty(this.getEntityKeyId(bean));
		if (!inserted && "0".equals(this.getEntityKeyId(bean).toString())) {
			inserted = true;
		}

		/**
		 * 执行保存前自定义业务逻辑
		 */
		if (!this.saveEntityBefore(bean, inserted, model)) {
			return MsgProperties.getRequestVerifyNopass();
		}

		// 实体id值存在时，更新已存在实体数据
		if (!inserted) {
			// 更新数据
			this.update(bean);
		} else {
			// 插入新的数据
			this.insert(bean);
		}

		// 将实体转为JSON对象,方便后续事件参数传入操作
		JSONObject json = (JSONObject) JSONUtils.parseObject(bean);

		// 保存之后自定义业务逻辑
		this.saveEntityAfter(bean, json, inserted, model);

		// 返回实体对象
		return MsgProperties.getSuccess(json);
	}

	/**
	 * 保存提交请求到实体中
	 */
	public ResponseModel save(RequestModel model) {
		Map<String, Object> params = model.getRequest();
		if (params == null || params.size() == 0) {
			return MsgProperties.getFailRequestVerify();
		}
		if (params.containsKey("_editorgrid_json")) {
			try {
				List<T> list = (List<T>) JSONUtils.parseArray(
						params.get("_editorgrid_json").toString(), this
								.getEntityInstance().getClass());
				for (T obj : list) {
					this.save(obj, model);
				}
				return MsgProperties.getSuccess("");
			} catch (Exception e) {
				return MsgProperties.getFail(e.getMessage());
			}
		}
		// 填充请求数据
		T tmpBean = this.fillRequestData(params);

		return this.save(tmpBean, model);
	}

	/**
	 * 插入新的实体对象到数据库
	 */
	public int insert(T bean) {
		if (bean == null) {
			return 0;
		}
		return getDao().insert(bean);
	}

	/**
	 * 更新已存在实体对象数据
	 */
	public int update(T bean) {
		if (bean == null) {
			return 0;
		}
		return getDao().update(bean);
	}

	/**
	 * 删除指定实体对象
	 */
	public int delete(T bean) {
		if (bean == null) {
			return 0;
		}
		return getDao().delete(bean);
	}

	/**
	 * 根据实体对象插入实体数据
	 * 
	 * @param entitys
	 *            实体对象列表
	 * @return　int
	 */
	public int insertBatch(List<T> entitys) {
		if (entitys == null || entitys.size() == 0) {
			return 0;
		}
		return this.getDao().insertBatch(entitys);
	}

	/**
	 * 根据实体keyId/或Map参数删除实体数据
	 * 
	 * @param model
	 *            需要做删除操作的参数，可以为实体keyId或Map键值
	 * @return ResponseModel
	 */
	public ResponseModel deleteByParams(RequestModel model) {
		Map<String, Object> dataMap = model.getRequest();
		// 删除前事件
		if (!this.deleteEntityBefore(dataMap)) {
			return MsgProperties.getFail("0");
		}

		int amount = getDao().deleteByParams(dataMap);

		// 删除后事件
		JSONObject json = this.deleteEntityAfter(dataMap, amount);
		if (json != null) {
			return MsgProperties.getFail("" + amount, json);
		}

		return amount > 0 ? MsgProperties.getSuccess("" + amount)
				: MsgProperties.getFail("");
	}

	/**
	 * 实体数据删除前事件，需要实体业务操作类自实现
	 * 
	 * @param bean
	 *            数据实体对象,或删除参数集
	 * @return boolean,为false时，将不进行数据删除操作
	 */
	protected boolean deleteEntityBefore(Object bean) {
		return true;
	}

	/**
	 * 实体数据删除后事件，需要实体业务操作类自实现
	 * 
	 * @param bean
	 *            数据实体对象,或删除参数集
	 * @param amount
	 *            删除数量
	 */
	protected JSONObject deleteEntityAfter(Object bean, int amount) {
		return null;
	}

	/**
	 * 获取指定id值的实体对象
	 * 
	 * @param id
	 *            实体id值
	 * @return T
	 */
	public T getById(Object id) {
		if (id == null) {
			return null;
		}
		T bean = this.getEntityInstance();
		this.setEntityKeyId(bean, id);
		return getDao().getById(this.getEntityKeyId(bean));
	}

	/**
	 * 获取指定id值的实体对象
	 * 
	 * @param model
	 *            实体id值
	 * @return 实体Map对象
	 */
	public Map<String, Object> getMap(RequestModel model) {
		Object id = model.getRequest(this.getEntityKeyField());
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		// 通过业务实体ID编号获取实体对象
		T bean = this.getById(id);
		if (bean == null) {
			return null;
		}

		JSONObject data = JSONUtils.parseObject(bean);

		// 执行查询实体对象之后业务逻辑
		Map<String, Object> masterData = this.queryAfter(bean, model);
		if (masterData != null) {
			data.putAll(masterData);
		}

		return data;
	}

	/**
	 * 通过指定参数集查询唯一实体对象之后事件
	 * 
	 * @param bean
	 *            实体对象
	 * @param model
	 *            请求参数模型
	 * @return Object 处理结果
	 */
	public Map<String, Object> queryAfter(Object bean, RequestModel model) {
		return null;
	}

	/**
	 * 获取指定参数值的实体对象
	 */
	public T query(Object bean) {
		List<T> list = selectList(beanToMap(bean));
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 获取指定条件的实体对象
	 * 
	 * @param params
	 *            查询条件
	 * @return List<T>
	 */
	@Override
	public List<T> selectList(Map<String, Object> params) {
		return getDao().selectList(params);
	}

	/**
	 * 获取指定条件实体数量
	 * 
	 * @param params
	 *            查询条件
	 * @return long
	 */
	public long selectCount(Map<String, Object> params) {
		return getDao().selectCount(params);
	}

	/**
	 * 将传入参数中的bean转为Map
	 * 
	 * @param bean
	 *            参数传入的bean
	 * @return 转换后的Map对象
	 */
	protected Map<String, Object> beanToMap(Object bean) {
		// Object p = bean;
		if (bean instanceof Map) {
			return (Map<String, Object>) bean;
		}
		return JSONUtils.parseObject(bean);
	}

	/**
	 * 获取指定参数查询的分页JSON数据
	 * 
	 * @param model
	 *            查询参数集
	 * @return 带分页的JSON格式数据
	 */
	public String getJson(RequestModel model) {
		// JSONObject json = new JSONObject();
		// long size = 0l;
		// if (model == null) {
		// json.put("size", size);
		// return json.toJSONString();
		// }
		// Map<String, Object> params = model.getRequest();
		//
		// Page page = this.getPageModel(params);
		//
		// List<T> list = this.selectList(params);
		// size = page.getTotalRecord();
		// if (size == 0 && list.size() > 0) {
		// size = list.size();
		// }
		//
		// // 列表查询后事件
		// JSONArray arr = JSONUtils.parseArray(this.selectAfterEvent(list));
		// // 封装返回JSON数据
		// json.put("rows", arr);
		// json.put("total", size);
		// return json.toJSONString();
		JSONObject json = this.selectListPage("selectList", model.getRequest());
		if (json == null) {
			json = new JSONObject();
			json.put("size", 0l);
		}
		return json.toJSONString();
	}

	/**
	 * 组装分页模型对象
	 * 
	 * @param params
	 *            查询参数
	 * @return Page
	 * @author 欧阳增高
	 * @date 2015-12-10 下午7:21:49
	 */
	protected Page getPageModel(Map<String, Object> params) {
		Page page = Page.newBuilder(0, 0);

		if (MapUtils.isExists(params, "rows")
				&& !StringUtils.isEmpty(params.get("rows"))) {
			Integer rows = Integer.valueOf(params.get("rows").toString());
			if (!params.containsKey("page")) {
				params.put("page", 1);
			}
			Integer pageNo = Integer.valueOf(params.get("page").toString());
			page.setPageNo(pageNo);
			page.setPageSize(rows);
			page.setStart((pageNo - 1) * rows);
			// 判断是否需要使用自定多的数据统计查询
			if (MapUtils.isExists(params, "__countSqlId")) {
				try {
					long size = this.selectCount(params.get("__countSqlId")
							.toString(), params);
					page.setTotalRecord(Long.valueOf(size).intValue());
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} else {
				params.put(Page.QUERY_REQUEST_KEY, page);
			}
		}
		return page;
	}

	/**
	 * 列表查询后数据操作事件
	 * 
	 * @param list
	 *            数据列表
	 * @return 事件后的集合对象
	 * @author 欧阳增高
	 * @date 2015-8-24 上午10:48:44
	 */
	protected Object selectAfterEvent(List<?> list) {
		return list;
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
	@Override
	public List<Object> selectList(String sqlId, Object params) {
		return getDao().selectList(sqlId, params);
	}

	private String getQueryId(String sqlId) {
		Class<?>[] clzss = getDao().getClass().getInterfaces();
		if (clzss == null) {
			return sqlId;
		}
		String daoName = getDao().getClass().getName();
		if (daoName.indexOf("$$") != -1) {
			String[] tmp = daoName.split("\\$\\$");
			String tmpName = tmp[0].replace("impl.", "I");
			return tmpName + "." + sqlId;
		}
		daoName = getDao().getClass().getSimpleName();
		if (daoName.indexOf("com.sun.proxy") != -1) {
			daoName = clzss[0].getSimpleName();
		}
		for (Class<?> c : clzss) {
			if (c.getName().indexOf(daoName) != -1) {
				return c.getName() + "." + sqlId;
			}
		}
		return sqlId;
	}

	@Override
	public JSONObject selectListPage(String sqlId, Map<String, Object> params) {
		JSONObject json = new JSONObject();
		long size = 0l;
		if (params.containsKey("__querySqlId")) {
			sqlId = params.get("__querySqlId").toString();
		}
		if (StringUtils.isEmpty(sqlId) || params == null) {
			json.put("size", size);
			return json;
		}

		Page page = this.getPageModel(params);

		List<Object> list = this.selectList(getQueryId(sqlId), params);
		size = page.getTotalRecord();
		if (size == 0 && list.size() > 0) {
			size = list.size();
		}

		// 列表查询后事件
		JSONArray arr = JSONUtils.parseArray(this.selectAfterEvent(list));
		// 封装返回JSON数据
		json.put("rows", arr);
		json.put("total", size);
		return json;
	}

	@Override
	public List<Object> selectListPage(Map<String, Object> params,
			String sqlId, Page page) {
		List<Object> list = new ArrayList<Object>();
		if (params.containsKey("__querySqlId")) {
			sqlId = params.get("__querySqlId").toString();
		}
		if (StringUtils.isEmpty(sqlId) || params == null) {
			return list;
		}

		if (page == null) {
			page = this.getPageModel(params);
		} else {
			params.put(Page.QUERY_REQUEST_KEY, page);
		}
		String queryId = getQueryId(sqlId);
		list = this.selectList(queryId, params);

		return list;
	}

	@Override
	public List<Object> selectListPage(Map<String, Object> params, Page page) {
		return selectListPage(params, "selectList", page);
	}

	/**
	 * 通过指定ID查询语句获取数据
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	public Object selectOne(String sqlId, Object params) {
		return getDao().getSqlSession().selectOne(sqlId, params);
	}

	/**
	 * 通过指定ID查询语句获取数据数据量
	 * 
	 * @param sqlId
	 *            mapper配置查询的id
	 * @param params
	 *            查询参数
	 * @return Object
	 */
	public long selectCount(String sqlId, Object params) {
		return (Long) this.selectOne(sqlId, params);
	}

	/**
	 * 根据参数判断获取分页模型对象
	 * 
	 * @param params
	 *            查询参数
	 */
	// private void getPageModel(Map<String, Object> params) {
	// if (params.containsKey("rows")) {
	// Integer rows = Integer.valueOf(params.get("rows").toString());
	// if (rows != null && rows > 0) {
	// Integer pageNo = Integer.valueOf(params.get("page").toString());
	// if (pageNo == null || pageNo == 0) {
	// pageNo = 1;
	// }
	// Page page = Page.newBuilder(pageNo, rows);
	// params.put("__page", page);
	// }
	//
	// }
	// }

}
