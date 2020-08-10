/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.analytics.MediaContentStatistics;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentStatisticsDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class MediaContentStatisticsDaoJDBC extends AbstractDaoJDBC<MediaContentStatistics> implements MediaContentStatisticsDao
{

	public MediaContentStatisticsDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public MediaContentStatistics find(Long mediaContentId)
	{
		final MediaContentStatistics stats = new MediaContentStatistics();
		final Integer[] ratingBreakdown = {50, 30, 10, 10, 10};
		
		stats.setRate(5);
		stats.setRatingBreakdown(ratingBreakdown);
		stats.setLikes((long) 52);
		stats.setNolikes((long) 10);
		stats.setViews((long) 1234);
		
		return stats;
	}

	@Override
	protected MediaContentStatistics createFromResult(ResultSet result) throws SQLException
	{
		return null;
	}

}
