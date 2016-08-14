package com.joken.ice.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;

import com.joken.common.utils.StringUtils;

/**
 * 
 * <p>
 * Title: IceClientUtil
 * </p>
 * <p>
 * Description: ice客户端工具类
 * </p>
 * 
 * @author 王波洋
 * @date 2016年3月31日 上午9:29:46
 */
public class IceClientUtil {
	/**
	 * ice客户端与服务端交互主要对象，单例
	 */
	private static volatile Communicator ic = null;
	/**
	 * 代理对象缓存
	 */
	@SuppressWarnings("rawtypes")
	private static Map<Class, ObjectPrx> class2PrxMap = new HashMap<Class, ObjectPrx>();

	private static String iceLocator = null;
	private static String threadPoolClientSize = null;
	private static String threadPoolClientSizeMax = null;

	private final static String locatorKey = "--Ice.Default.Locator";
	private final static String threadPoolClientSizeKey = "--Ice.ThreadPool.Client.Size";
	private final static String threadPoolClientSizeMaxKey = "--Ice.ThreadPool.Client.SizeMax";

	private static Communicator initCommunicator(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}// iceclient_default
		ResourceBundle rb = ResourceBundle.getBundle("properties/" + fileName,
				Locale.ENGLISH);
		iceLocator = rb.getString(locatorKey);
		threadPoolClientSize = rb.getString(threadPoolClientSizeKey);
		threadPoolClientSizeMax = rb.getString(threadPoolClientSizeMaxKey);
		System.out.println("iceLocator:" + iceLocator);
		System.out.println("Ice Client Locator:" + iceLocator);
		System.out.println("Ice Client TreadPool Size:" + threadPoolClientSize);
		System.out.println("Ice Client TreadPool Max Size:"
				+ threadPoolClientSizeMax);
		String[] intParams = new String[] { locatorKey + "=" + iceLocator,
				threadPoolClientSizeKey + "=" + threadPoolClientSize,
				threadPoolClientSizeMaxKey + "=" + threadPoolClientSizeMax };
		return Util.initialize(intParams);
	}

	public static Communicator getIcecCommunicator() {
		if (ic != null) {
			return ic;
		}
		synchronized (IceClientUtil.class) {
			if (ic == null) {
				ic = initCommunicator("iceclient");
				if (ic == null) {
					ic = initCommunicator("iceclient_default");
				}
			}
		}
		return ic;
	}

	/**
	 * 
	 * <p>
	 * Description: 创建客户端服务代理对象
	 * </p>
	 * 
	 * @author 王波洋
	 * @return ObjectPrx
	 * @date 2016年3月31日 上午11:16:42
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ObjectPrx createIceProxy(Communicator communicator,
			Class serviceClasss, String version) {
		ObjectPrx prx = null;
		String serviceName = serviceClasss.getSimpleName();
		int pos = serviceName.lastIndexOf("Helper");

		if (pos < 0) {
			throw new IllegalArgumentException(
					"Invalid ObjectPrx class,class name must end with Helper");
		}
		try {
			Object o = serviceClasss.getMethod("ice_staticId").invoke(null);
			StringBuffer objName = new StringBuffer();
			objName.append(o.toString().replaceAll("::", ".").substring(1));
			if (version != null && !"".equals(version)) {
				objName.append(".").append(version);
			}
			ObjectPrx base = communicator.stringToProxy(objName.toString());
			prx = (ObjectPrx) serviceClasss.getMethod("checkedCast",
					ObjectPrx.class).invoke(null, base);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prx;
	}

	/**
	 * 
	 * <p>
	 * Description: 获取客户端服务代理对象
	 * </p>
	 * 
	 * @author 王波洋
	 * @return ObjectPrx
	 * @date 2016年3月31日 上午11:15:50
	 */
	@SuppressWarnings("rawtypes")
	public static ObjectPrx getServicePrx(Class serviceClass) {
		ObjectPrx prx = createIceProxy(getIcecCommunicator(), serviceClass,
				null);
		class2PrxMap.put(serviceClass, prx);
		return prx;
	}

	/**
	 * 
	 * <p>
	 * Description: 获取客户端服务代理对象
	 * </p>
	 * 
	 * @author 王波洋
	 * @return ObjectPrx
	 * @date 2016年3月31日 上午11:15:50
	 */
	@SuppressWarnings("rawtypes")
	public static ObjectPrx getServicePrx(Class serviceClass, String version) {
		ObjectPrx prx = createIceProxy(getIcecCommunicator(), serviceClass,
				version);
		class2PrxMap.put(serviceClass, prx);
		return prx;
	}

	/**
	 * 
	 * <p>
	 * Description: 关闭Communicator,释放资源
	 * </p>
	 * 
	 * @author 王波洋
	 * @return void
	 * @date 2016年3月31日 上午10:31:28
	 */
	public static void closeCommunicator() {
		synchronized (IceClientUtil.class) {
			if (ic != null) {
				safeShutDown();
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Description: 关闭Communicator
	 * </p>
	 * 
	 * @author 王波洋
	 * @return void
	 * @date 2016年3月31日 上午11:17:51
	 */
	private static void safeShutDown() {
		try {
			ic.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ic.destroy();
			ic = null;
		}
	}

}
