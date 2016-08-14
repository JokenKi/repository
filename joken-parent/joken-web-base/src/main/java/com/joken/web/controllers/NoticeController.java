/*
 * @(#)NoticeController.java	2015-9-22 下午1:40:17
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.web.controllers;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.joken.api.NoticeRemoteService;
import com.joken.base.service.BaseService;
import com.joken.common.properties.MsgProperties;

/**
 * 通知控制器类
 * 
 * @version V1.0.0, 2015-9-22
 * @author 欧阳增高
 * @since V1.0.0
 */
@Path("notice/send")
public class NoticeController extends BaseController {

	/**
	 * 注入用户登录接口实现类
	 */
	@Autowired(required = false)
	private NoticeRemoteService noticeRemoteService;

	/**
	 * 设置通知业务处理对象
	 * 
	 * @param noticeRemoteService
	 *            the noticeRemoteService to set
	 */
	public void setNoticeRemoteService(NoticeRemoteService noticeRemoteService) {
		this.noticeRemoteService = noticeRemoteService;
	}

	/**
	 * 发送通知信息
	 * 
	 * @param phone
	 *            通知接收号
	 * @param msg
	 *            通知内容
	 * @param type
	 *            通知发送类型,SMS是短信, WECHAT是微信
	 * @return 通知发送处理结果
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:58:37
	 */
	@Post("")
	public String send(@Param("phone") String phone, @Param("msg") String msg,
			@Param("type") String type) {
		if (noticeRemoteService == null) {
			return this.getJsonWrite(MsgProperties.getFail());
		}
		JSONObject params = new JSONObject();
		params.put("phone", phone);
		params.put("msg", msg);
		params.put("type", type);
		Object result = noticeRemoteService.send(params.toJSONString());
		return this.getJsonWrite(result);
	}

	/**
	 * 通过不同类型进行发送
	 * 
	 * @param phone
	 *            手机号
	 * @param msg
	 *            手机短信内容
	 * @param type
	 *            通知发送类型,SMS是短信, WECHAT是微信
	 * @return 通知发送处理结果
	 * @author 欧阳增高
	 * @date 2015-9-22 下午4:47:02
	 */
	@Post("{type:[a-z]+}")
	public String sendByType(@Param("phone") String phone,
			@Param("msg") String msg, @Param("type") String type) {
		return this.send(phone, msg, type.toUpperCase());
	}

	/**
	 * 给部门全体人员发消息
	 * 
	 * @param msg
	 *            消息内容
	 * @param partyId
	 *            部门id
	 * @return String
	 */
	@Post("toParty")
	public String sendByPartyId(@Param("msg") String msg,
			@Param("partyId") String partyId) {
		Object o = noticeRemoteService.sendByPartyId(msg, partyId);
		return this.getJsonWrite(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.joken.web.controllers.BaseController#getService()
	 */
	@Override
	protected BaseService<?> getService() {
		return null;
	}

}
