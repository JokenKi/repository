/*
 * @(#)NoticeRemoteService.java	2015-8-21 下午2:36:06
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.api;

/**
 * 报警通知远程调用接口
 * 
 * @version V1.0.0, 2015-8-21
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface NoticeRemoteService {

	/**
	 * 报警通知发送接口
	 * 
	 * @param params
	 *            发送参数，以json字符串方式设置
	 *            <p>
	 *            {"phone":"13652147896,13695874258",
	 *            "msg":"打印机缺纸，请及时换纸","type":""}
	 *            <ul>
	 *            <li>phone：[必填]接收通知手机号</li>
	 *            <li>msg：[必填]通知内容</li>
	 *            <li>type：[选填]发送类型[SMS是短信, WECHAT是微信,不传则短信微信都发送]</li>
	 *            </ul>
	 *            </p>
	 * @return json格式字符串
	 * @author 欧阳增高,2015-8-21 下午2:37:56
	 */
	Object send(String params);

	/**
	 * 给指定部门人员发送报警通知
	 * 
	 * @param msg
	 *            报警信息内容
	 * @param partyId
	 *            接收报警的部门标识
	 * @return Object
	 * @author 王波洋
	 * @date 2016-3-14 下午5:28:31
	 */
	Object sendByPartyId(String msg, String partyId);
}
