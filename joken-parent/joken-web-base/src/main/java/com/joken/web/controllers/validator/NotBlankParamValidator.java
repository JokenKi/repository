/**
 * 
 */
package com.joken.web.controllers.validator;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.ParamValidator;
import net.paoding.rose.web.paramresolver.ParamMetaData;

import org.springframework.validation.Errors;

import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.StringUtils;

/**
 * null参数验证实现类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class NotBlankParamValidator implements ParamValidator {

	@Override
	public boolean supports(ParamMetaData metaData) {
		return metaData.getAnnotation(NotBlank.class) != null;
	}

	@Override
	public Object validate(ParamMetaData metaData, Invocation inv,
			Object target, Errors errors) {
		String paramName = metaData.getParamName();
		if (target == null) {
			return "@json:" + MsgProperties.getRequestParamNotNull(paramName);
		}
		String value = inv.getParameter(paramName);
		if (StringUtils.isEmpty(value)) {
			return "@json:" + MsgProperties.getRequestParamNotNull(paramName);
		}
		return null;
	}

}
