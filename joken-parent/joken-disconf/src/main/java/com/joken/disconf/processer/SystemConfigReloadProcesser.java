package com.joken.disconf.processer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.baidu.disconf.client.addons.properties.ReloadablePropertiesBase;
import com.joken.common.properties.SystemConfig;
import com.joken.common.properties.SystemGlobal;

/**
 * 
 * @author wangby
 * @date 2016年8月3日下午2:24:11
 */
public class SystemConfigReloadProcesser implements ApplicationContextAware,
		InitializingBean {

	/**
	 * Spring应用上下文环境
	 */
	private ApplicationContext applicationContext;

	public void doProcess() {
		if (SystemGlobal.getInt(SystemConfig.SYSTEM_CONFIG_DISCONFIG) != 1) {
			return;
		}
		// 获取disconf配置
		ReloadablePropertiesBase o = (ReloadablePropertiesBase) applicationContext
				.getBean("configPropertiesDisconf");
		if (o == null || o.getProperties() == null
				|| o.getProperties().size() < 1) {
			return;
		}
		// 覆盖项目本地文件配置
		SystemGlobal.putAll(o.getProperties());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.doProcess();
	}

}
