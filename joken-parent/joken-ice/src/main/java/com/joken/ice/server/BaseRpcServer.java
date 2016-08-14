/*
 * @(#)BaseRpcServer.java	2016-7-19 上午9:42:59
 *
 * Copyright 2016 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.ice.server;

import Ice.Communicator;
import Ice.DispatchInterceptor;
import Ice.Identity;
import Ice.ObjectAdapter;
import Ice.ObjectImpl;
import IceBox.Service;

import com.joken.ice.interceptor.IBaseInterceptor;
import com.joken.ice.interceptor.IProcesser;
import com.joken.ice.interceptor.RegInterceptor;

/**
 * 基础ICE远程服务类
 * 
 * @version V1.0.0, 2016-7-19
 * @author 欧阳增高
 * @since V1.0.0
 */
public abstract class BaseRpcServer implements Service {
	private ObjectAdapter objectAdapter;

	/**
	 * 获取服务接口实现类
	 * 
	 * @return ObjectImpl
	 */
	protected abstract ObjectImpl getRpcServiceImpl();

	/**
	 * 获取服务前置处理对象
	 * 
	 * @return
	 */
	protected IProcesser getProcesser() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IceBox.Service#start(java.lang.String, Ice.Communicator,
	 * java.lang.String[])
	 */
	@Override
	public void start(String arg0, Communicator communicator, String[] arg2) {
		objectAdapter = communicator.createObjectAdapter(arg0);
		IProcesser processer = getProcesser();
		Identity identity = communicator.stringToIdentity(arg0);
		if (processer == null) {
			objectAdapter.add(getRpcServiceImpl(), identity);
			objectAdapter.activate();
			return;
		}
		IBaseInterceptor interceptor = new RegInterceptor(getRpcServiceImpl());
		interceptor.addProcesser(processer);
		objectAdapter.add((DispatchInterceptor) interceptor, identity);
		objectAdapter.activate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see IceBox.Service#stop()
	 */
	@Override
	public void stop() {
		if (this.objectAdapter != null) {
			this.objectAdapter.destroy();
		}
	}

}
