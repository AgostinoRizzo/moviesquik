/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import it.unical.mat.moviesquik.model.analytics.MediaContentReview;

/**
 * @author Agostino
 *
 */
public interface MediaContentReviewDao
{
	public boolean save( final MediaContentReview review );
	public MediaContentReview find( final Long subjectUserId, final Long mediaContentId );
}
