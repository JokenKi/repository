/*
 * @(#)QuartzTaskService.java	2015-11-9 下午2:22:39
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.quartz.task;

import com.joken.api.TaskService;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.utils.StringUtils;

/**
 * 定时任务调度接口类,用于所有需要使用定时任务服务接口
 * 用于调用接口的定时任务优化
 * @Auther Hanzibin
 * @date 6:05:57 PM,Jul 8, 2016
 */
public class TaskSchedulerWithParamService {

	/**
	 * 任务描述,用于记录日志使用
	 */
	private String taskDesc;
	
	/**
	 * 请求参数
	 */
	private Object params;
	/**
	 * 日志记录
	 */
	private static Logger logger = LoggerFactory.getLogger("TaskScheduler");

	/**
	 * 定时任务业务处理实现接口
	 */
	private TaskService service;

	/**
	 * 设置业务实现对象
	 * 
	 * @param service
	 *            the service to set
	 */
	public void setService(TaskService service) {
		this.service = service;
	}

	/**
	 * @param taskDesc
	 *            the taskDesc to set
	 */
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	
	/**
	 * @return the params
	 */
	public Object getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Object params) {
		this.params = params;
	}

	/**
	 * 定时任务入口方法
	 * 
	 * @author 欧阳增高
	 * @date 2015-11-9 下午2:25:39
	 */
	public void execute() {
		if (!StringUtils.isEmpty(taskDesc)) {
			logger.debug("[" + taskDesc + "]定时任务执行开始");
		}
		String result = service.executeTask(params);

		if (!StringUtils.isEmpty(taskDesc)) {
			logger.debug("[" + taskDesc + "]结果:" + result);
		}
	}
}
