/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Agostino
 *
 */
public class DateUtil
{
	private static final SimpleDateFormat STANDARD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyy-MM");
	
	public static Date parse( final String stringDate )
	{
		return parse(stringDate, STANDARD_FORMAT);
	}
	
	public static Date parseMonthFormat( final String stringDate )
	{
		return parse(stringDate, MONTH_FORMAT);
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
	
	public static java.sql.Date toJDBC( final Date fromDate )
	{
		return new java.sql.Date(fromDate.getTime());
	}
	
	public static Date toJava( final java.sql.Date fromDate )
	{
		return new Date(fromDate.getTime());
	}
	
	public static Date getCurrent()
	{
		return new Date(System.currentTimeMillis());
	}
}
