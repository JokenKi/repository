package com.joken.ice.utils;

import junit.framework.TestCase;

import com.joken.common.properties.SystemGlobal;
import com.joken.ice.interceptor.RegInterceptor;

/**
 * ice 拦截器测试用例
 * 
 * @author wangby
 * @date 2016年6月6日下午1:56:40
 */
public class TestIntercepter extends TestCase {

	public void testReg() {
		System.out.println(SystemGlobal.getInteger("ice.intercept"));
		System.out.println("com.joken.catering.rpc.ucenter.UCenterService"
				.matches(SystemGlobal.get("service.express")));
		System.out.println("com.joken.catering.rpc.news.NewsService"
				.matches(SystemGlobal.get("service.express.exclude")));
		System.out
				.println("support".matches(SystemGlobal.get("method.express")));
	}

	public void checkExpress() {
		RegInterceptor processer = new RegInterceptor(null);
		System.out.println(processer.checkExpress(
				"com.joken.catering.rpc.forum.ForumService", "cancelSupport",
				"uservalidate"));
	}
}
