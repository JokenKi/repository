package com.joken.web.controllers;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.joken.base.service.BaseService;
import com.joken.base.service.UploadService;
import com.joken.base.spring.SpringContextUtil;
import com.joken.common.exception.BaseException;
import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.StringUtils;
import com.joken.web.controllers.required.LoginRequired;
import com.joken.web.controllers.validator.NotBlank;

/**
 * 文件上传视图控制类 此类由IKForm平台自定义业务对象生成
 * 
 * @author Inkcar<inkcaridge@qq.com>
 * @since 1.0
 */
@Path("upload")
public class UploadController extends BaseController {

	/**
	 * 自定义上传业务bean定义后缀,防止用户猜bean定义随意调用
	 */
	private static String CUSTOM_BEAN_SUFFIX = "UploadService";

	/**
	 * rose上下文
	 */
	@Autowired
	protected Invocation inv;

	/**
	 * 注入文件上传业务操作类
	 */
	@Resource(name = "UploadService")
	private UploadService service = null;

	/**
	 * 设置业务实现类
	 * 
	 * @param service
	 *            the service to set
	 */
	public void setService(UploadService service) {
		this.service = service;
	}

	/**
	 * 以默认上传处理类上传文件
	 * 
	 * @param files
	 *            需要上传的文件集合
	 * @return 业务处理结果
	 * @author 欧阳增高
	 * @date 2015-12-3 下午6:26:52
	 */
	@LoginRequired
	@Post
	public String upload(MultipartFile[] files) {
		if (service == null) {
			return this.getJsonWrite(MsgProperties.getRequestNoData());
		}
		try {
			return this
					.getJsonWrite(service.upload(files, this.getRequestMap()));
		} catch (BaseException e) {
			e.printStackTrace();
			return this.getJsonWrite(MsgProperties.getFail(e.getMessage()));
		}
	}

	/**
	 * 以指定上传业务类上传文档
	 * 
	 * @param files
	 *            需要上传的文件集合
	 * @param serviceName
	 *            业务处理类bean配置名称
	 * @return 业务处理结果
	 * @author 欧阳增高
	 * @date 2015-12-3 下午6:28:08
	 */
	@Post("{serviceName:[a-zA-Z]{1}[a-zA-Z0-9]+}")
	public String uploadByService(MultipartFile[] files,
			@Param("serviceName") @NotBlank String serviceName) {
		if (files == null || files.length == 0
				|| StringUtils.isEmpty(serviceName)) {
			return this.getJsonWrite(MsgProperties.getFailRequestVerify());
		}

		UploadService fileService = (UploadService) SpringContextUtil
				.getBean(serviceName + CUSTOM_BEAN_SUFFIX);
		if (service == null) {
			return this.getJsonWrite(MsgProperties.getFailRequestVerify());
		}
		try {
			return this.getJsonWrite(fileService.upload(files,
					this.getRequestMap()));
		} catch (BaseException e) {
			e.printStackTrace();
			return this.getJsonWrite(MsgProperties.getFail(e.getMessage()));
		}
	}

	@Override
	protected BaseService<?> getService() {
		return null;
	}

}
