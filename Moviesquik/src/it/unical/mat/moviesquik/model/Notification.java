/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class Notification
{	
	private Long id;
	private Date dateTime;
	private String title;
	private String description;
	private Boolean isRead;
	private User subjectUser;
	private MovieParty movieParty;
	
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
		return DateUtil.toHumanReadable(dateTime);
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
	public User getSubjectUser()
	{
		return subjectUser;
	}
	public void setSubjectUser(User subjectUser)
	{
		this.subjectUser = subjectUser;
	}
	public MovieParty getMovieParty()
	{
		return movieParty;
	}
	public void setMovieParty(MovieParty movieParty)
	{
		this.movieParty = movieParty;
	}
	
}
