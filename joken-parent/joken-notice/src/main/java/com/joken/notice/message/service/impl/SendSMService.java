/*
 * @(#)SMSSend.java	V0.0.1 Aug 7, 2015, 7:34:02 PM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joken.notice.message.model.ResultModel;
import com.joken.notice.message.service.SendMessageService;
import com.joken.notice.message.util.SMSendUtil;

/**
 * <p>
 * 。 短信发送实现
 * 
 * @version V0.0.1, Aug 7, 2015
 * @author Hanzibin
 * @since V0.0.1
 */
@Service("SendSMService")
public class SendSMService implements SendMessageService {

	/**
	 * 发送短信息
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * 
	 * @param phones 手机号
	 * @param msg 信息内容
	 */
	@Override
	public ResultModel send(List<String> phones, String msg) {
		String[] mobiles = phones.toArray(new String[phones.size()]);
		return SMSendUtil.getInstance().sendSMS(mobiles, msg, 5);
	}

}
