package com.joken.common.properties;

import com.alibaba.fastjson.JSONObject;
import com.joken.common.model.ResponseModel;
import com.joken.common.model.ResponseModel.SuccessStatus;
import com.joken.common.utils.StringUtils;

/**
 * 配置信息获取
 * 
 * @author inkcar
 * @version 0.0.0.1 下午04:50:21
 */
public class MsgProperties {
	/**
	 * 私有化配置,防止被实例化
	 */
	private MsgProperties() {
	}

	/**
	 * 获取指定配置项配置值
	 * 
	 * @param config
	 *            配置项实现对象
	 * @return 配置值
	 */
	private static String get(BaseConfig config) {
		return get(config.toString());
	}

	/**
	 * 获取指定键的配置值
	 * 
	 * @param key
	 *            配置键名
	 * @return 配置值
	 */
	private static String get(String key) {
		return SystemGlobal.get(key);
	}

	/**
	 * 访问的数据不存在
	 * 
	 * @return String
	 */
	public static ResponseModel getRequestNoData() {
		return getFailByCode("1103");
	}

	/**
	 * 请求验证未通过
	 * 
	 * @return String
	 */
	public static ResponseModel getRequestVerifyNopass() {
		return getFailByCode("1101");
	}

	/**
	 * 请求参数不能为空
	 * 
	 * @return String
	 */
	public static ResponseModel getRequestParamNotNull(Object... values) {
		return getFail(StringUtils.format(
				get(MsgConfig.MSG_ERROR_REQUEST_NULL), values));
	}

	/**
	 * JSON分页统计查询错误
	 * 
	 * @return String
	 */
	public static ResponseModel getLimitError() {
		return getFail(get(MsgConfig.ERROR_MYBATIS_LIMIT));
	}

	/**
	 * 参数不完整或参数取值不合法
	 * 
	 * @return String
	 */
	public static ResponseModel getFailRequestVerify() {
		return getFailByCode("1102");
	}

	/**
	 * 获取成功及失败提醒字符串
	 * 
	 * @param succ
	 *            成功数
	 * @param fail
	 *            失败数
	 * @return String
	 * @author 欧阳增高
	 * @date 2015-8-19 上午11:35:50
	 */
	public static String getSuccFailAmount(int succ, int fail) {
		return StringUtils.format(get(MsgConfig.MSG_SUCCESS_FAIL_AMOUNT), succ,
				fail);
	}

	/**
	 * 获取操作失败通用返回响应对象
	 * 
	 * @return String
	 */
	public static ResponseModel getFail() {
		return getFail(null, null);
	};

	/**
	 * 获取操作失败通用返回响应对象
	 * 
	 * @param status
	 *            错误码
	 * @return String
	 */
	public static ResponseModel getFailByCode(String status) {
		if (StringUtils.isEmpty(status)) {
			status = ResponseModel.FAIL.getStatus().toString();
		}
		ResponseModel model = new ResponseModel(SystemGlobal.get("MSG_FAILUER_"
				+ status));
		model.setStatus(Integer.valueOf(status));
		model.setSuccess(SuccessStatus.FAIL);
		return model;
	};

	/**
	 * 获取操作失败通用响应对象
	 * 
	 * @param status
	 *            错误码
	 * @param values
	 *            需要填充到配置点位符的数据
	 * @return ResponseModel
	 */
	public static ResponseModel getFailByFormat(String status, Object... values) {
		if (StringUtils.isEmpty(status)) {
			status = ResponseModel.FAIL.getStatus().toString();
		}
		String msg = StringUtils.format(
				SystemGlobal.get("MSG_FAILUER_" + status), values);
		ResponseModel model = new ResponseModel(msg);
		model.setStatus(Integer.valueOf(status));
		model.setSuccess(SuccessStatus.FAIL);
		return model;
	};

	/**
	 * 获取操作失败通用返回响应对象
	 * 
	 * @param msg
	 *            失败信息描述
	 * @return ResponseModel
	 */
	public static ResponseModel getFail(String msg) {
		return getFail(msg, null);
	};

	/**
	 * 获取操作失败通用返回响应对象
	 * 
	 * @param msg
	 *            失败信息描述
	 * @param data
	 *            需要返回的数据JSON对象
	 * @return ResponseModel
	 */
	public static ResponseModel getFail(String msg, Object data) {
		ResponseModel model = getFailResp(msg, data);
		return model;
	};

	/**
	 * 获取操作失败响应模型
	 * 
	 * @return ResponseModel
	 */
	public static ResponseModel getFailResp() {
		return ResponseModel.FAIL;
	};

	/**
	 * 获取操作失败响应模型
	 * 
	 * @param msg
	 *            失败信息描述
	 * @return ResponseModel
	 */
	public static ResponseModel getFailResp(String msg) {
		return getFailResp(msg, null);
	};

	/**
	 * 获取操作失败响应模型
	 * 
	 * @param msg
	 *            失败信息描述
	 * @param data
	 *            需要返回的数据JSON对象
	 * @return ResponseModel
	 */
	public static ResponseModel getFailResp(String msg, Object data) {
		if (StringUtils.isEmpty(msg)) {
			msg = get("MSG_FAILURE");
		}
		ResponseModel model = new ResponseModel(msg, data);
		model.setStatus(1000);
		model.setSuccess(SuccessStatus.FAIL);
		return model;
	};

	/**
	 * 获取操作成功通用响应对象
	 * 
	 * @param msg
	 *            操作成功描述信息
	 * @param data
	 *            需要返回的数据JSON对象
	 * @return String
	 */
	public static ResponseModel getSuccess(String msg, Object data) {
		return getSuccessResp(msg, data);
	};

	/**
	 * 获取操作成功响应模型
	 * 
	 * @param msg
	 *            操作成功描述信息
	 * @param data
	 *            需要返回的数据JSON对象
	 * @return ResponseModel
	 */
	public static ResponseModel getSuccessResp(String msg, Object data) {
		if (StringUtils.isEmpty(msg)) {
			msg = get("MSG_SUCCESS");
		}
		return new ResponseModel(msg, data);
	};

	/**
	 * 获取操作成功通用响应对象
	 * 
	 * @return ResponseModel
	 */
	public static ResponseModel getSuccess() {
		return getSuccess(null, null);
	};

	/**
	 * 获取操作成功通用响应对象
	 * 
	 * @param msg
	 *            操作成功描述信息
	 * @return String
	 */
	public static ResponseModel result(String msg) {
		return getSuccess(msg, null);
	};

	/**
	 * 获取操作成功响应模型
	 * 
	 * @return ResponseModel
	 */
	public static ResponseModel getSuccessResp() {
		return ResponseModel.SUCC;
	};

	/**
	 * 获取操作成功响应模型
	 * 
	 * @param msg
	 *            操作成功描述信息
	 * @return ResponseModel
	 */
	public static ResponseModel getSuccessResp(String msg) {
		return getSuccessResp(msg, null);
	};

	/**
	 * 获取操作成功通用响应对象
	 * 
	 * @param data
	 *            需要返回的数据JSON对象
	 * @return String
	 */
	public static ResponseModel getSuccess(Object data) {
		return getSuccess(null, data);
	}

	/**
	 * 获取操作成功响应模型
	 * 
	 * @param data
	 *            需要返回的数据JSON对象
	 * @return ResponseModel
	 */
	public static ResponseModel getSuccessResp(JSONObject data) {
		return getSuccessResp(null, data);
	}
}
