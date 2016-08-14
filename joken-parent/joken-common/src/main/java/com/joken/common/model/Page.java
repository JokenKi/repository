package com.joken.common.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页模型类
 * 
 */
public class Page {
	/**
	 * 默认每页数
	 */
	public static String DEFAULT_PAGESIZE = "10";
	/**
	 * 请求查询键名
	 */
	public static String QUERY_REQUEST_KEY = "__page__";
	/**
	 * 当前页面
	 */
	private int pageNo;
	/**
	 * 每页行数
	 */
	private int pageSize;
	/**
	 * 总记录数
	 */
	private int totalRecord;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 开始位置
	 */
	private int start;

	/**
	 * 查询条件
	 */
	private Map<String, String> params;
	/**
	 * 数组查询条件
	 */
	private Map<String, List<String>> paramLists;
	/**
	 * url 地址
	 */
	private String searchURL;
	/**
	 * 可以显示的页号 ("|"号分割，总页数变更时更新)
	 */
	private String pageNoDisp;

	/**
	 * 构造
	 */
	private Page() {
		pageNo = 1;
		pageSize = Integer.valueOf(DEFAULT_PAGESIZE);
		totalRecord = 0;
		totalPage = 0;
		params = new HashMap<String, String>();
		paramLists = new HashMap<String, List<String>>();
		searchURL = "";
		pageNoDisp = "";
	}

	/**
	 * 静态构造者构造
	 * 
	 * @param pageNo
	 *            当前页面
	 * @param pageSize
	 *            每页行数
	 * @return Page
	 */
	public static Page newBuilder(int pageNo, int pageSize) {
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setStart((pageNo - 1) * pageSize);
		return page;
	}

	/**
	 * 总记录数改变时，重新计算总页数
	 */
	private void refeshPage() {
		totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize
				: (totalRecord / pageSize + 1);
		if (pageNo > totalPage && totalPage != 0) {
			pageNo = totalPage;
		}
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * @param totalRecord
	 *            the totalRecord to set
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		this.refeshPage();
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the paramLists
	 */
	public Map<String, List<String>> getParamLists() {
		return paramLists;
	}

	/**
	 * @param paramLists
	 *            the paramLists to set
	 */
	public void setParamLists(Map<String, List<String>> paramLists) {
		this.paramLists = paramLists;
	}

	/**
	 * @return the searchURL
	 */
	public String getSearchURL() {
		return searchURL;
	}

	/**
	 * @param searchURL
	 *            the searchURL to set
	 */
	public void setSearchURL(String searchURL) {
		this.searchURL = searchURL;
	}

	/**
	 * @return the pageNoDisp
	 */
	public String getPageNoDisp() {
		return pageNoDisp;
	}

	/**
	 * @param pageNoDisp
	 *            the pageNoDisp to set
	 */
	public void setPageNoDisp(String pageNoDisp) {
		this.pageNoDisp = pageNoDisp;
	}

}
