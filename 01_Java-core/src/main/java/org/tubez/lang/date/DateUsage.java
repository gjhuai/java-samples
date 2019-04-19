package org.tubez.lang.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class DateUsage {

	/**
	 * 将字符串转换为日期
	 */
	@Test
	public void string2Date() {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

		try {
			Date d1 = df.parse("2001-01-01");
			Date d2 = df.parse("2000-1-1");
			assertEquals("Mon Jan 01 00:01:00 CST 2001", d1.toString());
			assertEquals("Sat Jan 01 00:01:00 CST 2000", d2.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日期的格式化成字符串
	 */
	@Test
	public void date2string() {
		Date date = new Date(1000);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"E yyyy.MM.dd 'at' hh:mm:ss a zzz");

		assertEquals("星期四 1970.01.01 at 08:00:01 上午 CST",
				formatter.format(date));
	}

	/**
	 * 日期比较
	 */
	@Test
	public void compareDate() {
		Date d1 = new Date(); // 当前时间
		Date d2 = new Date(0); // Thu Jan 01 08:00:00 CST 1970

		assertEquals("Thu Jan 01 08:00:00 CST 1970", d2.toString());
		assertFalse(d1.equals(d2));
	}

	/**
	 * 计算日期之间的间隔
	 */
	@Test
	public void diffDate() {
		Date d1 = new Date(0); // Thu Jan 01 08:00:00 CST 1970
		Date d2 = new GregorianCalendar(2000, 11, 31, 23, 59).getTime();

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		assertEquals(978278340000L, diff);
		assertEquals(11322, (diff / (1000 * 60 * 60 * 24))); // 2000 年距1970 有多少天
	}

	/**
	 * 日期的加减运算
	 * 
	 * 用Date的setTime方法
	 */
	@Test
	public void addDate() {
		Date date = new Date(0);
		long msec = date.getTime();

		msec += 100 * 24 * 60 * 60 * 1000L; // 加上100天
		date.setTime(msec);

		assertEquals("Sat Apr 11 08:00:00 CST 1970", date.toString());
	}

	/**
	 * 日期的加减运算
	 * 
	 * 用Calendar类的add()方法
	 */
	@Test
	public void addDate2() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(1348559838530L)); // Tue Sep 25 15:57:18 CST
													// 2012

		calendar.add(Calendar.YEAR, 2); // 两年后的今天
		assertEquals("Thu Sep 25 15:57:18 CST 2014", calendar.getTime()
				.toString()); // Thu Sep 25 15:57:18 CST 2014
	}

	/**
	 * 修改日期
	 */
	@Test
	public void setDate() {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.MINUTE, 29);
		calendar.set(Calendar.SECOND, 22);
	}

	/**
	 * 得到一个星期的第一天
	 */
	@Test
	public void getFirstDayOfWeek() {
		Calendar JO_cal = Calendar.getInstance(new Locale("ar", "JO")); // Tue
																		// Sep
																		// 25
																		// 16:16:07
																		// CST
																		// 2012
		Calendar FR_cal = Calendar.getInstance(Locale.FRANCE);
		Calendar CA_cal = Calendar.getInstance(Locale.CANADA);

		DateFormatSymbols dfs = new DateFormatSymbols(Locale.CHINA);
		String weekdays[] = dfs.getWeekdays();

		// 在美国，这一天是 SUNDAY，而在法国，这一天是 MONDAY。
		assertEquals("星期六", weekdays[JO_cal.getFirstDayOfWeek()]); // 星期六
		assertEquals("星期一", weekdays[FR_cal.getFirstDayOfWeek()]); // 星期一
		assertEquals("星期日", weekdays[CA_cal.getFirstDayOfWeek()]); // 星期日
	}

	/**
	 * 得到不同时区的当前时间
	 */
	@Test
	public void getCurrentTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+12:00"));
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+12:00")); // 北京时间为GMT+08:00
		System.out.println(sdf.format(cal.getTime()));
	}

	@Test
	public void getRuninngTime() throws InterruptedException {
		long t0, t1;
		t0 = System.currentTimeMillis();
		Thread.sleep(500);
		t1 = System.currentTimeMillis();

		assertEquals(500,t1 - t0);
	}
}
