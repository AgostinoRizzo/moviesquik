/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import it.unical.mat.moviesquik.model.analytics.WatchHistoryLog;

/**
 * @author Agostino
 *
 */
public interface WatchHistoryLogDao
{
	public boolean log( final WatchHistoryLog log );
}
