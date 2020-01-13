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
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.FamilyDao;

/**
 * @author Agostino
 *
 */
public class FamilyDaoJDBC implements FamilyDao
{
	protected static final String INSERT_STATEMENT          = "insert into family(family_id, name, credit_card_id) values (?,?,?)";
	protected static final String FIND_BY_PRIMARY_KEY_QUERY = "select * from family where family_id = ?";
	protected static final String FIND_ALL_QUERY            = "select * from family";
	protected static final String UPDATE_STATEMENT          = "update family SET name = ? credit_card_id = ? WHERE family_id = ?";
	protected static final String DELETE_STATEMENT          = "delete from family where family_id = ?";
	
	protected final StatementPrompterJDBC statementPrompter;
	
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
			
			setDataToInsertStatement(family, statement);
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
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
			
			return family;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
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
			
			return families;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return families; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public boolean update(Family family)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setString(1, family.getName());
			statement.setString(2, family.getCreditCard().getNumber());
			statement.setLong  (3, family.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
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
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	protected static void setDataToInsertStatement( final Family family, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong  (1, family.getId());
		statement.setString(2, family.getName());
		statement.setLong(3, family.getCreditCard().getId());
	}
	
	private Family createUserFromResult( final ResultSet result ) throws SQLException
	{
		final Family family = new Family();
		
		family.setId(result.getLong("family_id"));
		family.setName(result.getString("name"));
		
		final DaoFactory factory = DBManager.getInstance().getDaoFactory();
		family.setMembers(factory.getUserDao().findByFamily(family));
		family.setCreditCard(factory.getCreditCardDao().findByPrimaryKey(result.getLong("credit_card_id")));
		
		return family;
	}
	
}
