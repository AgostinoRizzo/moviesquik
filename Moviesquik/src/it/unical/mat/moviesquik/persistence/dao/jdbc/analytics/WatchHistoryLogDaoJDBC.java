/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.analytics.WatchHistoryLog;
import it.unical.mat.moviesquik.persistence.dao.analytics.WatchHistoryLogDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.IdBroker;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class WatchHistoryLogDaoJDBC extends AbstractDaoJDBC<WatchHistoryLog> implements WatchHistoryLogDao
{
	protected static final String INSERT_STATEMENT = "INSERT INTO watch_history_log(watch_id, user_id, media_content_id, date_time) VALUES (?,?,?,?)";
	
	public WatchHistoryLogDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean log(WatchHistoryLog log)
	{
		try
		{
			log.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong(1, log.getId());
			statement.setLong(2, log.getSubject().getId());
			statement.setLong(3, log.getMediaContent().getId());
			statement.setDate(4, DateUtil.toDateJDBC(log.getWatchDateTime()));
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected WatchHistoryLog createFromResult(ResultSet result) throws SQLException
	{
		return null;
	}
	
}
