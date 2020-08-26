/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryLog;
import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryLogProxy;
import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryWindow;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaAnalyticsHistoryLogDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.IdBroker;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class MediaAnalyticsHistoryLogDaoJDBC extends AbstractDaoJDBC<MediaAnalyticsHistoryLog> implements MediaAnalyticsHistoryLogDao
{
	protected static final String INSERT_STATEMENT = "INSERT INTO media_analytics_log(log_id, log_date, media_content_id, trending, popularity, rate, likes, nolikes, views) " + 
													 "VALUES (?,?,?,?,?,?,?,?,?)";
	protected static final String FIND_BY_MEDIA_CONTENT_QUERY = "SELECT * FROM media_analytics_log " + 
																"WHERE media_content_id = ? AND log_date >= CURRENT_DATE - INTEGER '7' " + 
																"ORDER BY log_date ASC";
	protected static final String FIND_BY_TODAY_DATE_QUERY = "SELECT * FROM media_analytics_log WHERE log_date = CURRENT_DATE - INTEGER '1'";
	
	public MediaAnalyticsHistoryLogDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean insert(MediaAnalyticsHistoryLog log)
	{
		try
		{
			log.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong     (1, log.getId());
			statement.setDate     (2, DateUtil.toDateJDBC(log.getLogDate()));
			statement.setLong     (3, log.getMedia().getId());
			
			final Number[] analyticsValues = log.getAnalyticsValues();
			statement.setFloat    (4, (float) analyticsValues[MediaAnalyticsHistoryLog.TRENDING_VALUE]);
			statement.setFloat    (5, (float) analyticsValues[MediaAnalyticsHistoryLog.POPULARITY_VALUE]);
			statement.setFloat    (6, (float) analyticsValues[MediaAnalyticsHistoryLog.RATE_VALUE]);
			statement.setLong     (7, (long) analyticsValues[MediaAnalyticsHistoryLog.LIKES_VALUE]);
			statement.setLong     (8, (long) analyticsValues[MediaAnalyticsHistoryLog.NOLIKES_VALUE]);
			statement.setLong     (9, (long) analyticsValues[MediaAnalyticsHistoryLog.VIEWS_VALUE]);
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public List<MediaAnalyticsHistoryLog> findByMediaContent(MediaContent mc, MediaAnalyticsHistoryWindow win)
	{
		final List<MediaAnalyticsHistoryLog> history = new ArrayList<MediaAnalyticsHistoryLog>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_MEDIA_CONTENT_QUERY);
			statement.setLong    (1, mc.getId());
			//statement.setString  (2, Integer.toString(MediaAnalyticsHistoryWindow.getDaysCount(win)));
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				history.add( createFromResult(result) );
			return history;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return history; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<MediaAnalyticsHistoryLog> findByLastDay()
	{
		final List<MediaAnalyticsHistoryLog> history = new ArrayList<MediaAnalyticsHistoryLog>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_TODAY_DATE_QUERY);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				history.add( createFromResult(result) );
			return history;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return history; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MediaAnalyticsHistoryLog createFromResult(ResultSet result) throws SQLException
	{
		final MediaAnalyticsHistoryLogProxy historyLog = new MediaAnalyticsHistoryLogProxy();
		final Number[] analyticsValues = new Number[MediaAnalyticsHistoryLog.ANALYTICS_VALUES_COUNT];
		
		historyLog.setId(result.getLong("log_id"));
		historyLog.setLogDate(DateUtil.toJava(result.getDate("log_date")));
		historyLog.setMediaContentId(result.getLong("media_content_id"));
		
		analyticsValues[MediaAnalyticsHistoryLog.TRENDING_VALUE]   = result.getFloat("trending");
		analyticsValues[MediaAnalyticsHistoryLog.POPULARITY_VALUE] = result.getFloat("popularity");
		analyticsValues[MediaAnalyticsHistoryLog.RATE_VALUE]       = result.getFloat("rate");
		analyticsValues[MediaAnalyticsHistoryLog.LIKES_VALUE]      = result.getFloat("likes");
		analyticsValues[MediaAnalyticsHistoryLog.NOLIKES_VALUE]    = result.getFloat("nolikes");
		analyticsValues[MediaAnalyticsHistoryLog.VIEWS_VALUE]      = result.getFloat("views");
		
		historyLog.setAnalyticsValues(analyticsValues);
		
		return historyLog;
	}

}
