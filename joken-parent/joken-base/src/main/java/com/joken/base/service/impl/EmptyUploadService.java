/**
 * 
 */
package com.joken.base.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joken.base.service.UploadService;
import com.joken.common.model.RequestModel;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;

/**
 * 空的文件上传实现，无业务逻辑
 * 
 * @author 欧阳增高
 * 
 */
@Service("UploadService")
public class EmptyUploadService implements UploadService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.ronguan.base.service.UploadService#upload(org.springframework.web.
	 * multipart.MultipartFile[], cn.ronguan.common.model.RequestModel)
	 */
	@Override
	public ResponseModel upload(MultipartFile[] files, RequestModel model) {
		return MsgProperties.getSuccessResp();
	}

}
