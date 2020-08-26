/**
 * 
 */
package it.unical.mat.moviesquik.controller.analytics;

import java.util.List;

import it.unical.mat.moviesquik.controller.async.Worker;
import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryLog;
import it.unical.mat.moviesquik.model.analytics.MediaContentSharing;
import it.unical.mat.moviesquik.model.analytics.MediaContentStatistics;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class MediaAnalyticsHistoryLogUpdater extends Worker
{
	private static final long NEXT_WORK_TIME = 86400000;  // (1 day) expressed in milliseconds
	private static MediaAnalyticsHistoryLogUpdater instance = null;
	
	public static MediaAnalyticsHistoryLogUpdater getInstance()
	{
		if ( instance == null )
			instance = new MediaAnalyticsHistoryLogUpdater();
		return instance;
	}
	
	private MediaAnalyticsHistoryLogUpdater()
	{}
	
	@Override
	protected void doWork()
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final List<MediaAnalyticsHistoryLog> lastDatLogs = daoFactory.getMediaAnalyticsHistoryLogDao().findByLastDay();
		
		if ( lastDatLogs == null || lastDatLogs.isEmpty() )
			updateMediaAnalyticsLogs();
		
		waitForNextWork(NEXT_WORK_TIME);
	}

	@Override
	protected void onFinalize()
	{}
	
	private void updateMediaAnalyticsLogs()
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final List<MediaContent> mediaContents = daoFactory.getMediaContentDao().findAll();
		
		if ( mediaContents == null )
			return;
		
		MediaContentSharing mediaShortSharing;
		MediaContentSharing mediaLongSharing;
		MediaContentStatistics mediaStatistics;
		Number[] analyticsValues;
		MediaAnalyticsHistoryLog newLog;
		
		for ( final MediaContent mc : mediaContents )
		{
			mediaShortSharing = mc.getShortSharing();
			mediaLongSharing = mc.getLongSharing();
			mediaStatistics = mc.getStatistics();
			analyticsValues = new Number[MediaAnalyticsHistoryLog.ANALYTICS_VALUES_COUNT];
			
			analyticsValues[MediaAnalyticsHistoryLog.TRENDING_VALUE]   = mediaShortSharing.getValue();
			analyticsValues[MediaAnalyticsHistoryLog.POPULARITY_VALUE] = mediaLongSharing.getValue();
			analyticsValues[MediaAnalyticsHistoryLog.RATE_VALUE]       = mediaStatistics.getActualRate();
			analyticsValues[MediaAnalyticsHistoryLog.LIKES_VALUE]      = mediaStatistics.getLikes();
			analyticsValues[MediaAnalyticsHistoryLog.NOLIKES_VALUE]    = mediaStatistics.getNolikes();
			analyticsValues[MediaAnalyticsHistoryLog.VIEWS_VALUE]      = mediaStatistics.getViews();
			
			newLog = new MediaAnalyticsHistoryLog();
			
			newLog.setLogDate(DateUtil.getYesterday());
			newLog.setMedia(mc);
			newLog.setAnalyticsValues(analyticsValues);
			
			daoFactory.getMediaAnalyticsHistoryLogDao().insert(newLog);
		}
	}

}
