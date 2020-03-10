/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Comment;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public interface CommentDao
{
	public boolean save( final Comment comment );
	public List<Comment> findByPost( final Post post, final DataListPage page );
}
