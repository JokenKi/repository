package com.joken.ice.interceptor;

import Ice.Current;

public interface IBaseInterceptor {

	void addProcesser(IProcesser interceptor);

	void doIntercept(Current current);
}
