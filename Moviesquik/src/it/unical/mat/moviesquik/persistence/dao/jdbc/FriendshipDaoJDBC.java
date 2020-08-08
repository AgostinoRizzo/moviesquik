/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.unical.mat.moviesquik.model.Friendship;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.FriendshipDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class FriendshipDaoJDBC implements FriendshipDao
{
	protected static final String INSERT_STATEMENT = "insert into friendship(friendship_id, start_date, a_user_id, b_user_id, a_for_applicant) values (?,?,?,?,?)";
	protected static final String UPDATE_STATEMENT = "update friendship SET start_date = ?, a_user_id = ?, b_user_id = ?, a_for_applicant = ? WHERE friendship_id = ?";
	protected static final String FIND_BY_MEMBERS_QUERY = "select * from friendship where (a_user_id = ? and b_user_id = ?) or (a_user_id = ? and b_user_id = ?) ";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public FriendshipDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(Friendship friendship)
	{
		try
		{
			friendship.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			setDataToInsertStatement(friendship, statement);
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean update(Friendship friendship)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setTimestamp   (1 , DateUtil.toTimestampJDBC(friendship.getStartDate()));
			statement.setLong        (2, friendship.getFirstUser().getId());
			statement.setLong        (3, friendship.getSecondUser().getId());
			
			if ( friendship.getFirstForApplicant() == null )
				statement.setNull(4, Types.BOOLEAN);
			else
				statement.setBoolean(4, friendship.getFirstForApplicant());
			
			statement.setLong   (5, friendship.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public Friendship findByMembers(User a_user, User b_user)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_MEMBERS_QUERY);
			statement.setLong(1, a_user.getId());
			statement.setLong(2, b_user.getId());
			statement.setLong(3, b_user.getId());
			statement.setLong(4, a_user.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createFromResult(result);
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	protected static void setDataToInsertStatement( final Friendship friendship, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong        (1 , friendship.getId());
		statement.setTimestamp   (2 , DateUtil.toTimestampJDBC(friendship.getStartDate()));
		statement.setLong        (3 , friendship.getFirstUser().getId());
		statement.setLong        (4 , friendship.getSecondUser().getId());
		statement.setBoolean     (5 , friendship.getFirstForApplicant());
	}
	
	private Friendship createFromResult( final ResultSet result ) throws SQLException
	{
		final Friendship friendship = new Friendship();
		
		friendship.setId(result.getLong("friendship_id"));
		friendship.setStartDate(DateUtil.toJava(result.getTimestamp("start_date")));
		
		final Long firstUserId = result.getLong("a_user_id");
		final Long secondUserId = result.getLong("b_user_id");
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		friendship.setFirstUser( daoFactory.getUserDao().findByPrimaryKey(firstUserId) );
		friendship.setSecondUser( daoFactory.getUserDao().findByPrimaryKey(secondUserId) );
		
		friendship.setFirstForApplicant(result.getBoolean("a_for_applicant"));
		if ( result.wasNull() )
			friendship.setFirstForApplicant(null);
		
		return friendship;
	}
}
