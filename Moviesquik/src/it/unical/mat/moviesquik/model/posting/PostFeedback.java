/**
 * 
 */
package it.unical.mat.moviesquik.model.posting;

import it.unical.mat.moviesquik.model.accounting.User;

/**
 * @author Agostino
 *
 */
public class PostFeedback
{
	private Long id;
	private boolean isLike;
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
	public boolean isLike()
	{
		return isLike;
	}
	public void setLike(boolean isLike)
	{
		this.isLike = isLike;
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
