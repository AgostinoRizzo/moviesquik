/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.UserDao;

/**
 * @author Agostino
 *
 */
public class UserDaoJDBC implements UserDao
{
	private static final String INSERT_STATEMENT = 
			"insert into User(UserID, FirstName, LastName, Email, Birthday, Gender, Password, FamilyID) values (?,?,?,?,?,?,?,?)";
	
	private static final String FIND_BY_PRIMARY_KEY_QUERY = 
			"select * from User where UserID = ?";
	
	private static final String FIND_ALL_QUERY = 
			"select * from User";
	
	private static final String UPDATE_STATEMENT = 
			"update User SET FirstName = ?, LastName = ?, Email = ?, Birthday = ?, Gender = ?, Password = ?, FamilyID = ? WHERE UserID = ?";
	
	private static final String DELETE_STATEMENT = 
			"delete from User where UserID = ?";
	
	private static final String FIND_BY_FAMILY_QUERY = 
			"select * from User where FamilyID = ?";

	private final StatementPrompterJDBC statementPrompter;
	
	public UserDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(User usr)
	{
		try
		{
			usr.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong  (1, usr.getId());
			statement.setString(2, usr.getFirstName());
			statement.setString(3, usr.getLastName());
			statement.setString(4, usr.getEmail());
			statement.setString(5, usr.getBirthday());
			statement.setString(6, usr.getGender());
			statement.setString(7, usr.getPassword());
			statement.setLong  (8, usr.getFamily().getId());
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return false;
	}

	@Override
	public User findByPrimaryKey(Long key)
	{
		User usr = null;
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PRIMARY_KEY_QUERY);
			statement.setLong  (1, key);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				usr = createUserFromResult(result, null);
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return usr;
	}

	@Override
	public List<User> findAll()
	{
		final List<User> users = new ArrayList<User>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_QUERY);
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				users.add( createUserFromResult(result, null) );
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return users;
	}

	@Override
	public boolean update(User usr)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setString(1, usr.getFirstName());
			statement.setString(2, usr.getLastName());
			statement.setString(3, usr.getEmail());
			statement.setString(4, usr.getBirthday());
			statement.setString(5, usr.getGender());
			statement.setString(6, usr.getPassword());
			statement.setLong  (7, usr.getFamily().getId());
			
			statement.setLong  (8, usr.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return false;
	}

	@Override
	public boolean delete(User usr)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(DELETE_STATEMENT);
			statement.setLong (1, usr.getId());
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return false;
	}
	
	@Override
	public List<User> findByFamily(Family family)
	{
		final List<User> users = new ArrayList<User>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_FAMILY_QUERY);
			statement.setLong  (1, family.getId());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				users.add( createUserFromResult(result, family) );
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return users;
	}
	
	private User createUserFromResult( final ResultSet result, final Family family ) throws SQLException
	{
		final User usr = new User();
		
		usr.setId(result.getLong("UserID"));
		usr.setFirstName(result.getString("FirstName"));
		usr.setLastName(result.getString("LastName"));
		usr.setEmail(result.getString("Email"));
		usr.setBirthday(result.getString("Birthday"));
		usr.setGender(result.getString("Gender"));
		usr.setPassword(result.getString("Password"));
		
		usr.setFamily( (family == null ) ? DBManager.getInstance().getDaoFactory().getFamilyDao()
				.findByPrimaryKey(result.getLong("FamilyID")) : family );
		
		return usr;
	}

}
