package com.joken.cat;

import org.springframework.stereotype.Service;

import com.joken.api.LogRemoteService;
import com.joken.cat.util.CatLog;
import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.StringUtils;

/**
 * Cat监控远程调用接口实现类
 * 
 * @version V1.0.0, 2016-4-7
 * @author Hanzibin
 * @since V1.0.0
 */
@Service("CatLogRemoteService")
public class CatLogRemoteServiceImpl implements LogRemoteService {

	@Override
	public Object logEvent(String type, String name, String nameValuePairs) {
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(name)) {
			return MsgProperties.getFailRequestVerify();
		}
		CatLog.logEvent(type, name, nameValuePairs);
		return MsgProperties.getSuccess();
	}

}
