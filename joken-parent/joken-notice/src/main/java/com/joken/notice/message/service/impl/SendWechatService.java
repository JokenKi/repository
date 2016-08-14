/*
 * @(#)WXSend.java	V0.0.1 Aug 7, 2015, 7:34:42 PM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joken.notice.message.model.Message;
import com.joken.notice.message.model.ResultModel;
import com.joken.notice.message.service.SendMessageService;
import com.joken.notice.message.util.WechatSendMsgUtil;

/**
 * <p>
 * 。 微信发送方法
 * 
 * @version V0.0.1, Aug 7, 2015
 * @author Hanzibin
 * @since V0.0.1
 */
@Service("SendWechatService")
public class SendWechatService implements SendMessageService {

	/**
	 * 发送微信消息
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param phones 手机号
	 * @param msg 信息内容
	 */
	@Override
	public ResultModel send(List<String> phones, String msg) {
		Message entity = new Message();
		entity.setTouserList(phones);
		entity.setContent(msg);
		return WechatSendMsgUtil.send(entity);
	}
	
	
	/**
	 * 发送微信消息
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param message 信息你让
	 * @param partyId 团体id
	 * @return ResultModel
	 */
	public ResultModel send(String message, String partyId){
		Message entity = new Message();
		entity.setToparty(partyId);
		entity.setContent(message);
		return WechatSendMsgUtil.send(entity);
	}
}
