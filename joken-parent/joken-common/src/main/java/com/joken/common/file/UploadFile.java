/*
 * @(#)UploadService.java	2016-5-23 下午3:25:45
 *
 * Copyright 2016 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.common.file;

import java.io.InputStream;

/**
 * 
 * 文件上传接口类
 * 
 * @version V1.0.0, 2016-5-23
 * @author 欧阳增高
 * @since V1.0.0
 */
public interface UploadFile {

	/**
	 * 上传文件接口该当
	 * 
	 * @param filePath
	 *            路径相对路径
	 * @param stream
	 *            文件数据流
	 */
	void upload(String filePath, InputStream stream);
}
