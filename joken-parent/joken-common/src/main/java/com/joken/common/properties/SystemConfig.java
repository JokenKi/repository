/**
 * 系统配置枚举类型
 */
package com.joken.common.properties;

/**
 * 系统配置枚举类--<code>SystemConfig</code>
 * 
 * @author 欧阳增高
 */
public enum SystemConfig implements BaseConfig {
	/**
	 * 系统配置名称
	 */
	SYSTEM_GLOBAL_PROPERTIES_PATH("system.properties"),
	/**
	 * 系统其他properties配置文件路径
	 */
	SYSTEM_PROPERTIES_GROUP("system.properties.group"),

	/**
	 * 系统其它按文件名来存储不同的配置信息的properties配置文件路径
	 */
	SYTEM_PROPERTIES_DIVIDE("system.properties.divide"),

	/**
	 * 项目相关文件路径
	 */
	SYSTEM_PROJECT_FILES_PATH("system.project.path"),

	/**
	 * 系统附件上传路径
	 */
	SYSTEM_UPLOAD_DIR("system.upload.dir"),

	/**
	 * 系统测试状态
	 */
	SYSTEM_DEBUG("system.debug"),

	/**
	 * freemarker标签占位符
	 */
	SYSTEM_PROPERTIES_FREEMARKER_REGX("system.properties.freemarker.regx"),

	/**
	 * 系统编码设置
	 */
	SYSTEM_ENCODING("system.encoding"),
	/**
	 * 系统默认登录路径
	 */
	SYSTEM_LOGIN_PATH("system.login.path"),
	/**
	 * 时间戳有效时长
	 */
	SYSTEM_TIMESTAMP_INVALID("system.timestamp.invalid"),
	/**
	 * 默认分页数
	 */
	QUERY_LIMIT("system.query.limit"),
	/**
	 * 是否启用disconfig
	 */
	SYSTEM_CONFIG_DISCONFIG("system.config.disconfig");

	private final String value;

	/**
	 * 获取枚举值
	 * 
	 * @return 枚举字符值
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * 系统配置枚举构造
	 * 
	 * @param value
	 *            枚举值
	 */
	SystemConfig(String value) {
		this.value = value;
	}
}
