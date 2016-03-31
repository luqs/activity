package com.cuize.product.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ch.qos.logback.classic.Logger;

public class DateUtils {
	private static final Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(DateUtils.class);
	/**
	 * 日期格式
	 */
	public static final String YYYY_MM_DD_DATE_FORMAT = "yyyy-MM-dd";
	public static final String YYYYMMDD_DATE_FORMAT = "yyyyMMdd";
	public static final String YYYY_MM_DD_HH_mm_SS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DATE_FORMAT = "YYYY-MM";

	public static final int LEAP_YEAR_DAYS = 366;
	public static final int NO_LEAP_YEAR_DAYS = 365;


	/**
	 * 按月结算，获取周期
	 * @param date
	 * @return
	 */
	public static String[] getBalanceByMonth(Date date) {
		String[] dates = new String[2];
		dates[0] = getDay(date, 0, 0, Boolean.TRUE, YYYY_MM_DD_DATE_FORMAT);
		dates[1] = getDay(date, 0, 0, Boolean.FALSE, YYYY_MM_DD_DATE_FORMAT);
		return dates;
	}


	/**
	 * 格式化日期显示：今天 10:30   昨天 10:30   前天 10:30  2015-12-12 10:30
	 * @param date
	 * @return
	 */
	public static String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance();
		
