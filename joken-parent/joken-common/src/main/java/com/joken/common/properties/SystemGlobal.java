/**
 * 
 */
package com.joken.common.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.joken.common.utils.FileUtils;
import com.joken.common.utils.StringUtils;

/**
 * 系统配置操作类--<code>SystemGlobal</code>
 * 
 * @author 欧阳增高
 * @date 2012-05-10 10:03
 */
public class SystemGlobal {
	// private static SystemGlobal SYSTEMGLOBAL = null;
	private static Logger logger = Logger.getLogger("SystemGlobal");
	/**
	 * 配置对象
	 */
	private static Properties CONFIGS_PROPERTIES = null;
	/**
	 * 表达式模式对象
	 */
	private static VariableExpander EXPANDER = null;

	/**
	 * 按文件名为key的配置map对象
	 */
	private static Map<String, Properties> DIVIDE_CONFIGS_PROPERTIES = null;

	/**
	 * 初始化块
	 */
	static {
		init();
	}

	/**
	 * 初始化配置
	 */
	private static void init() {
		if (CONFIGS_PROPERTIES != null) {
			return;
		}

		CONFIGS_PROPERTIES = loadProperties(SystemProperties
				.getGlobalPropertiesPath());

		EXPANDER = new VariableExpander(CONFIGS_PROPERTIES, "${", "}");

		/**
		 * 载入其他配置文件
		 */
		String properties = SystemProperties.getGroupPropertiesPath();
		if (!StringUtils.isEmpty(properties)) {
			String[] pros = properties.split(",");
			for (String path : pros) {
				put(path);
			}
		}

		String dividePropertiesPath = SystemProperties
				.getDividePropertiesPath();

		if (!StringUtils.isEmpty(dividePropertiesPath)) {
			DIVIDE_CONFIGS_PROPERTIES = new HashMap<String, Properties>();
			String[] pros = dividePropertiesPath.split(",");
			String fileName = null;
			int tmp = 0;
			for (String path : pros) {
				tmp = path.lastIndexOf("/") + 1;
				fileName = path.substring(tmp, path.indexOf("."));
				put(path, fileName);
			}
		}

	}

	/**
	 * 私有构造
	 */
	private SystemGlobal() {
	}

	/**
	 * 将指定路径的配置文件载入到系统中
	 * 
	 * @param propertiesPath
	 *            配置文件路径
	 */
	public static void put(final String propertiesPath) {
		if (StringUtils.isEmpty(propertiesPath)) {
			return;
		}
		Properties properties = loadProperties(propertiesPath);
		for (Object key : properties.keySet()) {
			CONFIGS_PROPERTIES.put(key, properties.get(key));
		}
	}

	/**
	 * 将指定路径的配置文件载入到系统中
	 * 
	 * @param propertiesPath
	 *            配置文件路径
	 */
	public static void put(final String propertiesPath, final String fileName) {
		if (StringUtils.isEmpty(propertiesPath)
				|| StringUtils.isEmpty(fileName)) {
			return;
		}
		DIVIDE_CONFIGS_PROPERTIES.put(fileName, loadProperties(propertiesPath));
	}

	/**
	 * 将配置添加到全局配置中
	 * 
	 * @param properties
	 *            需要添加的配置
	 */
	public static void put(Properties properties) {
		CONFIGS_PROPERTIES.putAll(properties);
	}

