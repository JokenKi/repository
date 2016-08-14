package com.joken.notice.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joken.notice.message.model.ResultModel;

/**
 * 调用demo
 * <p>。
 * @version V0.0.1, Aug 12, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class SendMessageDemo {

	public static void main(String[] args){
		//参数:
		//1, 手机号集合
		List<String> phones = new ArrayList<String>();
		phones.add("15510059334");
		//2, 信息内容
		String msg = "信息内容";
		//3, 发送方式: null或""为微信和短信全发, SMS是短信, WECHAT是微信
		String type = "SMS";
		
		//4,返回结果是 "发送方式" 和 发送结果的map
		Map<String,ResultModel> result = SendMessageUtil.send(phones, msg, type);
		System.out.println(result.get("SMS").getMsg());
	}
}
