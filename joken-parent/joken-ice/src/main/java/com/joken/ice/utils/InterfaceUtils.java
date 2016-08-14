package com.joken.ice.utils;

import com.joken.common.rpc.NullValueException;
import com.joken.common.rpc.Page;
import com.joken.common.rpc.PageHolder;

/**
 * ice远程调用接口工具类
 *
 */
public class InterfaceUtils {

	private InterfaceUtils() {
	}

	/**
	 * 检查主键id是否合法
	 * 
	 * @param id
	 *            主键id,默认长度36
	 * @return 长度判断后的主键id
	 * @throws NullValueException
	 */
	public static String checkId(String id) throws NullValueException {
		if (id == null || id.length() != 36) {
			throw new NullValueException("请求失败", "参数不合法");
		}
		return id;
	}

	/**
	 * 通用处理远程调用page对象
	 * 
	 * @param page
	 *            客户端调用的page对象
	 * @param pageOut
	 *            远程调用结束后返回给客户端的 pageHolder对象
	 * @param soaPage
	 *            数据库查询执行结束后返回的soaPage对象
	 */
	public static void dealPageHolder(Page page, PageHolder pageOut,
			com.joken.common.model.Page soaPage) {

		if (page == null || pageOut == null || soaPage == null) {
			return;
		}
		pageOut.value = page;
		pageOut.value.setTotalNum(soaPage.getTotalRecord());
		pageOut.value.setPageNum(soaPage.getPageNo());
		pageOut.value.setPageSize(soaPage.getPageSize());
		pageOut.value.setPageTotal(soaPage.getTotalPage());
	}

}
