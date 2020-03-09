/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;
import java.util.List;

import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class Post
{
	public static final int POSTS_LIMIT = 5;
	
	private Long id;
	private Date dateTime;
	private String text;
	private User owner;
	private List<Comment> comments;
	private Long numAllComments;
	private Long numLikes;
	private Long numLoves;
	
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
	public List<Comment> getComments()
	{
		comments = DBManager.getInstance().getDaoFactory().getCommentDao().findByPost(this, Comment.COMMENTS_LIMIT);
		return comments;
	}
	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
	}
	public Long getNumAllComments()
	{
		return numAllComments;
	}
	public void setNumAllComments(Long numAllComments)
	{
		this.numAllComments = numAllComments;
	}
	public Long getNumLikes()
	{
		return numLikes;
	}
	public void setNumLikes(Long numLikes)
	{
		this.numLikes = numLikes;
	}
	public Long getNumLoves()
	{
		return numLoves;
	}
	public void setNumLoves(Long numLoves)
	{
		this.numLoves = numLoves;
	}
}
