/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface PostDao
{
	public boolean save( final Post post );
	public List<Post> findByUser( final User user, final int limit );
	public List<Post> findByFollowedUsers( final User user, final int limit );
}
