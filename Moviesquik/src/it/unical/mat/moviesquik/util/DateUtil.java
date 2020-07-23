/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Agostino
 *
 */
public class DateUtil
{
	private static final SimpleDateFormat STANDARD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyy-MM");
	private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static Date parse( final String stringDate )
	{
		return parse(stringDate, STANDARD_FORMAT);
	}
	
	public static Date parseMonthFormat( final String stringDate )
	{
		return parse(stringDate, MONTH_FORMAT);
	}
	
	public static Date parseDateTimeFormat( final String stringDateTime )
	{
		return parse(stringDateTime, DATETIME_FORMAT);
	}
	
	public static Date parseDateTimeFormat( final String stringDate, final String stringTime )
	{
		return parseDateTimeFormat(stringDate + " " + stringTime);
	}
	
	public static Date parse( final String stringDate, final SimpleDateFormat format )
	{
		try
		{ return format.parse(stringDate); }
		
		catch (ParseException e)
		{
			e.printStackTrace();
			return new Date(System.currentTimeMillis());
		}
	}
	
	public static Timestamp toJDBC( final Date fromDate )
	{
		if ( fromDate == null )
			return null;
		return new Timestamp(fromDate.getTime());
	}
	
	public static Date toJava( final Timestamp fromDate )
	{
		if ( fromDate == null )
			return null;
		return new Date(fromDate.getTime());
	}
	
	public static Date getCurrent()
	{
		return new Date(System.currentTimeMillis());
	}
	
	public static void setDaysHorizon( final Calendar c )
	{
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}
	
	public static String toHumanReadable( final Date when )
	{
		return toHumanReadable(when, false);
	}
	
	public static String toHumanReadable( final Date when, final boolean forceTimeString )
	{
		final Calendar right_now = Calendar.getInstance();
		final Calendar target_date = Calendar.getInstance();
		target_date.setTime(when);
		
		if ( target_date.after(right_now) ||
				target_date.get(Calendar.YEAR) != right_now.get(Calendar.YEAR) || 
				target_date.get(Calendar.MONTH) != right_now.get(Calendar.MONTH) )
			return toString(when, forceTimeString);
		
		final int days = right_now.get(Calendar.DATE) - target_date.get(Calendar.DATE);
		
		if ( days == 0 )
		{
			final int hours = right_now.get(Calendar.HOUR_OF_DAY) - target_date.get(Calendar.HOUR_OF_DAY);
			if ( hours > 0 )
				return hours + " hour" + getPluralChar(hours) + " ago";
			
			final int mins = right_now.get(Calendar.MINUTE) - target_date.get(Calendar.MINUTE);
			return (mins == 0) ? "just now" : mins + " minute" + getPluralChar(mins) + " ago";
		}
		
		if ( days == 1 )
			return "yesterday at " + getTimeString(target_date);
		
		return toString(when, forceTimeString);
	}
	
	public static String toString( final Date when )
	{
		return toString(when, false);
	}
	
	public static String toString( final Date when, final boolean includeTime )
	{
		final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
		String ans = format.format(when);
		
		if ( includeTime )
		{
			final Calendar target_date = Calendar.getInstance();
			target_date.setTime(when);
			ans += " at " + getTimeString(target_date);
		}
		
		return ans;
	}
	
	public static boolean isExpired( final Date when )
	{
		return getCurrent().after(when);
	}
	
	private static String getPluralChar( final int n )
	{
		return (n != 1 && n != -1) ? "s" : "";
	}
	
	private static String getTimeString( final Calendar cal )
	{
		return getTimeNumberString(cal.get(Calendar.HOUR)) + ":" + getTimeNumberString(cal.get(Calendar.MINUTE)) +
				" " + ((cal.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM");
	}
	
	private static String getTimeNumberString( final int time )
	{
		if ( time < 10 )
			return "0" + time;
		return Integer.toString(time);
	}
}
