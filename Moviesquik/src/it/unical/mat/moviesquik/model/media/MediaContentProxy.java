/**
 * 
 */
package it.unical.mat.moviesquik.model.media;

import it.unical.mat.moviesquik.model.analytics.MediaContentSharing;
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
	
	@Override
	public MediaContentSharing getShortSharing()
	{
		if ( shortSharing == null )
			shortSharing = DBManager.getInstance().getDaoFactory().getMediaContentSharingDao().findShortSharing(this);
		return shortSharing;
	}
	
	@Override
	public MediaContentSharing getLongSharing()
	{
		if ( longSharing == null )
			longSharing = DBManager.getInstance().getDaoFactory().getMediaContentSharingDao().findLongSharing(this);
		return longSharing;
	}
}
