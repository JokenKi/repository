package com.joken.notice.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.joken.notice.message.constants.ErrorCode;
import com.joken.notice.message.model.ResultModel;
import com.joken.notice.message.service.SendMessageService;
import com.joken.notice.message.service.impl.SendSMService;
import com.joken.notice.message.service.impl.SendWechatService;

/**
 * <p>发送消息工具类
 * @version V0.0.1, Aug 10, 2015
 * @author Hanzibin
 * @since V0.0.1
 */
public class SendMessageUtil {

	/**
	 * 发送消息
     * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param phones   联系人
	 * @param message  内容
	 * @param type  类型,WECHAT为微信,SMS为短信,null或""为全发
	 * @return Map<String, ResultModel> 微信发送结果 发送结果{@link ResultModel}
	 */
	public static Map<String, ResultModel> send(List<String> phones,
			String message, String type) {
		Map<String, ResultModel> resultMap = new HashMap<String, ResultModel>();

		if (phones.isEmpty() || StringUtils.isBlank(message)) {
			resultMap.put(type, new ResultModel(ErrorCode.PARAMS_ERROR));
			return resultMap;
		}

		SendMessageService service = null;

		if (StringUtils.isBlank(type)) {// 发两种
			service = new SendSMService();
			resultMap.put("SMS", service.send(phones, message));
			service = new SendWechatService();
			resultMap.put("WECHAT", service.send(phones, message));
		} else if (type.toUpperCase().equals("SMS")) {
			service = new SendSMService();
			resultMap.put("SMS", service.send(phones, message));
		} else if (type.toUpperCase().equals("WECHAT")) {
			service = new SendWechatService();
			resultMap.put("WECHAT", service.send(phones, message));
		} else {
			resultMap.put(type, new ResultModel(ErrorCode.PARAMS_ERROR));
			return resultMap;
		}
		return resultMap;
	}
	
	/**
	 * 向部门所有人发消息
     * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param message 消息内容
	 * @param partyId 部门id
	 * @return Map<String, ResultModel> 微信发送结果 发送结果{@link ResultModel}
	 */
	public static Map<String, ResultModel> sendToParty(String message,
			String partyId) {
		Map<String, ResultModel> resultMap = new HashMap<String, ResultModel>();
		SendWechatService service = new SendWechatService();
		resultMap.put("WECHAT", service.send(message, partyId));
		return resultMap;
	}
}
