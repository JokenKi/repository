/*
 * 配置表达式解析
 */
package com.joken.common.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 系统配置解析操作类--<code>VariableExpander</code>
 * 
 * @author 欧阳增高
 *         <code><p> VariableExpander variable = new VariableExpander(CONFIGS_PROPERTIES, "${", "}")</p></code>
 * 
 */
public class VariableExpander {

	/**
	 * 变量匹配表达式前缀,如"${"
	 */
	private String pre;

	/**
	 * 变量匹配解析表达式后缀,如"}"
	 */
	private String post;

	/**
	 * 匹配缓存
	 */
	private Map<String, Object> cache;

	/**
	 * 配置属性
	 */
	private Properties properties;

	/**
	 * 配置解析构造方法
	 * 
	 * @param properties
	 *            配置属性对象
	 * @param pre
	 *            变量匹配解析表达式前缀,如"${"
	 * @param post
	 *            变量匹配解析表达式后缀,如"}"
	 */
	public VariableExpander(Properties properties, String pre, String post) {
		this.properties = properties;
		this.pre = pre;
		this.post = post;
		cache = new HashMap<String, Object>();
	}

	/**
	 * 清空缓存数据
	 * 
	 * @author 欧阳增高
	 * @date 2016-3-15 上午9:00:29
	 */
	public void clearCache() {
		cache.clear();
	}

	/**
	 * 获取指定扩展变量的匹配值
	 * 
	 * @param source
	 *            需要匹配的变量名
	 * @return 匹配到的配置属性值
	 * @author 欧阳增高
	 * @date 2016-3-15 上午9:00:39
	 */
	public String expandVariables(String source) {
		String result = (String) this.cache.get(source);

		if (source == null || result != null) {
			return result;
		}

		int fIndex = source.indexOf(this.pre);

		if (fIndex == -1) {
			return source;
		}

		StringBuffer sb = new StringBuffer(source);

		while (fIndex > -1) {
			int lIndex = sb.indexOf(this.post);

			int start = fIndex + this.pre.length();
			if (fIndex == 0) {
				String varName = sb.substring(start,
						start + lIndex - this.pre.length());
				sb.replace(fIndex, fIndex + lIndex + 1,
						this.properties.getProperty(varName, ""));
			} else {
				String varName = sb.substring(start, lIndex);
				sb.replace(fIndex, lIndex + 1,
						this.properties.getProperty(varName, ""));
			}

			fIndex = sb.indexOf(this.pre);
		}

		result = sb.toString();

		this.cache.put(source, result);

		return result;
	}
}
