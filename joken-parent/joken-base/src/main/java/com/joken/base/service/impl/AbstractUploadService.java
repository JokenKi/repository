/*
 * @(#)AbstractUploadService.java	2015-12-3 下午6:43:49
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.base.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.joken.base.service.UploadService;
import com.joken.common.exception.BaseException;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.model.RequestModel;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.properties.SystemProperties;
import com.joken.common.utils.DateUtils;
import com.joken.common.utils.FileUtils;

/**
 * 
 * 
 * @version V1.0.0, 2015-12-3
 * @author 欧阳增高
 * @since V1.0.0
 */
public abstract class AbstractUploadService implements UploadService {
	/**
	 * 日志记录
	 */
	private static Logger logger = LoggerFactory.getLogger("upload");

	/**
	 * 具体业务处理方法
	 * 
	 * @param files
	 *            上传后的文档对象
	 * @param model
	 *            请求参数模型
	 * @return ResponseModel
	 * @author 欧阳增高
	 * @throws BaseException 
	 * @date 2015-12-3 下午7:28:00
	 */
	protected abstract ResponseModel uploadHandle(List<File> files,
			RequestModel model) throws BaseException;

	/**
	 * 文件上传前动作
	 * 
	 * @param model
	 *            请求参数集合
	 * @return ResponseModel
	 * @author 欧阳增高
	 * @date 2015-12-10 下午4:36:00
	 */
	protected ResponseModel before(RequestModel model) {
		return MsgProperties.getSuccessResp();
	}

	@Override
	public ResponseModel upload(MultipartFile[] files, RequestModel model) throws BaseException {
		if (files == null || files.length == 0) {
			logger.info("空文档上传错误");
			return MsgProperties.getFailRequestVerify();
		}
		ResponseModel resp = this.before(model);
		if (!resp.isSuccess()) {
			return resp;
		}
		logger.info("文档上传开始");

		// 获取保存路径,有特殊用法需要子类实现
		String savePath = getSavePath(model);

		List<File> filePath = new ArrayList<File>(files.length);
		File tmpFile;
		for (MultipartFile file : files) {
			// 转存文件
			tmpFile = this.transferFile(file, savePath);
			if (tmpFile == null) {
				continue;
			}
			filePath.add(tmpFile);
		}
		logger.info("文档上传数量：" + filePath.size());

		return this.uploadHandle(filePath, model);
	}

	/**
	 * 上传文件转存
	 * 
	 * @param file
	 *            需要转存的文件
	 * @param savePath
	 *            保存路径
	 * @return String
	 */
	private File transferFile(MultipartFile partfile, String savePath) {
		try {
			String filePath = this.getFileName(savePath,
					partfile.getOriginalFilename());
			File file = new File(filePath);
			// 转存文件
			partfile.transferTo(file);

			return file;
		} catch (Exception e) {
			logger.error("文档转换错误", e);
		}
		return null;
	}

	/**
	 * 获取文档上传保存路径
	 * 
	 * @param model
	 *            参数模型
	 * @return String
	 * @author 欧阳增高
	 * @date 2015-12-3 下午7:19:19
	 */
	protected String getFileName(String savePath, String originalFilename) {
		// 获取文件扩展名
		String extensionName = FileUtils.getFileExtension(originalFilename);

		Calendar calendar = Calendar.getInstance();
		// 获取文件名
		String fileName = DateUtils.ORA_DATE_TIME_EXTENDED_FORMAT
				.format(calendar.getTime());

		// 组装文件
		StringBuffer filePath = new StringBuffer();
		filePath.append(savePath);
		filePath.append(fileName);
		filePath.append(".");
		filePath.append(extensionName);

		return filePath.toString();
	}

	/**
	 * 获取文档上传保存路径
	 * 
	 * @param model
	 *            参数模型
	 * @return String
	 * @author 欧阳增高
	 * @date 2015-12-3 下午7:19:19
	 */
	protected String getSavePath(RequestModel model) {
		/**
		 * 获取上传附件目录
		 */
		StringBuffer uploadPath = new StringBuffer();
		uploadPath.append(SystemProperties.getUploadDir());
		// 获取随机目录
		uploadPath.append(FileUtils.getRandsFolder(false));

		// 获取项目业务相关文档目录
		StringBuffer savePath = new StringBuffer();
		savePath.append(SystemProperties.getProjectPath());
		savePath.append(uploadPath);

		// 创建文件保存目录
		FileUtils.createFileDir(savePath.toString());

		return savePath.toString();
	}

}
