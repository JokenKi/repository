/*
 * @(#)TestTaskService.java	2015-11-9 下午2:39:18
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.quartz.task.test;

import com.joken.api.TaskSchedulerService;

/**
 * 定时任务测试类
 * 
 * @version V1.0.0, 2015-11-9
 * @author 欧阳增高
 * @since V1.0.0
 */
public class TestTaskService implements TaskSchedulerService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.joken.api.TaskSchedulerService#executeTask()
	 */
	@Override
	public String executeTask() {
		return "测试定时任务完成了";
	}

}
