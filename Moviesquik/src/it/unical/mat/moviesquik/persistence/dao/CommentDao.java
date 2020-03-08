/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Comment;
import it.unical.mat.moviesquik.model.Post;

/**
 * @author Agostino
 *
 */
public interface CommentDao
{
	public boolean save( final Comment comment );
	public List<Comment> findByPost( final Post post, final int limit );
}
