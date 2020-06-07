/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.Watchlist;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.PostDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class PostDaoJDBC extends AbstractDaoJDBC<Post> implements PostDao
{
	protected static final String INSERT_STATEMENT             = "insert into post(post_id, date_time, text, user_id, watchlist_id) values (?,?,?,?,?)";
	protected static final String FIND_BY_ID_QUERY             = "select * from post where post_id = ?";
	protected static final String FIND_BY_USER_QUERY           = "select * from post where user_id = ? order by date_time desc limit ? offset ?";
	protected static final String FIND_BY_FOLLOWED_USERS_QUERY = "select * from post where user_id = ? or user_id in (" + 
																	UserDaoJDBC.FIND_FOLLOWED_IDS_QUERY + ") order by date_time desc limit ? offset ?";
	
	protected static final String FIND_LIKES_LOVES_COUNTERS_BY_ID_QUERY    = "select count(distinct f1.feedback_id) as n_likes, count(distinct f2.feedback_id) as n_loves \r\n" + 
																			 "from post_feedback as f1 full outer join post_feedback as f2 on (f1.user_id = f2.user_id and f1.post_id = f2.post_id and f1.is_like != f2.is_like)\r\n" + 
																			 "where (f1.post_id = ? or f2.post_id = ?) and ( (f1.is_like is NULL or f1.is_like) and (f2.is_like is NULL or not f2.is_like))";
	protected static final String FIND_COMMENTS_COUNTERS_BY_ID_QUERY       = "select count(\"comment\".comment_id) as n_comments\r\n" + 
																			 "from \"comment\"\r\n" + 
																			 "where post_id = ?";
	
	private User currentOwner = null;
	
	protected PostDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}
	
	@Override
	public Post findById(Long id)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_ID_QUERY);
			
			statement.setLong(1, id);
			
			ResultSet result = statement.executeQuery();
			currentOwner = null;
			
			if ( result.next() )
				return createFromResult(result);
			
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean save(Post post)
	{
		try
		{
			post.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong        (1, post.getId());
			statement.setTimestamp   (2, DateUtil.toJDBC(post.getDateTime()));
			statement.setString      (3, post.getText());
			statement.setLong        (4, post.getOwner().getId());
			
			final Watchlist wl = post.getWatchlist();
			statement.setLong        (5, wl == null ? null : wl.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public List<Post> findByUser(User user, DataListPage page)
	{
		final List<Post> posts = new ArrayList<Post>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_USER_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setInt(2, page.getLimit());
			statement.setInt(3, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			currentOwner = user;
			
			while ( result.next() )
				posts.add(createFromResult(result));
			
			return posts;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return posts; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<Post> findByFollowedUsers(User user, DataListPage page)
	{
		final List<Post> posts = new ArrayList<Post>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_FOLLOWED_USERS_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setLong(2, user.getId());
			statement.setInt(3, page.getLimit());
			statement.setInt(4, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			currentOwner = user;
			
			while ( result.next() )
				posts.add(createFromResult(result));
			
			return posts;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return posts; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected Post createFromResult(ResultSet result) throws SQLException
	{
		final Post post = new Post();
		
		post.setId(result.getLong("post_id"));
		post.setDateTime(DateUtil.toJava(result.getTimestamp("date_time")));
		post.setText(result.getString("text"));
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Long subjectUserId = result.getLong("user_id");
		
		if ( result.wasNull() )
			post.setOwner(null);
		else if ( currentOwner != null )
			post.setOwner(currentOwner);
		else
			post.setOwner(daoFactory.getUserDao().findByPrimaryKey(subjectUserId));
		
		final Long watchlistId = result.getLong("watchlist_id");
		if ( result.wasNull() )
			post.setWatchlist(null);
		else
			post.setWatchlist(daoFactory.getWatchlistDao().findByUser(post.getOwner(), watchlistId));
		
		addPostCounters(post);
		
		return post;
	}
	
	private void addPostCounters( final Post post )
	{
		final Long post_id = post.getId();
		try
		{
			// likes and loves counters
			PreparedStatement statement = statementPrompter.prepareStatement(FIND_LIKES_LOVES_COUNTERS_BY_ID_QUERY);
			statement.setLong(1, post_id);
			statement.setLong(2, post_id);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
			{
				post.setNumLikes(result.getLong("n_likes"));
				post.setNumLoves(result.getLong("n_loves"));
			}
			
			// comments counters
			statement = statementPrompter.prepareStatement(FIND_COMMENTS_COUNTERS_BY_ID_QUERY);
			statement.setLong(1, post_id);
			
			result = statement.executeQuery();
			
			if ( result.next() )
				post.setNumAllComments(result.getLong("n_comments"));
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
}
