/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.accounting.Following;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.persistence.dao.FollowingDao;

/**
 * @author Agostino
 *
 */
public class FollowingDaoJDBC implements FollowingDao
{
	protected static final String INSERT_STATEMENT = "insert into following(follower_id, followed_id) values (?,?)";
	protected static final String FIND_BY_MEMBERS_QUERY = "select * from following where follower_id = ? and followed_id = ?";
		
	private final StatementPrompterJDBC statementPrompter;
	
	public FollowingDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(Following following)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong(1, following.getFollower().getId());
			statement.setLong(2, following.getFollowed().getId());
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public Following findByMembers(User follower, User followed)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_MEMBERS_QUERY);
			statement.setLong(1, follower.getId());
			statement.setLong(2, followed.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
			{
				final Following following = new Following();
				following.setFollower(follower);
				following.setFollowed(followed);
				
				return following;
			}
			
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
}
