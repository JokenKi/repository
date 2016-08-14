package com.joken.api;

/**
 * 
 * 定时任务调度接口类,用于所有需要使用定时任务服务接口
 * 
 * @version V1.0.0, 2015-11-9
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface TaskSchedulerService {

	/**
	 * 调度任务执行接口方法
	 * 
	 * @author 欧阳增高
	 * @date 2015-11-9 下午2:16:49
	 * @return String
	 */
	String executeTask();
}
