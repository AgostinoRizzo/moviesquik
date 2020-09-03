/**
 * 
 */
package it.unical.mat.moviesquik.model.analytics;

import java.util.Date;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public class MediaStatisticLog
{
	private Long id;
	private User subject;
	private MediaContent mediaContent;
	private Date logDay;
	private Long hits;
	private Long scrolls;
	private Integer spentTime;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public User getSubject()
	{
		return subject;
	}
	public void setSubject(User subject)
	{
		this.subject = subject;
	}
	public MediaContent getMediaContent()
	{
		return mediaContent;
	}
	public void setMediaContent(MediaContent mediaContent)
	{
		this.mediaContent = mediaContent;
	}
	public Date getLogDay()
	{
		return logDay;
	}
	public void setLogDay(Date logDay)
	{
		this.logDay = logDay;
	}
	public Long getHits()
	{
		return hits;
	}
	public void setHits(Long hits)
	{
		this.hits = hits;
	}
	public Long getScrolls()
	{
		return scrolls;
	}
	public void setScrolls(Long scrolls)
	{
		this.scrolls = scrolls;
	}
	public Integer getSpentTime()
	{
		return spentTime;
	}
	public void setSpentTime(Integer spentTime)
	{
		this.spentTime = spentTime;
	}
	
}
