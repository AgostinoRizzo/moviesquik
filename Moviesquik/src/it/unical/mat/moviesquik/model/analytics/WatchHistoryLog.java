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
public class WatchHistoryLog
{
	private Long id;
	private User subject;
	private MediaContent mediaContent;
	private Date watchDateTime;
	
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
	public Date getWatchDateTime()
	{
		return watchDateTime;
	}
	public void setWatchDateTime(Date dateTime)
	{
		this.watchDateTime = dateTime;
	}
	
}
