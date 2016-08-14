/*
 * @(#)Send.java	V0.0.1 Aug 7, 2015, 7:32:49 PM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.service;

import java.util.List;

import com.joken.notice.message.model.ResultModel;

/**
 * 信息发送接口
 * <p>。
 * @version V0.0.1, Aug 7, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public interface SendMessageService {
	
	/**
	 * 信息发送方法
	 * @author Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param phones 手机号
	 * @param msg 信息内容
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	ResultModel send(List<String> phones,String msg);
	
}
