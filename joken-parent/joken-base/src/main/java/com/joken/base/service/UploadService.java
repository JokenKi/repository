/**
 * 
 */
package com.joken.base.service;

import org.springframework.web.multipart.MultipartFile;

import com.joken.common.exception.BaseException;
import com.joken.common.model.RequestModel;
import com.joken.common.model.ResponseModel;

/**
 * 文件上传接口
 * 
 * @author 欧阳增高
 * 
 */
public interface UploadService {

	/**
	 * 附件上传业务实现方法
	 * 
	 * @param files
	 *            上传文件数组
	 * @param model
	 *            参数模型
	 * @return String
	 * @throws BaseException 
	 */
	ResponseModel upload(MultipartFile[] files, RequestModel model) throws BaseException;

}
