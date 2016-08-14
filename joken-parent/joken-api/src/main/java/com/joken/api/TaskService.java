package com.joken.api;

/**
 * 定时任务调度接口类,用于所有需要使用定时任务服务接口
 * 用于调用接口的定时任务优化
 * @Auther Hanzibin
 * @date 6:05:57 PM,Jul 8, 2016
 */
public interface TaskService {

	/**
	 * 调度任务执行接口方法
	 * 
	 * @author 欧阳增高
	 * @date 2015-11-9 下午2:16:49
	 * @return String
	 */
	String executeTask(Object params);
}
