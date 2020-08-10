/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.analytics.WatchHistoryLog;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class AnalyticsManager
{
	private static final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
	
	public static boolean logNewMediaWatch( final User subject, final MediaContent mediaContent )
	{
		final WatchHistoryLog watchlog = new WatchHistoryLog();
		
		watchlog.setSubject(subject);
		watchlog.setMediaContent(mediaContent);
		watchlog.setWatchDateTime(DateUtil.getCurrent());
		
		return daoFactory.getWatchHistoryLogDao().log(watchlog);
	}
}
