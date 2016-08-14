package com.joken.cat;

import org.springframework.stereotype.Service;

import com.joken.api.LogRemoteService;
import com.joken.common.properties.MsgProperties;

/**
 * Cat监控远程调用接口实现类
 * 
 * @version V1.0.0, 2016-4-7
 * @author Hanzibin
 * @since V1.0.0
 */
@Service("EmptyLogRemoteService")
public class EmptyLogRemoteService implements LogRemoteService {

	@Override
	public Object logEvent(String type, String name, String nameValuePairs) {
		return MsgProperties.getSuccess();
	}

}
