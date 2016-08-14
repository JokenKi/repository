/*
 * @(#)WechatSendMsgUtil.java	V0.0.1 Aug 4, 2015, 2:26:32 PM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信接口返回的错误码
 * @author HZB
 * @time 2015-8-4 16:54:30
 */
public enum ErrorCode {
	
	/**
	 * 调用失败
	 */
	CATCH_EXCEPTION(-100000, "调用失败"),
	/**
	 * 参数错误
	 */
	PARAMS_ERROR(-100001, "参数错误"),
	/**
	 * 不支持的消息类型,暂支持text、image、voice、video、file类型的消息
	 */
	TYPE_NOT_SUPPORT(-100002, "不支持的消息类型,暂支持text、image、voice、video、file类型的消息"),
	
	
	//wechat
	/**
	 * Error:需要https的链接
	 */
	REQUIRE_HTTPS(43003, "Error:需要https的链接"),
	/**
	 * Error:必要参数corpid错误
	 */
	INVALID_CORPID(40013, "Error:必要参数corpid错误"),
	/**
	 * Error:必要参数corpsecret错误
	 */
	INVALID_CREDENTIAL(40001, "Error:必要参数corpsecret错误"),
	/**
	 * Error:缺少必要参数corpid
	 */
	CORPID_MISSING(41002, "Error:缺少必要参数corpid"),
	/**
	 * Error:缺少必要参数corpsecret
	 */
	CORPSECRET_MISSING(41004, "Error:缺少必要参数corpsecret"),
	/**
	 * Error:token已过期
	 */
	ACCESS_TOKEN_EXPIRED(42001, "Error:token已过期"),
	
	
	;
	
	private final int code;
	
	private final String description;
	
	/**
	 * 异常枚举构造函数
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param code 异常昨天码
	 * @param description 异常描述
	 */
	ErrorCode(int code, String description){
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	/**
	 * 获取枚举类Map
	 * @author HZB
	 * @return Map
	 */
	public static Map<Integer, String> getCodeDescMap(){
		Map<Integer, String> result = new HashMap<Integer, String>();
		for (ErrorCode statusEnum : values()) {
			result.put(statusEnum.code, statusEnum.description);
		}
		return result;
	}
	
}
