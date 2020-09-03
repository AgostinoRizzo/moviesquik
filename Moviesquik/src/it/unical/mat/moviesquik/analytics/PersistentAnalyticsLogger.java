/**
 * 
 */
package it.unical.mat.moviesquik.analytics;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.analytics.WatchHistoryLog;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class PersistentAnalyticsLogger implements AnalyticsLogger
{
	private static final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
	private static PersistentAnalyticsLogger instance = null;
	
	public static PersistentAnalyticsLogger getInstance()
	{
		if ( instance == null )
			instance = new PersistentAnalyticsLogger();
		return instance;
	}
	
	private PersistentAnalyticsLogger()
	{}
	
	@Override
	public boolean logNewMediaWatch(User subject, MediaContent mediaContent)
	{
		final WatchHistoryLog watchlog = new WatchHistoryLog();
		
		watchlog.setSubject(subject);
		watchlog.setMediaContent(mediaContent);
		watchlog.setWatchDateTime(DateUtil.getCurrent());
		
		return daoFactory.getWatchHistoryLogDao().log(watchlog);
	}

	@Override
	public boolean logMediaPageHit(Long subjectId, Long mediaContentId)
	{
		return daoFactory.getMediaStatisticLogDao().logHit(subjectId, mediaContentId);
	}

	@Override
	public boolean logMediaPageScroll(Long subjectId, Long mediaContentId)
	{
		return daoFactory.getMediaStatisticLogDao().logScroll(subjectId, mediaContentId);
	}

	@Override
	public boolean logMediaPageSpentTime(Long subjectId, Long mediaContentId, Integer spentTime)
	{
		return daoFactory.getMediaStatisticLogDao().logSpentTime(subjectId, mediaContentId, spentTime);
	}

}
