/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import it.unical.mat.moviesquik.model.analytics.MediaContentSharing;
import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public interface MediaContentSharingDao
{
	public MediaContentSharing findShortSharing( final MediaContent mc );
	public MediaContentSharing findLongSharing( final MediaContent mc );
}
