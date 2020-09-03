/**
 * 
 */
package it.unical.mat.moviesquik.controller.notification;

import java.util.Date;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.posting.Notification;

/**
 * @author Agostino
 *
 */
public class NotificationPacket
{
	private Long id;
	private Date dateTime;
	private String humanReadableDateTime;
	private String title;
	private String description;
	private Boolean isRead;
	private Long subjectUserId;
	private Long moviePartyId;
	private String iconSrc;
	private Boolean trigger=false;
	private String triggerUrl=null;
	private String triggerError=null;
	
	public NotificationPacket()
	{}
	
	public NotificationPacket( final Notification notification )
	{
		id = notification.getId();
		dateTime = notification.getDateTime();
		humanReadableDateTime = notification.getHumanReadableDateTime();
		title = notification.getTitle();
		description = notification.getDescription();
		isRead = notification.getIsRead();
		
		final User subjectUser = notification.getSubjectUser();
		final MovieParty movieParty = notification.getMovieParty();
		
		iconSrc = "";
		subjectUserId = null;
		moviePartyId = null;
		if ( subjectUser != null )
		{
			subjectUserId = subjectUser.getId();
			iconSrc = subjectUser.getProfileImagePath();
		}
		if ( movieParty != null )
			moviePartyId = movieParty.getId();
		
		
	}
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Date getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	public String getHumanReadableDateTime()
	{
		return humanReadableDateTime;
	}
	public void setHumanReadableDateTime(String humanReadableDateTime)
	{
		this.humanReadableDateTime = humanReadableDateTime;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Boolean getIsRead()
	{
		return isRead;
	}
	public void setIsRead(Boolean isRead)
	{
		this.isRead = isRead;
	}
	public Long getSubjectUserId()
	{
		return subjectUserId;
	}
	public void setSubjectUserId(Long subjectUserId)
	{
		this.subjectUserId = subjectUserId;
	}
	public Long getMoviePartyId()
	{
		return moviePartyId;
	}
	public void setMoviePartyId(Long moviePartyId)
	{
		this.moviePartyId = moviePartyId;
	}
	public String getIconSrc()
	{
		return iconSrc;
	}
	public void setIconSrc(String iconSrc)
	{
		this.iconSrc = iconSrc;
	}
	public Boolean getTrigger()
	{
		return trigger;
	}
	public void setTrigger(Boolean trigger)
	{
		this.trigger = trigger;
	}
	public String getTriggerUrl()
	{
		return triggerUrl;
	}
	public void setTriggerUrl(String triggerUrl)
	{
		this.triggerUrl = triggerUrl;
	}
	public String getTriggerError()
	{
		return triggerError;
	}
	public void setTriggerError(String triggerError)
	{
		this.triggerError = triggerError;
	}
	
}
