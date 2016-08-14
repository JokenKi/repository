/*
 * @(#)QuartzTaskService.java	2015-11-9 下午2:22:39
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.quartz.task;

import com.joken.api.TaskSchedulerService;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.utils.StringUtils;

/**
 * 默认定时任务实现类
 * 
 * @version V1.0.0, 2015-11-9
 * @author 欧阳增高
 * @since V1.0.0
 */
public class QuartzTaskService {

	/**
	 * 任务描述,用于记录日志使用
	 */
	private String taskDesc;
	/**
	 * 日志记录
	 */
	private static Logger logger = LoggerFactory.getLogger("TaskScheduler");

	/**
	 * 定时任务业务处理实现接口
	 */
	private TaskSchedulerService service;

	/**
	 * 设置业务实现对象
	 * 
	 * @param service
	 *            the service to set
	 */
	public void setService(TaskSchedulerService service) {
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
	 * 定时任务入口方法
	 * 
	 * @author 欧阳增高
	 * @date 2015-11-9 下午2:25:39
	 */
	public void execute() {
		if (!StringUtils.isEmpty(taskDesc)) {
			logger.debug("[" + taskDesc + "]定时任务执行开始");
		}
		String result = service.executeTask();

		if (!StringUtils.isEmpty(taskDesc)) {
			logger.debug("[" + taskDesc + "]结果:" + result);
		}
	}
}
