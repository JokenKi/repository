/*
 * @(#)ValidateService.java	2015-8-31 上午9:20:49
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.api;

/**
 * 
 * 通用数据验证接口
 * 
 * @version V1.0.0, 2015-8-31
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface ValidateService {

	/**
	 * 通用验证接口方法
	 * 
	 * @param validData
	 *            需要验证的数据,以json字符串格式传入
	 * @return json格式验证结果,null值视为验证成功
	 *         <p>
	 *         {"success":true|false,"msg":"验证结果描述"}
	 *         <ul>
	 *         <li>success:验证结果,true为验证成功,反之失败</li>
	 *         <li>msg:验证结果描述</li>
	 *         </ul>
	 *         </p>
	 * @author 欧阳增高
	 * @date 2015-8-31 上午9:22:10
	 */
	Object validate(String validData);
}
