/**
 * 
 */
package it.unical.mat.moviesquik.model.analytics;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public class MediaContentReview
{
	private User subject;
	private MediaContent mediaContent;
	private Integer rate;
	private Boolean like;
	
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
	public Integer getRate()
	{
		return rate;
	}
	public void setRate(Integer rate)
	{
		this.rate = rate;
	}
	public Boolean getLike()
	{
		return like;
	}
	public void setLike(Boolean like)
	{
		this.like = like;
	}
	
}
