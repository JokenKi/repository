package com.joken.ice.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.joken.common.model.AlarmModel;
import com.joken.common.rpc.SortModel;
import com.joken.common.utils.DateUtils;

/**
 * 
 * <p>
 * Title: TestUtils
 * </p>
 * <p>
 * Description: 工具类测试用例
 * </p>
 * 
 * @author wangby
 * @date 2016年4月20日 上午10:40:00
 */
public class TestUtils extends TestCase {

	/**
	 * 
	 * <p>
	 * Description: 测试日期正则表达式
	 * </p>
	 * 
	 * @author wangby
	 * @date 2016年4月20日 上午10:40:39
	 */
	public void dateTimeReg() {
		// (((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-((1[0-2]{1})|(0[1,9]{1}))-(([0-2]{1}[0-9]{1})|3[0-1]{1})
		// (([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))|(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-\\d{2}-\\d{2})|((([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))
		String timeString = "2016-06-12 10:09:01";
		// timeString = "2016-04-19";
		// timeString = "23:11:00";
		// System.out
		// .println(timeString
		// .matches("(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-((1[0-2]{1})|(0[1,9]{1}))-(([0-2]{1}[0-9]{1})|3[0-1]{1}) (([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))|(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-\\d{2}-\\d{2})|((([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))"));
		// ^(?:(31)(\\D)(0?[13578]|1[02])\\2|(29|30)

		Pattern pattern = Pattern
				.compile("(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-((1[0-2]{1})|(0[1-9]{1}))-(([0-2]{1}[0-9]{1})|3[0-1]{1}) (([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))|(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-\\d{2}-\\d{2})|((([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))");
		Matcher matcher = pattern.matcher(timeString);
		if (matcher.matches()) {
			Date date = DateUtils.dateString2Date(timeString);
			System.out.println(date);
		}

		System.out.println(DateUtils.DATE_TIME_CHECK_PATTERN.matcher(
				timeString.toString()).matches());
		System.out.println("03".matches("(1[0-2]{1})|(0[1-9]{1})"));
	}

	public void generateQueryParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(
				IceBeanUtils.QUERY_KEY.toString(),
				"between_times_start=1&between_times_end=10&gt_dicimal=10.26&gt_updateTime=2016-01-11 10:20:10&in_times=1&in_times=2&in_times=10&in_times=20");
		AlarmModel alarmModel = new AlarmModel();
		params.put("custId", "asc");
		params.put("delivery.deliveryId", "asc");
		params.put("items.itemId", "asc");
		params.put("orderDeliveryId", "asc");
		// params.put("gt_lastTime", "200000");
		Object generateQueryParams = IceBeanUtils.generateQueryParams(params,
				alarmModel);
		System.out.println(generateQueryParams);
	}

	public void testSort() {
		SortModel sm = new SortModel();
		Map<String, String> _sortMap = new HashMap<String, String>();
		sm.setSortMap(_sortMap);
		_sortMap.put("custId", "asc");
		_sortMap.put("delivery.deliveryId", "asc");
		_sortMap.put("items.itemId", "asc");
		_sortMap.put("orderDeliveryId", "asc");

		sm.setSortMap(_sortMap);
		String sortString = IceBeanUtils.getSortString(sm);
		System.out.println(sortString);
	}

}
