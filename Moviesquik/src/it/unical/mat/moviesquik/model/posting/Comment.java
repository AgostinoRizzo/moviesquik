/**
 * 
 */
package it.unical.mat.moviesquik.model.posting;

import java.util.Date;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class Comment
{	
	private Long id;
	private Date dateTime;
	private String text;
	private User owner;
	private Post referredPost;
	
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
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public User getOwner()
	{
		return owner;
	}
	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	public Post getReferredPost()
	{
		return referredPost;
	}
	public void setReferredPost(Post referredPost)
	{
		this.referredPost = referredPost;
	}
}
