/*
 * @(#)WechatSendMsgUtil.java	V0.0.1 Aug 4, 2015, 7:16:32 PM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.util;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.joken.common.utils.JSONUtils;
import com.joken.notice.message.constants.ErrorCode;
import com.joken.notice.message.model.Message;
import com.joken.notice.message.model.ResultModel;

/**
 * 微信发送消息工具类
 * <p>。
 * @version V0.0.1, Aug 4, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class WechatSendMsgUtil {
	private final static Logger log = Logger.getLogger(WechatSendMsgUtil.class);
	
	/**
	 * 发送消息接口
	 * 消息类型,取值范围为text、image、voice、video、file、news
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param message {@link Message} 消息参数
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	public static ResultModel send(Message message){
		if(!CommonUtil.checkMessageEntity(message)){
			log.info("send wechat error: PARAMS_ERROR");
			return new ResultModel(ErrorCode.PARAMS_ERROR);
		}
		if(!CommonUtil.checkMessageType(message)){
			log.info("send wechat error: TYPE_NOT_SUPPORT");
			return new ResultModel(ErrorCode.TYPE_NOT_SUPPORT);
		}
		
		ResultModel result = WechatTokenUtil.getInstance().getToken();
		if(!result.getMsg().toLowerCase().equals("success"))
			return result;
		String postData = new CommonUtil().formatMessagetoJsonString(message).toString();
		HttpRequestHandler hrh = new HttpRequestHandler();
		hrh.setUrl(PropertyGet.getApplicationProperty("WECHAT_SEND_MSG_URL")+result.getData().toString());
		hrh.setPostData(postData);
		String resultJson = hrh.sendPostRequest();
		JSONObject json = JSONUtils.parse(resultJson);
		log.info("send wechat resultCode: "+json.getInteger("errcode"));
		return new ResultModel(json.getInteger("errcode"), json.getString("errmsg"));
	}
	
}
