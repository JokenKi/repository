package com.joken.ice.interceptor;

import Ice.Current;
import Ice.DispatchStatus;
import Ice.Object;
import Ice.ObjectAdapter;
import Ice.Request;

import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.StringUtils;

/**
 * ice正则表达式匹配adaptor和method拦截器
 * 
 * @author wangby
 * @date 2016年6月7日下午3:49:42
 */
public class RegInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -2263232972160948362L;

	public RegInterceptor(Object servant) {
		super(servant);
	}

	@Override
	public DispatchStatus dispatch(Request arg0) {
		Current current = arg0.getCurrent();
		String operation = current.operation;
		if (operation.contains("_")) {
			return servant.ice_dispatch(arg0);
		}
		if (SystemGlobal.getInteger("ice.intercept") != 1) {
			return servant.ice_dispatch(arg0);
		}
		this.doIntercept(current);
		return servant.ice_dispatch(arg0);
	}

	/**
	 * 判断是否执行拦截器方法
	 * 
	 * @param service
	 *            服务名称
	 * @param method
	 *            接口名称
	 * @param regConfig
	 *            正则表达式配置文件名称
	 * 
	 * @return Boolean true:执行 false:不执行
	 * 
	 * @date 2016年6月12日上午10:26:04
	 */
	public Boolean checkExpress(String service, String method, String regConfig) {
		if (SystemGlobal.getInteger("ice.intercept", regConfig) != 1) {
			return false;
		}

		String personalMethodEx = SystemGlobal.get(
				String.valueOf(service + ".method.express"), regConfig);
		String personalMethodExExclude = SystemGlobal.get(
				String.valueOf(service + ".method.express.exclude"), regConfig);

		if (!StringUtils.isEmpty(personalMethodExExclude)
				&& this.checkPersonalMethodReg(personalMethodExExclude, method)) {
			return false;
		}
		if (!StringUtils.isEmpty(personalMethodEx)
				&& this.checkPersonalMethodReg(personalMethodEx, method)) {
			return true;
		}

		String commonMethodEx = SystemGlobal.get("method.express", regConfig);
		String commonMethodExExclude = SystemGlobal.get(
				"method.express.exclude", regConfig);
		if (this.checkCommonReg(commonMethodEx, commonMethodExExclude, method)) {
			return true;
		}
		String serviceEx = SystemGlobal.get("service.express", regConfig);
		String serviceExExclude = SystemGlobal.get("service.express.exclude",
				regConfig);

		if (!this.checkCommonReg(serviceEx, serviceExExclude, service)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否匹配正则
	 * 
	 * @return Boolean true:匹配 false:不匹配
	 * @date 2016年6月12日上午10:50:25
	 */
	public Boolean checkPersonalMethodReg(String ex, String operation) {
		if (StringUtils.isEmpty(ex)) {
			return false;
		}
		if (operation.matches(ex)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否匹配公共服务或接口的正则
	 * 
	 * @return Boolean true:匹配 false:不匹配
	 * @date 2016年6月12日上午10:50:33
	 */
	public Boolean checkCommonReg(String ex, String exExclude, String operation) {
		if (StringUtils.isEmpty(ex)
				|| (!operation.matches(ex) || (!StringUtils.isEmpty(exExclude) && operation
						.matches(exExclude)))) {
			return false;
		}
		return true;
	}

	@Override
	public void doIntercept(Current current) {
		ObjectAdapter adapter = current.adapter;
		String serviceName = adapter.getName();
		String operation = current.operation;

		if (StringUtils.isEmpty(serviceName) || StringUtils.isEmpty(operation)) {
			return;
		}
		String regConfig = null;
		for (IProcesser interceptor : interceptors) {
			regConfig = interceptor.getRegConfig();
			if (StringUtils.isEmpty(regConfig)) {
				continue;
			}
			if (!this.checkExpress(serviceName, operation, regConfig)) {
				continue;
			}
			interceptor.doProcess(current);
		}
	}
}
