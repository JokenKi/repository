package com.joken.ice.interceptor;

import Ice.Current;

public interface IProcesser {
	void doProcess(Current current);

	String getRegConfig();
}
