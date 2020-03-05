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
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.PostDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class PostDaoJDBC extends AbstractDaoJDBC<Post> implements PostDao
{
	protected static final String INSERT_STATEMENT             = "insert into post(post_id, date_time, text, user_id) values (?,?,?,?)";
	protected static final String FIND_BY_USER_QUERY           = "select * from post where user_id = ? order by date_time desc limit ?";
	protected static final String FIND_BY_FOLLOWED_USERS_QUERY = "select * from post where user_id = ? or user_id in (" + 
																	UserDaoJDBC.FIND_FOLLOWED_IDS_QUERY + ") order by date_time desc limit ?";
	
	protected PostDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
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
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public List<Post> findByUser(User user, int limit)
	{
		final List<Post> posts = new ArrayList<Post>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_USER_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setInt(2, limit);
			
			ResultSet result = statement.executeQuery();
			
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
	public List<Post> findByFollowedUsers(User user, int limit)
	{
		final List<Post> posts = new ArrayList<Post>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_FOLLOWED_USERS_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setLong(2, user.getId());
			statement.setInt(3, limit);
			
			ResultSet result = statement.executeQuery();
			
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
		else
			post.setOwner(daoFactory.getUserDao().findByPrimaryKey(subjectUserId));
		
		return post;
	}
}
