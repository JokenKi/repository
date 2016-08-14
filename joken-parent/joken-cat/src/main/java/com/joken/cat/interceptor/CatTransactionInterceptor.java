/*
 * @(#)AppInnerSignInterceptor.java	2016年3月11日11:36:34
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.cat.interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.joken.cat.util.CatTransaction;
import com.joken.common.utils.JSONUtils;

/**
 * cat调用签名验证类
 * @author Hanzibin
 */
@Repository("CatTransactionInterceptor")
public class CatTransactionInterceptor extends ControllerInterceptorAdapter {
	
	/**
	 * 获取登录注解类
	 */
	@Override
	protected Class<? extends Annotation> getRequiredAnnotationClass() {
		return CatTransaction.class;
	}

	private Transaction transaction = null;
	
	/**
	 * 单点登录验证、必填参数验证检查
	 */
	@Override
	protected Object before(Invocation inv) {
		CatTransaction annotation = inv.getAnnotation(CatTransaction.class);
		String type = annotation.type();
		String name = annotation.name();
		if(StringUtils.isEmpty(type) || StringUtils.isEmpty(name)){
			HttpServletRequest req = inv.getRequest();
			type = req.getRequestURI();
			name = req.getRequestURL().toString();
		}
		transaction = Cat.newTransaction(type, name);
		return true;
	}
	
	@Override
	protected Object after(Invocation inv, Object instruction) {
		transaction.setStatus(Transaction.SUCCESS);
		// 若返回值不是成功,则将错误信息加在数据里返回
		String result = instruction.toString();
		if(result.contains("{")){
			result = result.substring(result.indexOf("{"), result.length());
			JSONObject obj = JSONUtils.parse(result);
			int status = obj.getIntValue("status");
			if(status != 1001){
				transaction.addData((String)obj.get("result"));
			}
		}else if(!result.contains("1001")){
			transaction.addData(result);	
		}
		transaction.complete();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return instruction;
	}

}
