/**
 * 系统配置枚举类型
 */
package com.joken.common.properties;

import com.joken.common.properties.BaseConfig;
import com.joken.common.properties.SystemGlobal;

/**
 * 系统配置枚举类--<code>SystemConfig</code>
 * 
 * @author 欧阳增高
 */
public enum MsgConfig implements BaseConfig {

	/**
	 * 操作成功
	 */
	MSG_SUCCESS("MSG_SUCCESS"),
	/**
	 * 操作失败
	 */
	MSG_FAILURE("MSG_FAILURE"),
	/**
	 * 访问数据不存在
	 */
	MSG_ERROR_REQUEST_NODATA("MSG_ERROR_REQUEST_NODATA"),
	/**
	 * 请求参数加密验证
	 */
	MSG_ERROR_REQUEST_VERIFY("MSG_ERROR_REQUEST_VERIFY"),
	/**
	 * 请求参数验证不通过
	 */
	MSG_ERROR_REQUEST_NOPASS("MSG_ERROR_REQUEST_NOPASS"),
	/**
	 * 请求参数不能为空
	 */
	MSG_ERROR_REQUEST_NULL("MSG_ERROR_REQUEST_NULL"),

	/**
	 * 分页查询错误
	 */
	ERROR_MYBATIS_LIMIT("MSG_ERROR_MYBATIS_LIMIT"),
	/**
	 * 成功失败数量
	 */
	MSG_SUCCESS_FAIL_AMOUNT("MSG_SUCCESS_FAIL_AMOUNT");

	private final String value;

	/**
	 * 获取枚举值
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * 枚举构造
	 * 
	 * @param value
	 *            枚举值
	 */
	MsgConfig(String value) {
		this.value = value;
	}

	/**
	 * 获取配置值
	 * 
	 * @return String
	 */
	public String getValue() {
		return SystemGlobal.get(this.value);
	}
}