		c.setTime(new Date());
		int todayYear = c.get(Calendar.YEAR);
		int todayMon = c.get(Calendar.MONTH);
		int today = c.get(Calendar.DAY_OF_MONTH);
		
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int mon = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		String result = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
		if(todayYear==year&&todayMon==mon){
			if(today==day){
				result= "今天  " + sdf.format(date);
			}else if(today-day==1){
				result= "昨天  " + sdf.format(date);
			}else if(today-day==2){
				result= "前天  " + sdf.format(date);
			}
		}
		return result;
	}
	public static void main(String[] args) {
		Date oDate = new GregorianCalendar(2016, 0, 6,13,13,0).getTime();
		System.out.println(getFormatDate(oDate));
	}

	/**
	 * 获取一年天数
	 * 
	 * @param year
	 * @return
	 */
	public static int getTotalDaysPerYear(int year) {
		return isLeapYear(year) ? LEAP_YEAR_DAYS : NO_LEAP_YEAR_DAYS;
	}

	/**
	 * 获取月份总天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getTotalDaysPerMonth(int year, int month) {
		boolean yearleap = isLeapYear(year);
		int day;
		if (yearleap && month == 2) {
			day = 29;
		} else if (!yearleap && month == 2) {
			day = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		} else {
			day = 31;
		}
		return day;
	}

	/**
	 * 获取日期 例“2015-01-16, 1, 1, true, yyyy-MM-dd” 输出为2016-02-01
	 * @param date
	 * @param addYearValue
	 * @param addMonthValue
	 * @param isFirstDay 如果为true，则是当月第一天，如果为false，则是当月最后一天
	 * @return
	 */
	public static String getDay(Date date, int addYearValue, int addMonthValue, boolean isFirstDay, String dateFormat) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.YEAR, addYearValue);
		ca.add(Calendar.MONTH, addMonthValue);
		if (isFirstDay) {
			ca.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DATE));
		}
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		return sf.format(ca.getTime());
	}

	/**
	 * 获取指定月份的时间， 例“2015-01-16, 4, true, yyyy-MM-dd” 输出为2016-04-01
	 * @param date
	 * @param month
	 * @param isFirstDay 如果为true，则是当月第一天，如果为false，则是当月最后一天
	 * @param dateFormat
	 * @return
	 */
	public static String getDesignatedDay(Date date, int month, boolean isFirstDay, String dateFormat) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.MONTH, month - 1);
		if (isFirstDay) {
			ca.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DATE));
		}
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		return sf.format(ca.getTime());
	}

	/**
	 * 获取该date归属月份
	 * @param date
	 * @return
	 */
	public static int getCurrentMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int month = ca.get(Calendar.MONTH);
		return month > 12 ? 1 : month + 1;
	}

	/**
	 * 获取该date归属年份
	 * @param date
	 * @return
	 */
	public static int getCurrentYear(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(Calendar.YEAR);
	}

	/**
	 * 格式化日期
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatDate(Date date, String dateFormat) {
		SimpleDateFormat f = new SimpleDateFormat(dateFormat);
		String sDate = f.format(date);
		return sDate;
	}

	/**
	 * 以date为准，获取第几个月的第几天，如date为（2015-06-15，1,2） 则结果为2015-07-02
	 * @param date
	 * @param monthNum
	 * @param dayNum
	 * @return
	 */
	public static String getPreOrfurDate(Date date, int monthNum, int dayNum, String dateFormat) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, monthNum);
		ca.add(Calendar.DAY_OF_MONTH, dayNum);
		return formatDate(ca.getTime(), dateFormat);
	}

	/**
	 * 以date为准，操作事件+-days
	 * @param date
	 * @param dayNum
	 */
	public static Date getPreOrfurDate(Date date, int dayNum) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, dayNum);
		return ca.getTime();
	}
	/**
	 * 以分钟为准，操作事件+-分钟
	 * @param 分钟
	 * @param dayNum
	 */
	public static Date getPreOrfurDateByMin(Date date, int minNum) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minNum);
		return ca.getTime();
	}
	/**
	 * 以date为准，操作事件+-days
	 * @param date
	 * @param dayNum
	 */
	public static Date getPreOrfurDateByYear(Date date, int yearNum) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.YEAR, yearNum);
		return ca.getTime();
	}

	/**
	 * 能被4整除且又能不能被100整除 是闰年,能直接被400整除也是闰年
	 * 
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year) {
		return (year % 400 == 0) || (year % 4 == 0) && (year % 100 != 0);
	}

	public static String getFormatDateTime() {
		GregorianCalendar gca = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_mm_SS_DATE_FORMAT);
		return sdf.format(gca.getTime());
	}

	/**
	 * 整数天运算 到秒单位
	 */
	public static Long formatDateToLong(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.getTimeInMillis() / 1000;
	}

	/**
	 * 秒运算 到date单位
	 */
	public static Date formatLongToDate(Long para) {
		return new Date(para * 1000);
	}
	/**
	 * 获取当前时间 （到秒单位）
	 * @return long
	 */
	public static long getTimeInSeconds() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 下一个月1号
	 * @param date
	 * @return
	 */
	public static Date nextMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 当月1号
	 * @param date
	 * @return
	 */
	public static Date currentMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date convertStringToDate(String dateStr, String model) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(model);
		return df.parse(dateStr);
	}

	public static void mai2n(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = "2015-10-31 10:56:12";

		Date date = df.parse(str);
		System.out.println(currentMonthFirstDate(date));

		// System.out.println(getPreOrfurDate(nextMonthFirstDate(date),-1));

		// // System.out.println(getDesignatedDay(date, 1, true, "yyyy-MM-dd"));
		// String[] s = getBalanceDate(5, 2, date);
		// System.out.println(s[0]);
		// System.out.println(s[1]);
		// System.out.println(getFormatDateTime());

		// Long ls = 1440640561l;
		// System.out.println(formatLongToDate(ls));
		// System.out.println(getNowDateTime());

		// System.out.println(countCompareDays("20150914", "20150914"));
		//
		// System.out.println(getPreOrfurDate(new Date(),-1));

		// System.out.println(nextMonthFirstDate(date));
		// System.out.println(DateUtils.formatDate(date,"YYYY-MM月"));

		// System.out.println(new BigDecimal("0.0").setScale(2,BigDecimal.ROUND_HALF_UP).equals(new
		// BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP)));;
		// System.out.println(getPreOrfurDate(new Date(1443149772* 1000),0));

		// 1443110400 000
		// System.out.println(date);
		// System.out.println(date.getTime());

		System.out.println(formatLongToDate(1444651898l));

	}

	// /**
	// * 获取现在时间
	// *
	// * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
	// */
	// public static Date getNowDate(String dateFormat) {
	// SimpleDateFormat df = new SimpleDateFormat(dateFormat);
	// try {
	// return df.parse(df.format(new Date()));
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * 获取现在时间
	 * 
	 * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate(String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);// 设置日期格式
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			logger.error("getNowDate error", e);
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (Exception ex) {
			logger.error("getNowDate error", ex);
		}
		return null;
	}

	/**
	 * 设置时间格式
	 * 
	 * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static Date convertDateFormat(Date date , String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);// 设置日期格式
		try {
			return df.parse(df.format(date));
		} catch (ParseException e) {
			logger.error("getNowDate error", e);
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (Exception ex) {
			logger.error("getNowDate error", ex);
		}
		return null;
	}
	/**
	 * 比较beginDate与endDate之间相差的天数 与 days的大小 beginDate>endDate 1 beginDate=endDate 0
	 * @param beginDate
	 * @param endDate
	 * @param days
	 * @return
	 */
	public static int compare(String beginDate, String endDate) {
		try {
			Calendar end = Calendar.getInstance();
			end.setTime(parse("yyyyMMdd", endDate));
			Calendar begin = Calendar.getInstance();
			begin.setTime(parse("yyyyMMdd", beginDate));

			return begin.compareTo(end);

		} catch (Exception e) {
			logger.error("compare error", e);
			return 99;
		}
	}

	public static long countCompareDays(String beginDate, String endDate) {
		try {

			SimpleDateFormat smdf = new SimpleDateFormat("yyyyMMdd");
			Date start = smdf.parse(beginDate);
			Date end = smdf.parse(endDate);
			long days = (end.getTime() - start.getTime()) / (3600 * 24 * 1000);
			return days;
		} catch (Exception e) {
			logger.error("countCompareDays error", e);
			return -9999;
		}
	}

	/**
	 * Parses a string to produce a Date.
	 * 
	 * @param pattern - the pattern of the string
	 * @param strDateTime - the string to be parsed
	 * @return A Date parsed from the string. In case of error, returns null.
	 */
	public static java.util.Date parse(String pattern, String strDateTime) {
		return parse(pattern, strDateTime, Locale.getDefault());
	}

	/**
	 * 解析27/Feb/2008:10:12:35 +0800类似时间字符串.
	 * 
	 * @param pattern
	 * @param strDateTime
	 * @param locale
	 * @return
	 */
	public static java.util.Date parse(String pattern, String strDateTime, Locale locale) {
		if (strDateTime == null || pattern == null)
			return null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
			formatter.setLenient(false);
			return formatter.parse(strDateTime);
		} catch (Exception e) {
			logger.error("parse error", e);
			return null;
		}
	}

	/**
	 * 判断给定日期是否为月末的一天
	 * 
	 * @param date
	 * @return true:是|false:不是
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	public static final int getDaysOfCurrYear() {
		return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
	}


	/**
	 * 判断是否月末
	 * @param date
	 * @return
	 */
	public static boolean isMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}
	
	/**
	 * 获取现在时间字符串
	 * 
	 * @return 返回长时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateString(String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);// 设置日期格式
		try {
			return df.format(new Date());
		} catch (Exception ex) {
			logger.error("getNowDateString error", ex);
		}
		return null;
	}

}
