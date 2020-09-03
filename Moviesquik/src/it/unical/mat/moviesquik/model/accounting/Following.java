/**
 * 
 */
package it.unical.mat.moviesquik.model.accounting;

/**
 * @author Agostino
 *
 */
public class Following
{
	private User follower;
	private User followed;
	
	public User getFollower()
	{
		return follower;
	}
	public void setFollower(User follower)
	{
		this.follower = follower;
	}
	
	public User getFollowed()
	{
		return followed;
	}
	public void setFollowed(User followed)
	{
		this.followed = followed;
	}
	
}
