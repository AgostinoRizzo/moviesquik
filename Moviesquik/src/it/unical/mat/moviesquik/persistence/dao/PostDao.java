/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.posting.Post;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public interface PostDao
{
	public boolean save( final Post post );
	public Post findById( final Long id );
	public List<Post> findByUser( final User user, final DataListPage page );
	public List<Post> findByFollowedUsers( final User user, final DataListPage page );
}
