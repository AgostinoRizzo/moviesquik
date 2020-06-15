/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface UserDao extends DataAccessObject<User>
{
	public List<User> findByFamily( final Family family );
	public User findByEmail( final String email );
	public User findByLogin( final String email, final String password );
	public List<User> findFriends( final User user, final int maxCount );
	public List<User> findByName( final String name, final int limit );
	public List<User> findFriendsByName( final String name, final User user, final int limit );
}
