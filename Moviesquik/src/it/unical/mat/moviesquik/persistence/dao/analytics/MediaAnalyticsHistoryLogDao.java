/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import java.util.List;

import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryLog;
import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryWindow;
import it.unical.mat.moviesquik.model.media.MediaContent;

/**
 * @author Agostino
 *
 */
public interface MediaAnalyticsHistoryLogDao
{
	public boolean insert( final MediaAnalyticsHistoryLog log );
	public List<MediaAnalyticsHistoryLog> findByMediaContent( final MediaContent mc, final MediaAnalyticsHistoryWindow win );
	public List<MediaAnalyticsHistoryLog> findByLastDay();
}
