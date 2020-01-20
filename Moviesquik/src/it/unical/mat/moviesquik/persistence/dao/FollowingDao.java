/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.Following;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface FollowingDao
{
	public boolean save( final Following following );
	public Following findByMembers( final User follower, final User followed );
}
