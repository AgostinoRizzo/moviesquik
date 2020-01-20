/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.Friendship;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface FriendshipDao
{
	public boolean save( final Friendship friendship );
	public boolean update( final Friendship friendship );
	public Friendship findByMembers( final User a_user, final User b_user );
}
