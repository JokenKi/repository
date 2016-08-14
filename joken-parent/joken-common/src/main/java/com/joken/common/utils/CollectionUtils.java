/**
 * @(#)CollectionUtils.java	V1.0.0 2015-8-13 下午5:02:10
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.joken.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集合操作工具类
 * 
 * @version V1.0.0, 2015-8-13 下午5:02:10
 * @author 欧阳增高
 * @since V1.0.0
 */
public class CollectionUtils {
	/**
	 * 构造
	 */
	private CollectionUtils() {
	}

	/**
	 * 创建一个带初始记录的LIST对象
	 * 
	 * @param objs
	 *            需要添加到List中的对象值
	 * @return List<Object>
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> createList(T... objs) {
		List<T> list = new ArrayList<T>();
		if (objs != null) {
			for (T t : objs) {
				list.add(t);
			}

		}
		return list;
	}

	/**
	 * 给list排序
	 * 
	 * @param list
	 *            需要排序的list对象
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Collections.sort(list);
	}

	// public static void main(String[] args) {
	// System.out.println(CollectionUtils.createList("test", "aaa",
	// ResponseModel.FAIL));
	// }
}
