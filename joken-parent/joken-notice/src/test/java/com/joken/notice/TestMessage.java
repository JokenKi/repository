/*
 * @(#)TestMessage.java	V0.0.1 Aug 21, 2015, 9:48:25 AM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joken.common.utils.JSONUtils;
import com.joken.notice.message.SendMessageUtil;
import com.joken.notice.message.model.ResultModel;

public class TestMessage {

	public static void main(String[] args) {
		// 1, 手机号集合
		List<String> phones = new ArrayList<String>();
		phones.add("18689225187");
		// 2, 信息内容
		String msg = "信息内容";
		// 3, 发送方式: null或""为微信和短信全发, SMS是短信, WECHAT是微信
		String type = "";

		// 4,返回结果是 "发送方式" 和 发送结果的map
		Map<String, ResultModel> result = SendMessageUtil.send(phones, msg,
				type);
		System.out.println(JSONUtils.parseObject(result));
		System.out.println(result.get("WECHAT").getMsg());

	}
}
