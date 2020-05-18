/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.Watchlist;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.WatchlistDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class WatchlistDaoJDBC extends AbstractDaoJDBC<Watchlist> implements WatchlistDao
{
	protected static final String INSERT_STATEMENT   = "insert into watchlist(watchlist_id, name, description, date_time, is_default, user_id) values (?,?,?,?,?,?)";
	protected static final String UPDATE_STATEMENT   = "update watchlist SET name = ?, description = ?, date_time = ?) WHERE watchlist_id = ?";
	protected static final String REMOVE_STATEMENT   = "delete from watchlist where watchlist_id = ?";
	protected static final String FIND_BY_USER_QUERY = "select * from watchlist where user_id = ? order by date_time asc";
	
	private User currentOwner = null;
	
	protected WatchlistDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean insert(Watchlist wl)
	{
		try
		{
			wl.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong     (1, wl.getId());
			statement.setString   (2, wl.getName());
			statement.setString   (3, wl.getDescription());
			statement.setTimestamp(4, DateUtil.toJDBC(wl.getDateTime()));
			statement.setBoolean  (5, wl.getIsDefault());
			statement.setLong     (6, wl.getOwner().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean update(Watchlist wl)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setString   (1, wl.getName());
			statement.setString   (2, wl.getDescription());
			statement.setTimestamp(3, DateUtil.toJDBC(wl.getDateTime()));
			statement.setLong     (4, wl.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean remove(Long id)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(REMOVE_STATEMENT);
			
			statement.setLong(1, id);
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<Watchlist> findByUser(User usr)
	{
		final List<Watchlist> watchlists = new ArrayList<Watchlist>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_USER_QUERY);
			
			statement.setLong(1, usr.getId());
			
			ResultSet result = statement.executeQuery();
			currentOwner = usr;
			
			while ( result.next() )
				watchlists.add(createFromResult(result));
			
			if ( watchlists.isEmpty() )
			{
				DBManager.getInstance().getDaoFactory().getWatchlistDao().insert(Watchlist.createNewWatchLaterWatchlist(usr));
				DBManager.getInstance().getDaoFactory().getWatchlistDao().insert(Watchlist.createNewFavoritesWatchlist(usr));
				watchlists.addAll(DBManager.getInstance().getDaoFactory().getWatchlistDao().findByUser(usr));
			}
			return watchlists;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return watchlists; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected Watchlist createFromResult(ResultSet result) throws SQLException
	{
		final Watchlist wl = new Watchlist();
		
		wl.setId(result.getLong("watchlist_id"));
		wl.setName(result.getString("name"));
		wl.setDescription(result.getString("description"));
		wl.setDateTime(DateUtil.toJava(result.getTimestamp("date_time")));
		wl.setIsDefault(result.getBoolean("is_default"));
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		
		final Long subjectUserId = result.getLong("user_id");
		
		if ( result.wasNull() )
			wl.setOwner(null);
		else if ( currentOwner != null )
			wl.setOwner(currentOwner);
		else
			wl.setOwner(daoFactory.getUserDao().findByPrimaryKey(subjectUserId));
		
		wl.setItems( daoFactory.getWatchlistItemDao().findAllByWatchlist(wl) );
		
		return wl;
	}

}
