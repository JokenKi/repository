package com.joken.api;

import com.joken.api.model.MQRequestAbstractModel;

/**
 * MQS请求远程调用接口
 * @Auther Hanzibin
 * @date 4:16:24 PM,Apr 14, 2016
 */
public interface MQRemoteService {

	public boolean send(MQRequestAbstractModel request);
}
