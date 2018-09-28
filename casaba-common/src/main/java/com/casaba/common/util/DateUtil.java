package com.casaba.common.util;

import com.casaba.common.constants.DateConstant;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;

	/**
	 * 时间转换为字符串
	 * 
	 * @param d
	 *            时间
	 * @param timeFormart
	 *            日期格式
	 * @return
	 */
	public static String formartDate(Date d, String timeFormart) {
		if (d == null) {
			return "";
		}
		SimpleDateFormat formart = new SimpleDateFormat(timeFormart);
		return formart.format(d);
	}

	/**
	 * 格式化日期为data
	 * 
	 * @param d
	 *            日期
	 * @param timeFormart
	 *            日期格式
	 * @return
	 */
	public static Date formartStringToDate(String d, String timeFormart) {
		if (StringUtils.isEmpty(d)) {
			return null;
		}
		SimpleDateFormat formart = new SimpleDateFormat(timeFormart);
		try {
			return formart.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化String 日期
	 *
	 * @param d
	 *            String类型日期
	 * @param timeFormart
	 *            日期格式
	 * @return
	 */
	public static String formartStringToString(String d, String timeFormart) {
		if (StringUtils.isEmpty(d)) {
			return null;
		}
		Date date = formartStringToDate(d, timeFormart);
		return formartDate(date, timeFormart);
	}

	/**
	 * 将long的时间格式转换为date
	 * 
	 * @param d
	 *            日期
	 * @param d
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String d) {
		if (StringUtils.isEmpty(d) || "null".equals(d)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(d));
		return cal.getTime();
	}

	/**
	 * 取对应时间的相差的时间
	 * 
	 * @param d
	 *            时间
	 * @param timeFormart
	 *            日期格式
	 * @param dateType
	 *            日期类型
	 * @return
	 */
	public static String getPreDayByDateTypeAndLength(Date d,
			String timeFormart, int dateType, int dateLength) {
		SimpleDateFormat formart = new SimpleDateFormat(timeFormart);
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.add(dateType, dateLength);
		return formart.format(ca.getTime());
	}

	public static int getHourByDate(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int hour = ca.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 计算两个时间之间的相差的天数
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static int getSubtractionDate(Date beginTime, Date endTime) {
		return (int) ((endTime.getTime() - beginTime.getTime()) / (24 * 3600 * 1000));
	}

	/**
	 * 计算两个时间之间的相差的毫秒
	 *
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static int getSubtractionMSec(Date beginTime, Date endTime) {
		return (int) ((endTime.getTime() - beginTime.getTime()));
	}

	/**
	 * 计算两个时间之间的相差的秒
	 *
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static int getSubtractionSecond(Date beginTime, Date endTime) {
		return (int) ((endTime.getTime() - beginTime.getTime()) / 1000);
	}

	/**
	 * 计算两个时间之间的相差的分钟数
	 *
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static int getSubtractionMinute(Date beginTime, Date endTime) {
		return (int) ((endTime.getTime() - beginTime.getTime()) / (60 * 1000));
	}

	/**
	 * 添加的日期
	 *
	 * @param date
	 *            日期
	 * @param add
	 *            增加天数
	 * @param format
	 *            格式
	 * @return
	 */
	public static String addDate(String date, int add, String format) {
		Date d = formartStringToDate(date, format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_YEAR, add);
		return formartDate(cal.getTime(), format);
	}

	/**
	 * 判断日期是周几
	 * 
	 * @param day
	 *            日期
	 * @return
	 * @throws ParseException
	 */
	public static int dayOfWeek(String day) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(formartStringToDate(day,
				DateConstant.DATE_POINT_HOUR_SS_ZERO_FORMAT));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 判断日期是这个月的第几号
	 * 
	 * @param day
	 *            日期
	 * @return
	 * @throws ParseException
	 */
	public static int dayOfMonth(String day) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(formartStringToDate(day,
				DateConstant.DATE_POINT_HOUR_SS_ZERO_FORMAT));
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据当前的日期推算日期
	 * 
	 * @param addDays
	 *            添加日期
	 * @return
	 */
	public static String getAddDaysByNow(int addDays) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, addDays);
		return formartDate(c.getTime(), DateConstant.DATE_NO_LINE_MI_FORMAT);
	}

	/**
	 * 推算几个月前距离今天有多少日
	 * 
	 * @param addDays
	 * @return
	 */
	public static int getDiffDays(int addDays) {
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MONTH, (-1) * addDays);
		return getSubtractionDate(c1.getTime(), c.getTime());
	}

	/**
	 * 根据当前的日期推算日期
	 * 
	 * @param addDays
	 *            添加日期
	 * @param date
	 *            传入的时间
	 * @return
	 */
	public static String getAddDaysByNow(String date, int addDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(formartStringToDate(date, DateConstant.DATE_NO_LINE_MI_FORMAT));
		c.add(Calendar.DAY_OF_YEAR, addDays);
		return formartDate(c.getTime(), DateConstant.DATE_NO_LINE_MI_FORMAT);
	}

	/**
	 * 添加的月数
	 * 
	 * @param date
	 *            日期
	 * @param add
	 *            增加天数
	 * @param format
	 *            格式
	 * @return
	 */
	public static String addDateMonth(String date, int add, String format) {
		Date d = formartStringToDate(date, format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, add);
		return formartDate(cal.getTime(), format);
	}

	/**
	 * 添加的月数
	 *
	 * @param date
	 *            日期
	 * @param add
	 *            增加天数
	 * @param format
	 *            格式
	 * @return
	 */
	public static String addDateDay(Date date, int add, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, add);
		return formartDate(cal.getTime(), format);
	}

	/**
	 * 添加的月数
	 * 
	 * @param date
	 *            日期
	 * @param add
	 *            增加天数
	 * @param format
	 *            格式
	 * @return
	 */
	public static String addDateDay(String date, int add, String format) {
		Date d = formartStringToDate(date, format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_YEAR, add);
		return formartDate(cal.getTime(), format);
	}

	/**
	 * 毫秒转换成 时：分：秒 00:00:00
	 */
	public static String msecToTime(long mse) {
		long hours = (mse % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mse % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mse % (1000 * 60)) / 1000;
		return (hours >= 10 ? hours : "0" + hours) + ":"
				+ (minutes >= 10 ? minutes : "0" + minutes) + ":"
				+ (seconds >= 10 ? seconds : "0" + seconds);
	}

	public static String getDateFormatter(Date date, String format) {
		if (date == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date addDays(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	public static String addDaysFormat(int days) {
		return getDateFormatter(addDays(days), "yyyy-MM-dd");
	}

	public static String addDaysFormat(int days, String format) {
		return getDateFormatter(addDays(days), format);
	}

	public static void main(String[] args) {
		Date date = formatDate1(new Date(),DateConstant.DATE_POINT_HOUR_SS_ZERO_FORMAT);
		System.out.println(date);
	}

	public static  Date formatDate1(Date source,String destFormat){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(destFormat);
		String d = simpleDateFormat.format(source);
		try {
			return simpleDateFormat.parse(d);
		} catch (ParseException e) {

			e.printStackTrace();
			return null;
		}
	}

}
