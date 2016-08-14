package com.joken.web.controllers.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.joken.base.service.BaseService;
import com.joken.base.spring.SpringContextUtil;
import com.joken.common.excel.ExcelExpUtil;
import com.joken.common.model.BaseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.StringUtils;
import com.joken.web.controllers.BaseController;

/**
 * Excel导入控制器类
 * 
 * @author 杨艳芳
 * @since 1.0
 */
@Path("")
public class ExcelController extends BaseController {
	/**
	 * 根据服务名及对应数据mapper配置导入Excel服务接口
	 * 
	 * @param name
	 *            服务名
	 * @param mapperName
	 *            数据mapper名称
	 * @return 响应数据
	 * @author 欧阳增高
	 * @date 2016-3-15 上午10:42:13
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Get("{name:[a-zA-Z]{1}[a-zA-Z0-9]+}")
	public String exportExcel(@Param("name") String name,
			@Param("mapperName") String mapperName) {

		StringBuffer path = new StringBuffer(
				SystemGlobal.get("excel.default.path"));
		/*
		 * if (StringUtils.isEmpty(path)) {
		 * 
		 * path.append("d://template/excel/template/"); }
		 */
		// path.append("\\");
		path.append(name);
		BaseService<?> service = (BaseService<?>) SpringContextUtil
				.getBean(name + "Service");

		Object data = null;
		if (StringUtils.isEmpty(mapperName)) {
			data = service.selectList(this.getRequestMap().getRequest());
		} else {
			try {
				Class clazz = service.getClass();
				Method method = clazz.getMethod(mapperName, Map.class);
				if (method == null) {
					return this.getJsonWrite(MsgProperties.getFail());
				} else {
					data = method.invoke(service, this.getRequestMap()
							.getRequest());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 通过模板的名称，模板的数据，模板的位置，拿到一个填充好的工作簿对象
		// 通过一个目录和工作簿创建一个excel文件
		File file = ExcelExpUtil.excelExp(
				StringUtils.getValue(mapperName, name), data, path.toString());

		// 转换文件名的编码格式
		String filename = null;
		try {
			filename = URLEncoder.encode(file.getName(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 文件下载
		inv.getResponse().reset();
		inv.getResponse().setContentType("application/x-msdownload");
		inv.getResponse().addHeader("Content-Disposition",
				"attachment; filename=\"" + filename + "\"");
		inv.getResponse().setContentLength((int) file.length());
		/* 如果文件长度大于0 */
		ServletOutputStream servletOS = null;
		try {
			servletOS = inv.getResponse().getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if ((int) file.length() != 0) {
			try {
				/* 创建输入流 */
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];
				/* 创建输出流 */
				int readLength;
				while (((readLength = inStream.read(buf)) != -1)) {
					servletOS.write(buf, 0, readLength);
				}
				inStream.close();
				servletOS.flush();
				servletOS.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file.delete();
		return null;
	}

	@Override
	public BaseService<? extends BaseModel> getService() {
		return null;
	}
}