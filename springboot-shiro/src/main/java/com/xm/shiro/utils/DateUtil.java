package com.xm.shiro.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private final static SimpleDateFormat FORMATTER_YEAR = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat FORMATTER_YEARMONTH = new SimpleDateFormat("yyyyMM");
    private final static SimpleDateFormat FORMATTER_YEARMONTHDAY = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat FORMATTER_COMPACT_TIME = new SimpleDateFormat("HHmmss");
    private final static SimpleDateFormat FORMATTER_COMPACT_DATETIME = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private final static SimpleDateFormat FORMATTER_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat FORMATTER_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat FORMATTER_CHINESE = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    /**
     * 获取yyyyMMddHHmmss格式日期时间字符串
     * 
     * @return
     */
    public static String getTime4FileName() {
        return FORMATTER_COMPACT_DATETIME.format(new Date());
    }

    /**
     * 获取yyyy格式
     * 
     */
    public static String getYear() {
        return FORMATTER_YEAR.format(new Date());
    }

    /**
     * 解析yyyy格式
     */
    public static Date parseYear(String source) {
        try {
            return FORMATTER_YEAR.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 获取yyyyMM格式
     * 
     */
    public static String getYearMonth() {
        return FORMATTER_YEARMONTH.format(new Date());
    }

    /**
     * 解析yyyyMM格式
     */
    public static Date parseYearMonth(String source) {
        try {
            return FORMATTER_YEARMONTH.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 获取YYYY-MM-DD格式
     * 
     * @return
     */
    public static String getDay() {
        return FORMATTER_DATE.format(new Date());
    }

    /**
     * 解析YYYY-MM-DD格式
     * 
     * @return
     */
    public static Date parseDay(String source) {
        try {
            return FORMATTER_DATE.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取YYYYMMDD格式
     * 
     * @return
     */
    public static String getDays() {
        return FORMATTER_YEARMONTHDAY.format(new Date());
    }

    /**
     * 解析YYYYMMDD格式
     * 
     * @return
     */
    public static Date parseDays(String source) {
        try {
            return FORMATTER_YEARMONTHDAY.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * 
     * @return
     */
    public static String getTime() {
        return FORMATTER_DATETIME.format(new Date());
    }

    /**
     * eg.2015年01月01日 13时27分15秒
     * 
     * @return
     */
    public static String getChineseTime() {
        return FORMATTER_CHINESE.format(new Date());
    }

    public static String getPureTime() {
        return FORMATTER_COMPACT_TIME.format(new Date());
    }

    /**
     * @Title: compareDate
     * @Description: (日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     */
    public static boolean compareDate(String s, String e) {
        if (parseToDate(s) == null || parseToDate(e) == null) {
            return false;
        }
        return parseToDate(s).getTime() >= parseToDate(e).getTime();
    }

    public static boolean compare2(String s, String e) {
        if (parseToDate(s) == null || parseToDate(e) == null) {
            return false;
        }
        return parseToDate(s).getTime() < parseToDate(e).getTime();
    }

    /**
     * 格式化日期yyyy-MM-dd
     * 
     * @return Date
     */
    public static Date parseToDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 格式化日期yyyy-MM-dd HH:mm:ss
     * 
     * @return Date
     */
    public static Date parseToDateTime(String date) {
        try {
            return FORMATTER_DATETIME.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * date转dateString
     * <li>yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * date转dateString
     * <li>yyyyMMddHHmmss
     * 
     * @param date
     * @return
     */
    public static String date2str(Date date) {
        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return fmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String dateStr(Date date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String strDateStr(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = parseToDate(date);
            return fmt.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     * 
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
                    / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     * 
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     * 
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 获取指定日期days天后的日期
     * 
     * @param date
     * @return
     */
    public static Date getAfterDayDate(Date date, Integer days) {

        Calendar calendar = Calendar.getInstance(); // java.util包
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        Date dateReturn = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(dateReturn);
        Date d = null;
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {

        }
        return d;
    }

    /**
     * 得到n天之后是周几
     * 
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 当前之间距离明天0点还有多少秒
     * 
     * @return
     */
    public static int sec2tomorrow0() {
        Date nowDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long tomorrowMsec = cal.getTime().getTime();
        long nowMsec = nowDate.getTime();

        return (int) (tomorrowMsec - nowMsec) / 1000;
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    public static Date fromLocalDate(LocalDate date) {
        if (date == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        return cal.getTime();

    }

    public static void main(String[] args) {
        System.out.println(getDays());
        System.out.println(getAfterDayWeek("3"));
        System.out.println(getAfterDayDate(new Date(), 1));
        System.out.println(strDateStr("2015-09-09 00:00:00"));
        String cur = date2String(new Date());
        String next = "2015-11-30 23:59:59";
        System.out.println(getDaySub(cur, next));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println("明天0点：" + cal.getTime());
        System.out.println((int) ((cal.getTime().getTime() - new Date().getTime()) / 1000));
        System.out.println(sec2tomorrow0());
    }

    /**
     * 清除时分秒毫秒
     * 
     * @return
     */
    public static Date date() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTime();
    }

    /**
     * 清除时分秒毫秒
     * 
     * @return
     */
    public static Date date(Date date) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTime();
    }

    /**
     * 判断两个时期是否是同一天
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean isSameDay(Date a, Date b) {
        a = date(a);
        b = date(b);
        return a.equals(b);
    }
    /**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

}
