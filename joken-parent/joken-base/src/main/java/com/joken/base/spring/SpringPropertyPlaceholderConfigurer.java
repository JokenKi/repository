package com.joken.base.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.joken.common.properties.SystemGlobal;
import com.joken.common.properties.VariableExpander;

/**
 * spring关联properties配置实现
 * 
 * @version V1.0.0, 2015-8-14 上午9:05:39
 * @author 欧阳增高
 * @since V1.0.0
 */
public class SpringPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	/**
	 * properties配置属性
	 */
	private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

	/**
	 * 
	 */
	private static VariableExpander EXPANDER = null;

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {

		EXPANDER = new VariableExpander(props, "$(", ")");

		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = EXPANDER.expandVariables(props.getProperty(keyStr));
			ctxPropertiesMap.put(keyStr, value);
			props.setProperty(keyStr, value);
		}
		// 将配置添加到全局配置中
		SystemGlobal.put(props);
		super.processProperties(beanFactoryToProcess, props);
	}

	/**
	 * 获取properties配置属性值
	 * 
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	static Object getProperty(String name) {
		if (ctxPropertiesMap == null) {
			return null;
		}
		return ctxPropertiesMap.get(name);
	}
}
