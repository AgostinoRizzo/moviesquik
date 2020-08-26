/**
 * 
 */
package it.unical.mat.moviesquik.model.analytics;

import java.util.Arrays;
import java.util.Date;

import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public class MediaAnalyticsHistoryLog
{
	public static final short ANALYTICS_VALUES_COUNT = 6;
	
	public static final short TRENDING_VALUE   = 0;
	public static final short POPULARITY_VALUE = 1;
	public static final short RATE_VALUE       = 2;
	public static final short LIKES_VALUE      = 3;
	public static final short NOLIKES_VALUE    = 4;
	public static final short VIEWS_VALUE      = 5;
	
	protected Long id;
	protected MediaContent media;
	protected Date logDate;
	protected Number[] analyticsValues = new Number[ANALYTICS_VALUES_COUNT];
	
	public MediaAnalyticsHistoryLog()
	{}
	
	public MediaAnalyticsHistoryLog( final MediaContent media, final Date logDate )
	{
		this.media = media;
		this.logDate = new Date(logDate.getTime());
		Arrays.fill(analyticsValues, 0);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public MediaContent getMedia()
	{
		return media;
	}

	public void setMedia(MediaContent media)
	{
		this.media = media;
	}

	public Date getLogDate()
	{
		return logDate;
	}

	public void setLogDate(Date logDate)
	{
		this.logDate = logDate;
	}

	public Number[] getAnalyticsValues()
	{
		return analyticsValues;
	}

	public void setAnalyticsValues(Number[] analyticsValues)
	{
		this.analyticsValues = analyticsValues;
	}
	
}
