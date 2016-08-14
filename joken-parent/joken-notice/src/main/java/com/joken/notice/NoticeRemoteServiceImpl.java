/*
 * @(#)NoticeRemoteService.java	2015-8-21 下午2:40:41
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.joken.api.NoticeRemoteService;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.JSONUtils;
import com.joken.common.utils.StringUtils;
import com.joken.notice.message.SendMessageUtil;
import com.joken.notice.message.model.ResultModel;

/**
 * 报警通知远程调用接口实现类 
 * @version V1.0.0, 2015-8-21
 * @author 欧阳增高
 * @since V1.0.0
 */
@Service("NoticeRemoteService")
public class NoticeRemoteServiceImpl implements NoticeRemoteService {
	private static Logger logger = LoggerFactory.getLogger("notice");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.joken.api.NoticeRemoteService#send(java.lang.String)
	 */
	@Override
	public Object send(String params) {
		if (StringUtils.isEmpty(params)) {
			return MsgProperties.getFailRequestVerify();
		}
		// 将传入的数据转为json对象
		JSONObject json = JSONUtils.parse(params);
		if (json == null || json.size() == 0 || !json.containsKey("phone")
				|| !json.containsKey("msg")) {
			return MsgProperties.getFailRequestVerify();
		}

		// 1, 手机号集合
		List<String> phones = StringUtils.Split(json.getString("phone"), ",");
		if (phones == null || phones.size() == 0) {
			return MsgProperties.getFailRequestVerify();
		}

		// 2, 信息内容
		String msg = json.getString("msg");
		if (StringUtils.isEmpty(msg)) {
			return MsgProperties.getFailRequestVerify();
		}
		// 3, 发送方式: null或""为微信和短信全发, SMS是短信, WECHAT是微信
		String type = json.getString("type");

		// 4,返回结果是 "发送方式" 和 发送结果的map
		Map<String, ResultModel> result = SendMessageUtil.send(phones, msg,
				type);

		logger.info(params + "\n" + result);

		return MsgProperties.getSuccess(JSONUtils.parseObject(result));
	}

	@Override
	public Object sendByPartyId(String msg, String partyId) {
		if(StringUtils.isEmpty(msg) || StringUtils.isEmpty(partyId)){
			return MsgProperties.getFailRequestVerify();
		}
		Map<String, ResultModel> result = SendMessageUtil.sendToParty(msg, partyId);
		
		logger.info(partyId + ":" + msg +"\t" + result);
		
		return MsgProperties.getSuccess(JSONUtils.parseObject(result));
	}

	/*
	 * public static void main(String[] args) { NoticeRemoteService s = new
	 * NoticeRemoteService(); s.send(null); }
	 */
}
