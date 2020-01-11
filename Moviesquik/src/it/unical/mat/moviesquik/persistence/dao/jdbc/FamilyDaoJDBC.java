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
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.FamilyDao;

/**
 * @author Agostino
 *
 */
public class FamilyDaoJDBC implements FamilyDao
{
	private static final String INSERT_STATEMENT = 
			"insert into Family(FamilyID, Name) values (?,?)";
	
	private static final String FIND_BY_PRIMARY_KEY_QUERY = 
			"select * from Family where FamilyID = ?";
	
	private static final String FIND_ALL_QUERY = 
			"select * from Family";
	
	private static final String UPDATE_STATEMENT = 
			"update Family SET Name = ? WHERE FamilyID = ?";
	
	private static final String DELETE_STATEMENT = 
			"delete from Family where FamilyID = ?";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public FamilyDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}

	@Override
	public boolean save(Family family)
	{
		try
		{
			family.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong  (1, family.getId());
			statement.setString(2, family.getName());
			
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
	public Family findByPrimaryKey(Long key)
	{
		Family family = null;
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PRIMARY_KEY_QUERY);
			statement.setLong  (1, key);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				family = createUserFromResult(result);
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return family;
	}

	@Override
	public List<Family> findAll()
	{
		final List<Family> families = new ArrayList<Family>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_QUERY);
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				families.add( createUserFromResult(result) );
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return families;
	}

	@Override
	public boolean update(Family family)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setString(1, family.getName());			
			statement.setLong  (2, family.getId());
			
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
	public boolean delete(Family family)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(DELETE_STATEMENT);
			statement.setLong (1, family.getId());
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
		
		return false;
	}
	
	private Family createUserFromResult( final ResultSet result ) throws SQLException
	{
		final Family family = new Family();
		
		family.setId(result.getLong("FamilyID"));
		family.setName(result.getString("Name"));
		
		family.setMembers(DBManager.getInstance().getDaoFactory().getUserDao().findByFamily(family));
		
		return family;
	}
	
}
