/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.persistence.dao.analytics.MediaStatisticLogDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class MediaStatisticLogDaoJDBC extends AbstractDaoJDBC<MediaStatisticLogDaoJDBC> implements MediaStatisticLogDao
{
	protected static final String LOG_HIT_STATEMENT        = "SELECT \"logMediaStatisticHit\"(?, ?)";
	protected static final String LOG_SCROLL_STATEMENT     = "SELECT \"logMediaStatisticScroll\"(?, ?)";
	protected static final String LOG_SPENT_TIME_STATEMENT = "SELECT \"logMediaStatisticSpentTime\"(?, ?, ?)";
	
	public MediaStatisticLogDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean logHit(Long userId, Long mediaContentId)
	{
		return simpleLog(LOG_HIT_STATEMENT, userId, mediaContentId);
	}

	@Override
	public boolean logScroll(Long userId, Long mediaContentId)
	{
		return simpleLog(LOG_SCROLL_STATEMENT, userId, mediaContentId);
	}

	@Override
	public boolean logSpentTime(Long userId, Long mediaContentId, Integer time)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(LOG_SPENT_TIME_STATEMENT);
			
			statement.setLong(1, userId);
			statement.setLong(2, mediaContentId);
			statement.setInt (3, time);
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MediaStatisticLogDaoJDBC createFromResult(ResultSet result) throws SQLException
	{
		return null;
	}
	
	private boolean simpleLog( final String sqlStatement, final Long userId, final Long mediaContentId )
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(sqlStatement);
			
			statement.setLong(1, userId);
			statement.setLong(2, mediaContentId);
			
			statement.execute();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
}
