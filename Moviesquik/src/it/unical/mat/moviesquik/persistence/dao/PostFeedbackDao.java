/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.PostFeedback;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface PostFeedbackDao
{
	public boolean save( final PostFeedback feedback );
	public PostFeedback findByUserPost( final User user, final Post post, final boolean is_like );
}
