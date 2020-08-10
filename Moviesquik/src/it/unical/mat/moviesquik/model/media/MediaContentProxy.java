/**
 * 
 */
package it.unical.mat.moviesquik.model.media;

import it.unical.mat.moviesquik.model.analytics.MediaContentStatistics;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class MediaContentProxy extends MediaContent
{
	@Override
	public MediaContentStatistics getStatistics()
	{
		if ( statistics == null )
			statistics = DBManager.getInstance().getDaoFactory().getMediaContentStatisticsDao().find(id);
		return statistics;
	}
}
