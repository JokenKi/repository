/**
 * 
 */
package com.joken.common.model;

import java.util.Map;

/**
 * 数据请求封装模型对象
 * 
 * @author inkcar
 * @version 0.0.0.1 上午11:39:28
 */
public class RequestModel extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 请求参数模型构建者类
	 * 
	 * @author inkcar
	 * @version 0.0.0.1 下午12:01:36
	 */
	public static class Builder {
		/**
		 * 请求参数模型
		 */
		private RequestModel model;

		/**
		 * 构造函数
		 * 
		 * @param request
		 *            包装为map后的请求参数
		 */
		public Builder(Map<String, Object> request) {
			model = new RequestModel(request);
		}

		/**
		 * 设置请求参数
		 * 
		 * @param request
		 *            包装为map后的请求参数
		 * @return Builder
		 */
		public Builder setRequest(Map<String, Object> request) {
			model.setRequest(request);
			return this;
		}

		/**
		 * 设置会话参数
		 * 
		 * @param session
		 *            包装为map后的session参数
		 * @return Builder
		 */
		public Builder setSession(Map<String, Object> session) {
			model.setSession(session);
			return this;
		}

		/**
		 * 构建模型类
		 * 
		 * @return RequestModel
		 */
		public RequestModel build() {
			return model;
		}
	}

	/**
	 * Http请求参数包装
	 */
	private Map<String, Object> request;
	/**
	 * Http Session包装
	 */
	private Map<String, Object> session;

	/**
	 * 私有构造
	 */
	private RequestModel() {
	}

	/**
	 * 私有构造
	 * 
	 * @param request
	 *            请求参数包装
	 */
	private RequestModel(Map<String, Object> request) {
		this.request = request;
	}

	/**
	 * 获取请求参数
	 * 
	 * @return the request
	 */
	public Map<String, Object> getRequest() {
		return request;
	}

	/**
	 * 获取指定请求参数值
	 * 
	 * @param name
	 *            参数名称
	 * @return Object
	 */
	public Object getRequest(String name) {
		if (request == null) {
			return null;
		}
		return request.get(name);
	}

	/**
	 * 参数请求参数
	 * 
	 * @param request
	 *            the request to set
	 */
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	/**
	 * 获取Session属性值
	 * 
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * 获取session值
	 * 
	 * @param name
	 *            session属性名称
	 * @return the session
	 */
	public Object getSession(String name) {
		if (session == null) {
			return null;
		}
		return session.get(name);
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
