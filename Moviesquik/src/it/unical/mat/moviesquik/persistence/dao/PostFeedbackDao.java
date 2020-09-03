/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.posting.Post;
import it.unical.mat.moviesquik.model.posting.PostFeedback;

/**
 * @author Agostino
 *
 */
public interface PostFeedbackDao
{
	public boolean save( final PostFeedback feedback );
	public PostFeedback findByUserPost( final User user, final Post post, final boolean is_like );
}
