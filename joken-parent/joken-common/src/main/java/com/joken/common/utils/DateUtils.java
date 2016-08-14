package com.joken.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.joken.common.properties.SystemGlobal;

public class DateUtils {
	/**
	 * 
	 * <p>
	 * Description:日期字符串长度 2016-12-31 15:11:00 || 2016-04-19 || 23:11:00
	 * </p>
	 * 
	 * @author wangby
	 * @date 2016年4月20日 上午11:20:04
	 */
	public enum DateStringLength {
		DATE(10), TIME(8), DATETIME(19);
		private int length;

		private DateStringLength(int length) {
			this.length = length;
		}

		public int val() {
			return this.length;
		}
	}

	/**
	 * 检查日期的合法性 如{2016-12-31 15:11:00 || 2016-04-19 || 23:11:00} 合法
	 */
	public final static Pattern DATE_TIME_CHECK_PATTERN = Pattern
			.compile("(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-((1[0-2]{1})|(0[1-9]{1}))-(([0-2]{1}[0-9]{1})|3[0-1]{1}) (([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))|(((19[7-9]{1}[0-9]{1})|([2-9]{1}\\d{3}))-\\d{2}-\\d{2})|((([0-1]{1}[0-9]{1})|(2[0-3]{1})):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1}))");

	/**
	 * 标准日期格式 MM/dd/yyyy
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy");

	/** 以线程的方式创建SimpleDateFormat("MM/dd/yyyy") */
	public static ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM/dd/yyyy");
		}
	};

	/**
	 * 标准时间格式 MM/dd/yyyy HH:mm
	 */
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm");

	/** 以线程的方式创建SimpleDateFormat("MM/dd/yyyy HH:mm") */
	public static ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM/dd/yyyy HH:mm");
		}
	};

	/**
	 * 带时分秒的标准时间格式 MM/dd/yyyy HH:mm:ss
	 */
	public static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm:ss");

	/** 以线程的方式创建SimpleDateFormat("MM/dd/yyyy HH:mm:ss") */
	public static ThreadLocal<SimpleDateFormat> DATE_TIME_EXTENDED_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		}
	};

	/**
	 * ORA标准日期格式 yyyyMMdd
	 */
	public static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd");

	/** 以线程的方式创建SimpleDateFormat("yyyyMMdd") */
	public static ThreadLocal<SimpleDateFormat> ORA_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd");
		}
	};

	/**
	 * ORA标准时间格式 yyyyMMddHHmm
	 */
	public static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmm");

	/** 以线程的方式创建SimpleDateFormat("yyyyMMddHHmm") */
	public static ThreadLocal<SimpleDateFormat> ORA_DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmm");
		}
	};

	/**
	 * 带时分秒的ORA标准时间格式 yyyyMMddHHmmss
	 */
	public static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/** 以线程的方式创建SimpleDateFormat("yyyyMMddHHmmss") */
	public static ThreadLocal<SimpleDateFormat> ORA_DATE_TIME_EXTENDED_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};

	public static final SimpleDateFormat ORA_DATE_NOW_EXTENDED_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

	/** 以线程的方式创建SimpleDateFormat("yyyyMMddHHmmssSSS") */
	public static ThreadLocal<SimpleDateFormat> ORA_DATE_NOW_EXTENDED_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmssSSS");
		}
	};

	/**
	 * yyyy-MM-dd
	 */
	public static final SimpleDateFormat CHN_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/** 以线程的方式创建SimpleDateFormat("yyyy-MM-dd") */
	public static ThreadLocal<SimpleDateFormat> CHN_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * yyyy年MM月dd日
	 */
	public static final SimpleDateFormat CN_CHN_DATE_FORMAT = new SimpleDateFormat(
			"yyyy年MM月dd日");

	/** 以线程的方式创建SimpleDateFormat("yyyy年MM月dd日") */
	public static ThreadLocal<SimpleDateFormat> CN_CHN_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy年MM月dd日");
		}
	};

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final SimpleDateFormat CHN_DATE_TIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	/** 以线程的方式创建SimpleDateFormat("yyyy-MM-dd HH:mm") */
	public static ThreadLocal<SimpleDateFormat> CHN_DATE_TIME_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
	};

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat CHN_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/** 以线程的方式创建SimpleDateFormat("yyyy-MM-dd HH:mm:ss") */
	public static ThreadLocal<SimpleDateFormat> CHN_DATE_TIME_EXTENDED_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * HH:mm:ss
	 */
	public static final SimpleDateFormat CHN_DATE_TIME_SHORT_EXTENDED_FORMAT = new SimpleDateFormat(
			"HH:mm:ss");

	/** 以线程的方式创建SimpleDateFormat("HH:mm:ss") */
	public static ThreadLocal<SimpleDateFormat> CHN_DATE_TIME_SHORT_EXTENDED_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};

	/**
	 * HH:mm
	 */
	public static final SimpleDateFormat CHN_DATE_TIME_SHORTER_EXTENDED_FORMAT = new SimpleDateFormat(
			"HH:mm");

	/** 以线程的方式创建SimpleDateFormat("HH:mm") */
	public static ThreadLocal<SimpleDateFormat> CHN_DATE_TIME_SHORTER_EXTENDED_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm");
		}
	};

	/**
	 * 获取指定月份的季度
	 * 
	 * @param month
	 *            月份
	 * @return 季度
	 */
	public int getSeason(int month) {
		int season = 1;
		switch (month) {
		case 1:
		case 2:
		case 3:
			season = 1;
			break;
		case 4:
		case 5:
		case 6:
			season = 2;
			break;
		case 7:
		case 8:
		case 9:
			season = 3;
			break;
		case 10:
		case 11:
		case 12:
			season = 4;
			break;
		}
		return season;
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 *            年
	 * @return boolean
	 */
	public boolean isLeapYear(int year) {
		if ((year % 400) == 0) {
			return true;
		} else if ((year % 4) == 0) {
			if ((year % 100) == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 把时间字符串转换格式
	 * 
	 * @author Hanzibin
	 * @Time 11:25:53 AM ,Dec 22, 2015
	 * @param dateStr
	 *            字符串格式时间
	 * @param sdfOld
	 *            对应时间格式处理类
	 * @param sdfNew
	 *            转换格式处理类
	 * @return String
	 */
	public static String fmtDateStrFromOld2New(String dateStr,
			SimpleDateFormat sdfOld, SimpleDateFormat sdfNew) {
		try {
			Date date = sdfOld.parse(dateStr);
			return sdfNew.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***************************************************************************
	 * Java中的时间操作不外乎这四种情况： 1、获取当前时间 2、获取某个时间的某种格式 3、设置时间 4、时间的运算
	 * 
	 * Java计算时间依靠1970年1月1日开始的毫秒数.
	 * 
	 * Calender类本身是个抽象类，有部分方法需要是抽象的，所以本身不能通过new的方法来实例化，
	 * 需要借助于一些已经把抽象方法实现的子类来实例化，实例化方法都写在getInstance()方法里
	 * 当对抽象方法的普遍实现(使用getInstance()来实例化)不能满足你的需求的时候，你就应该自己来实现它。比如： public class
	 * CalenderEx extends Calender { } Calender cal = new CalenderEx();
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	/**
	 * 将指定时间格式字符串转为日期时间对象
	 * 
	 * @param date
	 *            日期时间字符串
	 * @return Date
	 */
	public static Date getDate(String date) {
		if (date == null || date.equals("")) {
			return null;
		}
		Date dte = null;
		boolean chn = true;
		if (date.indexOf("/") != -1) {
			chn = false;
		}

		if (date.length() > 10) {
			date = date.substring(0, 10);
		}
		try {
			if (chn) {
				dte = CHN_DATE_FORMAT_THREAD_LOCAL.get().parse(date);
			} else {
				dte = DATE_FORMAT_THREAD_LOCAL.get().parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dte;
	}

	/**
	 * 
	 * <p>
	 * Description: 将日期字符串转换为指定格式的日期对象
	 * </p>
	 * 
	 * @param dateString
	 *            日期字符串
	 * @param format
	 *            日期格式 yyyy-MM-dd HH:mm:ss
	 * @return 日期对象
	 * @author wangby
	 * @date 2016年4月20日 上午11:42:27
	 */
	public static Date dateString2Date(String dateString) {
		Date date = null;
		SimpleDateFormat format = null;
		try {
			switch (dateString.length()) {
			case 19:
				format = CHN_DATE_TIME_EXTENDED_FORMAT;
				break;
			case 16:
				format = CHN_DATE_TIME_FORMAT;
				break;
			case 10:
				format = CHN_DATE_FORMAT;
				break;
			case 8:
				format = CHN_DATE_TIME_SHORT_EXTENDED_FORMAT;
				break;
			case 5:
				format = CHN_DATE_TIME_SHORTER_EXTENDED_FORMAT;
				break;
			default:
				break;
			}
			if (format == null) {
				return null;
			}
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @return 带时分秒的标准时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return CHN_DATE_TIME_EXTENDED_FORMAT_THREAD_LOCAL.get().format(
				new Date());
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @return 带时分秒的标准时间格式 yyyyMMdd
	 */
	public static String getCurrentDate() {
		return getCurrentDate(new Date());
	}

	/**
	 * 获取指定日期时间的 yyyyMMdd格式值
	 * 
	 * @param dte
	 *            需要获取的日期对象
	 * @return yyyyMMdd
	 */
	public static String getCurrentDate(Date dte) {
		return ORA_DATE_FORMAT_THREAD_LOCAL.get().format(dte);
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @return 带时分秒的ORA标准时间格式 yyyyMMddHHmmss
	 */
	public static String getORADateTime() {
		return ORA_DATE_TIME_EXTENDED_FORMAT_THREAD_LOCAL.get().format(
				new Date());
	}

	/**
	 * 获取当前日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getCHNDate() {
		return getCHNDate(new Date());
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @param dte
	 *            需要检查的日期,null为当前时间
	 * @return yyyy-MM-dd
	 */
	public static String getCHNDate(Date dte) {
		if (dte == null) {
			dte = new Date();
		}
		return CHN_DATE_FORMAT_THREAD_LOCAL.get().format(dte);
	}
	
	/**
	 * 获取当前日期
	 * 
	 * @param dte
	 *            需要检查的日期,null为当前时间
	 * @return yyyy-MM-dd
	 */
	public static Date getCurrentDateCHN() {
		SimpleDateFormat sdf = CHN_DATE_FORMAT_THREAD_LOCAL.get();
		try {
			return sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 获取当前天对应星期日
	 * 
	 * @return 对应星期
	 */
	public static int getWeekDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 判断指定日志是否为非工作日
	 * 
	 * @param dte
	 *            需要检查的日期,null为当前时间
	 * @return boolean
	 */
	public static boolean isNonWeekDay(Date dte) {
		Calendar cal = Calendar.getInstance();
		if (dte != null)
			cal.setTime(dte);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String workDays = SystemGlobal.get("system.day.nonworkdays");
		// 1为星期天，7为星期六
		if (workDays == null || "".equals(workDays)) {
			workDays = "1,7";
		}

		// 等于-1时，表示不为周末
		if (workDays.indexOf(day + "") == -1) {
			return false;
		}

		// 获取并检查周末是否为调休上班日
		String working = SystemGlobal.get("system.day.working");
		if (working != null && !"".equals(working)) {
			String curr = ORA_DATE_FORMAT_THREAD_LOCAL.get()
					.format(cal.getTime()).substring(4);

			// 不为-1时，周末调休上班
			if (working.indexOf(curr) != -1) {
				return false;
			}
		}
		return true;

	}

	/**
	 * 判断传入日期是否为配置中的节假日
	 * 
	 * @param dte
	 *            需要检查的日期,null为当前时间
	 * @return boolean
	 */
	public static boolean isHoliday(Date dte) {
		String holiday = SystemGlobal.get("system.day.holidays");
		if (dte == null) {
			dte = Calendar.getInstance().getTime();
		}
		String curr = ORA_DATE_FORMAT_THREAD_LOCAL.get().format(dte)
				.substring(4);
		if (holiday.indexOf(curr) != -1) {
			return true;
		}
		// 获取特殊的假期，如农历假期、5.1周末调整假期等
		holiday = SystemGlobal.get("system.day.special.holiday");
		if (holiday.indexOf(curr) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化对象
	 * 
	 * @param date
	 *            日期对象
	 * @return yyyy-MM-dd HH:mm:ss
	 * @author 波洋
	 */
	public static String formateDate(Date date) {
		return CHN_DATE_TIME_EXTENDED_FORMAT_THREAD_LOCAL.get().format(date);
	}

	/**
	 * 获取两时间之间相差的秒数
	 * 
	 * @param endDate
	 *            结束时间
	 * @return Long
	 * @author 欧阳增高
	 * @date 2015-11-2 下午7:07:07
	 */
	public static Long getIntervalSecond(Date endDate) {
		return getIntervalSecond(Calendar.getInstance().getTime(), endDate);
	}

	/**
	 * 获取两时间之间相差的秒数
	 * 
	 * @param startDate
	 *            开始时间,默认为当前时间
	 * @param endDate
	 *            结束时间
	 * @return Long
	 * @author 欧阳增高
	 * @date 2015-11-2 下午7:07:07
	 */
	public static Long getIntervalSecond(Date startDate, Date endDate) {
		return (endDate.getTime() - startDate.getTime()) / 1000;
	}

	/**
	 * 一天中的时段，每15分钟一段
	 * 
	 * @param section
	 *            分段大小
	 * @param date
	 *            时间
	 * @return double
	 * @author 欧阳增高
	 * @date 2016-1-14 下午3:42:56
	 */
	public static double getDaySection(int section, Date date) {
		// 当前时间
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MINUTE, -(cal.get(Calendar.MINUTE) % section));
		cal.set(Calendar.SECOND, 0);
		Date startDte = cal.getTime();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		return (startDte.getTime() - cal.getTimeInMillis()) / 1000D / 3600D;
	}

	/**
	 * 对时间进行加运算
	 * 
	 * @param fromDate
	 *            初始时间
	 * @param unit
	 *            变动的单位,例:Calendar.MINUTE
	 * @param length
	 *            变动的幅度,为负值时为减运算
	 * @return Date
	 * @author hanzibin
	 * @date 2016年2月23日14:16:26
	 */
	public static Date calculateDate(Date fromDate, int unit, int length) {
		// 当前时间
		Calendar cal = Calendar.getInstance();
		if (fromDate != null) {
			cal.setTime(fromDate);
		}
		cal.add(unit, length);
		return cal.getTime();
	}

	/**
	 * 获取增量结束时间
	 * 
	 * @param endtime
	 *            预约的结束时间
	 * @param increment
	 *            增量,单位：秒
	 * @return Date
	 * @author 欧阳增高
	 * @date 2015-11-30 下午2:35:13
	 */
	public static Calendar getIncrementEndtime(Date endtime, Integer increment) {
		if (increment == null) {
			increment = 0;
		}
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endtime);
		endCal.add(Calendar.SECOND, increment);
		return endCal;
	}

	/**
	 * 
	 * <p>
	 * Description: 判断日期字符串是否满足对应的长度
	 * </p>
	 * 
	 * @param dateTime
	 *            日期字符串
	 * @return true 是 false 否
	 * @author wangby
	 * @date 2016年4月20日 上午11:29:29
	 */
	public static Boolean checkDateTimeStringLength(String dateTime) {
		int length = dateTime.length();
		if (length > DateStringLength.DATETIME.val()) {
			return false;
		}

		if (length < DateStringLength.TIME.val()) {
			return false;
		}

		if (length == DateStringLength.DATE.val()
				|| length == DateStringLength.TIME.val()
				|| length == DateStringLength.DATETIME.val()) {
			return true;
		}

		return false;
	}

	/**
	 * 获取指定时间的Unix时间戳
	 * 
	 * @param dte
	 *            需要获取的时间
	 * @return long
	 */
	public static Long getUnixTimestamp() {
		return getUnixTimestamp(null);
	}
	/**
	 * 获取指定时间的Unix时间戳
	 * 
	 * @param dte
	 *            需要获取的时间
	 * @return long
	 */
	public static Long getUnixTimestamp(Date dte) {
		if (dte == null) {
			dte = new Date();
		}
		return dte.getTime() / 1000l;
	}
	
	/**
	 * 判断两个日期是否同一天
	 * @Auther Hanzibin
	 * @date 2:34:22 PM,May 18, 2016
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Date day1, Date day2) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String ds1 = sdf.format(day1);
	    String ds2 = sdf.format(day2);
	    if (ds1.equals(ds2)) {
	        return true;
	    } else {
	        return false;
	    }
	}
}
