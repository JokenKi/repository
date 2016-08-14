/**
 * 
 */
package com.joken.web.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import net.paoding.rose.web.Invocation;

import org.springframework.beans.factory.annotation.Autowired;

import com.joken.base.service.BaseService;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.model.RequestModel;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.properties.SystemProperties;
import com.joken.common.utils.JSONUtils;
import com.joken.common.utils.StringUtils;

/**
 * 控制器基础抽象类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public abstract class BaseController {
	/**
	 * 日志记录
	 */
	private final static Logger logger = LoggerFactory.getLogger("controller");

	/**
	 * 注入rose架构操作类
	 */
	@Autowired
	protected Invocation inv;

	/**
	 * 获取业务操作类
	 * 
	 * @return 业务实现对象
	 */
	protected abstract BaseService<?> getService();

	/**
	 * 获取list数据
	 * 
	 * @return JSON集合格式数据
	 */
	public String list() {
		return getJsonWrite(this.getService().getJson(getRequestMap()));
	}

	/**
	 * 获取实体详细数据
	 * 
	 * @return JSON格式数据
	 */
	public String detail() {
		// 获取关联业务数据
		Map<String, Object> data = this.getService().getMap(getRequestMap());

		// 获取视图jsp路径
		String path = this.forwardPage();
		if (!StringUtils.isEmpty(path)) {
			inv.addModel("form", data);
			return path;
		}
		// System.out.println(bean);
		if (data == null) {
			return getJsonWrite(MsgProperties.getRequestNoData());
		}
		// return
		// getJsonWrite(MsgProperties.getSuccess(JSONUtils.parseObject(bean)));
		return getJsonWrite(MsgProperties.getSuccess(JSONUtils
				.parseObject(data)));
	}

	/**
	 * 保存实体数据
	 * 
	 * @return 处理结束响应数据
	 */
	public String save() {
		return getJsonWrite(this.getService().save(getRequestMap()));
	}

	/**
	 * 删除实体数据
	 * 
	 * @return 处理结束响应数据
	 */
	public String delete() {
		ResponseModel msg = this.getService().deleteByParams(getRequestMap());
		String path = this.forwardPage();
		if (!StringUtils.isEmpty(path)) {
			inv.addModel("msg", msg);
			return path;
		}
		return getJsonWrite(msg);
	}

	/**
	 * 获取当前请求参数模型对象实例
	 * 
	 * @return RequestModel
	 */
	@SuppressWarnings("unchecked")
	protected RequestModel getRequestMap() {
		Map<String, Object> map = new HashMap<String, Object>();

		// 请求参数键名
		String key = null;

		// 请求参数值
		String[] values;
		Map<String, String[]> params = inv.getRequest().getParameterMap();
		if (params != null && params.size() > 0) {
			for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
				key = it.next();
				values = params.get(key);
				if (values.length > 1) {
					map.put(key + "s", values);
				} else {
					String val = values[0];
					// if (!StringUtils.isEmpty(val)) {
					// try {
					// val = new String(val.getBytes("ISO-8859-1"),
					// SystemProperties.getEncoding());
					// } catch (UnsupportedEncodingException e) {
					// }
					// }
					map.put(key, val);
				}
			}

			if (this.getService() != null) {
				// 获取业务主键字段
				String keyField = this.getService().getEntityKeyField();
				if (params.containsKey(keyField)) {
					String value = map.get(keyField).toString();
					if (value.indexOf(",") != -1) {
						map.put("entityKeyValues", value.split(","));
					}
				}
			}

			// 查询条件
			if (map.containsKey("searchParams")) {
				if (StringUtils.isEmpty(map.get("searchParams"))) {
					map.remove("searchParams");
				} else {
					List<Object> list = JSONUtils.toJSONArray(map.get(
							"searchParams").toString());
					if (list.size() > 0)
						map.put("searchParams", list);
				}
			}

		}
		HttpSession session = inv.getRequest().getSession();
		Map<String, Object> sess = new HashMap<String, Object>();
		for (Enumeration<String> enu = session.getAttributeNames(); enu
				.hasMoreElements(); sess.put(key, session.getAttribute(key))) {
			key = (String) enu.nextElement();
		}

		// 采用cookie应对分布式时，session已经不再存储token等重要信息，改为从cookie获取
		Cookie[] cookies = inv.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				sess.put(cookie.getName(), cookie.getValue());
			}
		}

		RequestModel requestModel = (new RequestModel.Builder(map)).setSession(
				sess).build();
		return requestModel;
	}

	/**
	 * 获取返回视图
	 * 
	 * @return String
	 */
	protected String forwardPage() {
		try {
			String path = inv.getParameter("_path");
			if (StringUtils.isEmpty(path)) {
				return null;
			}
			return URLDecoder.decode(path, SystemProperties.getEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 组装rose标准响应字符串
	 * 
	 * @param msg
	 *            需要返回的数据
	 * @return String
	 */
	protected String getWrite(String msg) {
		logger.info("请求响应：" + msg);
		return "@" + msg;
	}

	/**
	 * 组装rose标准响应json
	 * 
	 * @param msg
	 *            需要返回的数据
	 * @return String
	 */
	protected String getJsonWrite(Object msg) {
		return getWrite("json:" + msg.toString());
	}

	/**
	 * 获取客户IP地址
	 * 
	 * @return String
	 */
	protected String getIpAddr() {
		if (inv.getRequest().getHeader("x-forwarded-for") == null) {
			return inv.getRequest().getRemoteAddr();
		}
		return inv.getRequest().getHeader("x-forwarded-for");
	}
}
