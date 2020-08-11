/**
 * 
 */
package it.unical.mat.moviesquik.analytics;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public interface AnalyticsLogger
{
	public boolean logNewMediaWatch( final User subject, final MediaContent mediaContent );
	public boolean logMediaPageHit( final Long subjectId, final Long mediaContentId );
	public boolean logMediaPageScroll( final Long subjectId, final Long mediaContentId );
	public boolean logMediaPageSpentTime( final Long subjectId, final Long mediaContentId, final Integer spentTime );
}
