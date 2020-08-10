/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import it.unical.mat.moviesquik.model.analytics.MediaContentStatistics;

/**
 * @author Agostino
 *
 */
public interface MediaContentStatisticsDao
{
	public MediaContentStatistics find( final Long mediaContentId );
}