	/**
	 * 将指定配置载入到集合
	 * 
	 * @param propertiesPath
	 *            配置文件路径
	 */
	private static Properties loadProperties(String propertiesPath) {
		Properties properties = new Properties();
		InputStream iStream = null;
		try {
			String confDir = System.getProperty("conf.dir");
			if(StringUtils.isEmpty(confDir))confDir = System.getenv("conf.dir");
			if (!StringUtils.isEmpty(confDir)) {
				iStream = FileUtils.readStream(confDir + "/" + propertiesPath);
				logger.info("读取配置：" + confDir + "/" + propertiesPath + ";读取情况：" + iStream==null);
			}
			if (iStream == null) {
				iStream = SystemGlobal.class.getClassLoader()
						.getResourceAsStream(propertiesPath);
				logger.info("读取配置：" + propertiesPath + ";读取情况：" + iStream==null);
				if (iStream == null
						&& SystemProperties.getGlobalPropertiesPath().equals(
								propertiesPath)) {
					iStream = SystemGlobal.class.getResourceAsStream("/"
							+ propertiesPath);
					if (iStream == null) {
						iStream = ClassLoader
								.getSystemResourceAsStream(propertiesPath);
					}
				}
			}
			// if (SystemProperties.getGlobalPropertiesPath().equals(
			// propertiesPath)
			// && !StringUtils.isEmpty(confDir)) {
			// iStream = FileUtils.readStream(confDir + "/" + propertiesPath);
			// } else {
			// iStream = SystemGlobal.class.getClassLoader()
			// .getResourceAsStream(propertiesPath);
			// if (iStream == null
			// && SystemProperties.getGlobalPropertiesPath().equals(
			// propertiesPath)) {
			// iStream = SystemGlobal.class.getResourceAsStream("/"
			// + propertiesPath);
			// if (iStream == null) {
			// iStream = ClassLoader
			// .getSystemResourceAsStream(propertiesPath);
			// }
			// }
			// }
			if (iStream != null) {
				properties.load(iStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	/**
	 * 获取指定名称的配置属性
	 * 
	 * @param field
	 *            属性名
	 * @return 配置属性值
	 */
	public static String get(String field) {
		if (StringUtils.isEmpty(field)) {
			return "";
		}
		init();
		String preExpansion = CONFIGS_PROPERTIES.getProperty(field);
		if (StringUtils.isEmpty(preExpansion)) {
			return "";
		}
		return EXPANDER.expandVariables(preExpansion);
	}

	/**
	 * 获取指定名称的配置属性
	 * 
	 * @param fileName
	 *            文件名
	 * @param field
	 *            属性名
	 * @return 配置属性值
	 */
	public static String get(String field, String fileName) {
		if (StringUtils.isEmpty(field) || StringUtils.isEmpty(fileName)) {
			return "";
		}
		init();
		if (DIVIDE_CONFIGS_PROPERTIES == null
				|| !DIVIDE_CONFIGS_PROPERTIES.containsKey(fileName)) {
			return "";
		}
		Properties properties = DIVIDE_CONFIGS_PROPERTIES.get(fileName);

		String preExpansion = properties.getProperty(field);
		if (StringUtils.isEmpty(preExpansion)) {
			return "";
		}
		return EXPANDER.expandVariables(preExpansion);
	}

	/**
	 * 获取配置值，并填充占位符值
	 * 
	 * @param field
	 *            需要获取的配置名称
	 * @param val
	 *            需要填充到占位符的值
	 * @return 配置属性值
	 */
	public static String getFormat(String field, Object... val) {
		return String.format(get(field), val);
	}

	/**
	 * 获取系统配置信息
	 * 
	 * @param config
	 *            配置枚举设置
	 * @return 配置属性值
	 */
	public static String get(BaseConfig config) {
		return get(config.toString());
	}

	/**
	 * 获取系统配置信息
	 * 
	 * @param config
	 *            配置枚举设置
	 * @return 配置属性值
	 */
	public static Integer getInt(BaseConfig config) {
		String value = get(config.toString());
		if (value == null || "".equals(value)) {
			return null;
		}
		return Integer.valueOf(value);
	}

	/**
	 * 获取数字类型值
	 * 
	 * @param fileName
	 *            文件名
	 * @param field
	 *            属性名
	 * @return 配置属性值
	 */
	public static Integer getInteger(String field, String fileName) {
		int value = -1;
		String str = get(field, fileName);
		if (str != null && !"".equals(str)) {
			try {
				value = Integer.valueOf(str);
			} catch (Exception e) {
			}
		}
		return value;
	}

	/**
	 * 获取数字类型值
	 * 
	 * @param field
	 *            属性名
	 * @return 配置属性值
	 */
	public static Integer getInteger(String field) {
		int value = -1;
		String str = get(field);
		if (str != null && !"".equals(str)) {
			try {
				value = Integer.valueOf(str);
			} catch (Exception e) {
			}
		}
		return value;
	}

	/**
	 * 获取boolean类型值
	 * 
	 * @param fileName
	 *            文件名
	 * @param field
	 *            属性名
	 * @return 配置属性值
	 */
	public static boolean getBoolean(String field, String fileName) {
		boolean value = false;
		String str = get(field, fileName);
		if (!StringUtils.isEmpty(str)) {
			try {
				value = Boolean.valueOf(str);
			} catch (Exception e) {
			}
		}
		return value;
	}

	/**
	 * 获取boolean类型值
	 * 
	 * @param field
	 *            属性名
	 * @return 配置属性值
	 */
	public static boolean getBoolean(String field) {
		boolean value = false;
		String str = get(field);
		if (!StringUtils.isEmpty(str)) {
			try {
				value = Boolean.valueOf(str);
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static void putAll(Properties properties) {
		if (CONFIGS_PROPERTIES == null) {
			return;
		}
		synchronized (SystemGlobal.class) {
			CONFIGS_PROPERTIES.putAll(properties);
		}
	}

	/**
	 * 更新值
	 * 
	 * @param key
	 * @param val
	 */
	// public static void set(String key, Object val) {
	// if (StringUtils.isEmpty(key)) {
	// return;
	// }
	// CONFIGS_PROPERTIES.setProperty(key, val.toString());
	// }
}
