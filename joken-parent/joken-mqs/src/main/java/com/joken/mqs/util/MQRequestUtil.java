package com.joken.mqs.util;

import com.joken.common.utils.StringUtils;
import com.joken.mqs.model.MQCabinetCmd;

public class MQRequestUtil {

	/**
	 * 检查命令
	 * @Auther Hanzibin
	 * @date 4:48:28 PM,Apr 14, 2016
	 * @param cmd
	 * @return 参数不合适返回false
	 */
	public static boolean checkCMD(MQCabinetCmd cmd){
		if(StringUtils.isEmpty(cmd.getCabinetId()) || 
				StringUtils.isEmpty(cmd.getModelId()) || 
				StringUtils.isEmpty(cmd.getContent()) || 
				StringUtils.isEmpty(cmd.getMessageId()) || 
				StringUtils.isEmpty(cmd.getCommand())){
			return false;
		}
		return true;
	}
}
