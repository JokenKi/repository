package com.joken.common.properties;

/**
 * 常用系统配置数据获取工具类
 * 
 * @version V1.0.0, 2016-3-14
 * @author 欧阳增高
 * @since V1.0.0
 */
public class SystemProperties {

	/**
	 * 私有化配置,防止被实例化
	 */
	private SystemProperties() {
	}

	/**
	 * 获取系统测试运行状态
	 * 
	 * @return boolean
	 */
	public static boolean isDubug() {
		return SystemGlobal.getBoolean(SystemConfig.SYSTEM_DEBUG.toString());
	}

	/**
	 * 获取系统配置路径
	 * 
	 * @return 全局配置路径
	 */
	public static String getGlobalPropertiesPath() {
		return SystemConfig.SYSTEM_GLOBAL_PROPERTIES_PATH.toString();
	}

	/**
	 * 获取用户配置文件集
	 * 
	 * @return 用户配置文件集合路径
	 */
	public static String getGroupPropertiesPath() {
		return get(SystemConfig.SYSTEM_PROPERTIES_GROUP);
	}

	/**
	 * 获取用户按文件名来存储不同的配置信息的配置文件集
	 * 
	 * @return 用户配置文件集合路径
	 */
	public static String getDividePropertiesPath() {
		return get(SystemConfig.SYTEM_PROPERTIES_DIVIDE);
	}

	/**
	 * 获取发展配置
	 * 
	 * @param config
	 *            配置枚举
	 * @return 配置属性值
	 */
	private static String get(BaseConfig config) {
		return SystemGlobal.get(config);
	}

	/**
	 * 得到系统编码值
	 * 
	 * @return 编码值
	 */
	public static String getEncoding() {
		return get(SystemConfig.SYSTEM_ENCODING);
	}

	/**
	 * 获取系统相关文件目录的父级目录,如模板、日志等目录的父级目录
	 * 
	 * @return String
	 */
	public static String getProjectPath() {
		return get(SystemConfig.SYSTEM_PROJECT_FILES_PATH);
	}

	/**
	 * 获取系统附件上传目录
	 * 
	 * @return String
	 */
	public static String getUploadDir() {
		return get(SystemConfig.SYSTEM_UPLOAD_DIR);
	}

	/**
	 * 获取配置文件中freemarker占位正则格式
	 * 
	 * @return String
	 */
	public static String getFreemarkerRegx() {
		return get(SystemConfig.SYSTEM_PROPERTIES_FREEMARKER_REGX);
	}

	/**
	 * 获取配置文件列表每页默认记录数
	 * 
	 * @return String
	 */
	public static int getDefaultQueryLimit() {
		return SystemGlobal.getInt(SystemConfig.QUERY_LIMIT);
	}

	public static int getSystemConfig() {
		return SystemGlobal.getInt(SystemConfig.SYSTEM_CONFIG_DISCONFIG);
	}

	/**
	 * 获取配置文件中的登录页面路径，非WEB项目忽略
	 * 
	 * @return String
	 */
	public static String getDefaultLoginPath() {
		return get(SystemConfig.SYSTEM_LOGIN_PATH);
	}

	/**
	 * 获取请求时间戳失效时长
	 * 
	 * @return int
	 */
	public static int getTimestampInvalid() {
		return SystemGlobal.getInt(SystemConfig.SYSTEM_TIMESTAMP_INVALID);
	}
}
