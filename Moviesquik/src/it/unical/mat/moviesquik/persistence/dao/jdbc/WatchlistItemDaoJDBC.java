/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Watchlist;
import it.unical.mat.moviesquik.model.WatchlistItem;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.WatchlistItemDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class WatchlistItemDaoJDBC extends AbstractDaoJDBC<WatchlistItem> implements WatchlistItemDao
{
	protected static final String INSERT_STATEMENT             = "insert into watchlist_item(watchlist_id, media_content_id, date_time) select ?, ?, ? " +
																 "where not exists (select * from watchlist_item where watchlist_id = ? and media_content_id = ?)";
	protected static final String REMOVE_STATEMENT             = "delete from watchlist_item where watchlist_id = ? and media_content_id = ?";
	protected static final String FIND_ALL_BY_WATCHLIST_QUERY  = "select * from watchlist_item where watchlist_id = ? order by date_time desc";
	
	
	protected WatchlistItemDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}
	
	@Override
	public boolean insert(WatchlistItem item, Watchlist wl)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong        (1, wl.getId());
			statement.setLong        (2, item.getMediaContent().getId());
			statement.setTimestamp   (3, DateUtil.toTimestampJDBC(item.getDateTime()));
			statement.setLong        (4, wl.getId());
			statement.setLong        (5, item.getMediaContent().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean remove(Long mediaContentId, Long watchlistId)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(REMOVE_STATEMENT);
			
			statement.setLong        (1, watchlistId);
			statement.setLong        (2, mediaContentId);
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<WatchlistItem> findAllByWatchlist(Watchlist wl)
	{
		final List<WatchlistItem> items = new ArrayList<WatchlistItem>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_BY_WATCHLIST_QUERY);
			
			statement.setLong(1, wl.getId());
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				items.add(createFromResult(result));
			
			return items;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return items; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected WatchlistItem createFromResult(ResultSet result) throws SQLException
	{
		final WatchlistItem item = new WatchlistItem();
		
		item.setDateTime(DateUtil.toJava(result.getTimestamp("date_time")));
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Long mediaConentId = result.getLong("media_content_id");
		
		item.setMediaContent(daoFactory.getMediaContentDao().findById(mediaConentId));
		
		return item;
	}

}
