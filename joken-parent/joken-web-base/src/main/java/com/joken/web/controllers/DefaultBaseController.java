package com.joken.web.controllers;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import com.joken.base.service.BaseService;
import com.joken.base.spring.SpringContextUtil;
import com.joken.common.model.BaseModel;
import com.joken.web.controllers.BaseController;
import com.joken.web.controllers.required.LoginRequired;

/**
 * 通用服务视图控制器实现类
 * 
 * @author Inkcar<inkcaridge@qq.com>
 * @since 1.0
 */
@LoginRequired
@Path("{serviceName:[A-Z]{1}[a-zA-Z0-9]+}")
public class DefaultBaseController extends BaseController {
	private BaseService<? extends BaseModel> service;

	/**
	 * 获取业务实现类
	 * 
	 * @return the service
	 */
	protected BaseService<? extends BaseModel> getService() {
		return service;
	}

	/**
	 * 设置业务实现类
	 * 
	 * @param service
	 *            the service to set
	 */
	public void setService(BaseService<? extends BaseModel> service) {
		this.service = service;
	}

	/**
	 * 设置指定名称的业务实现
	 * 
	 * @param serviceName
	 *            业务实现名称
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:51:52
	 */
	@SuppressWarnings("unchecked")
	private void setService(String serviceName) {
		service = (BaseService<? extends BaseModel>) SpringContextUtil
				.getBean(serviceName + "Service");
	}

	/**
	 * 通用列表获取
	 * 
	 * @param serviceName
	 *            需要获取的业务实现名称
	 * @return JSON集合格式数据
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:52:21
	 */
	@Get("list")
	public String list(@Param("serviceName") String serviceName) {
		this.setService(serviceName);
		return super.list();
	}

	/**
	 * 通用实体明细获取
	 * 
	 * @param serviceName
	 *            需要获取的业务实现名称
	 * @return JSON格式数据
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:53:30
	 */
	@Get("detail")
	public String detail(@Param("serviceName") String serviceName) {
		this.setService(serviceName);
		return super.detail();
	}

	/**
	 * 通用实体保存
	 * 
	 * @param serviceName
	 *            需要获取的业务实现名称
	 * @return 处理结束响应数据
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:54:04
	 */
	@Post("save")
	public String save(@Param("serviceName") String serviceName) {
		this.setService(serviceName);
		return super.save();
	}

	/**
	 * 通用删除
	 * 
	 * @param serviceName
	 *            需要获取的业务实现名称
	 * @return 处理结束响应数据
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:54:34
	 */
	@Get("delete")
	public String delete(@Param("serviceName") String serviceName) {
		this.setService(serviceName);
		return super.delete();
	}
}
