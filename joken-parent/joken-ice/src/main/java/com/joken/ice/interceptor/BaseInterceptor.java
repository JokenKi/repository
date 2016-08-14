package com.joken.ice.interceptor;

import java.util.ArrayList;
import java.util.List;

import Ice.Current;
import Ice.DispatchInterceptor;
import Ice.DispatchStatus;
import Ice.Object;
import Ice.Request;

public class BaseInterceptor extends DispatchInterceptor implements
		IBaseInterceptor {

	private static final long serialVersionUID = 2871386885260810762L;

	protected Object servant;

	protected List<IProcesser> interceptors = new ArrayList<IProcesser>();

	public BaseInterceptor(Object servant) {
		this.servant = servant;
	}

	@Override
	public void addProcesser(IProcesser interceptor) {
		interceptors.add(interceptor);
	}

	@Override
	public void doIntercept(Current current) {
	}

	@Override
	public DispatchStatus dispatch(Request arg0) {
		return servant.ice_dispatch(arg0);
	}
}
