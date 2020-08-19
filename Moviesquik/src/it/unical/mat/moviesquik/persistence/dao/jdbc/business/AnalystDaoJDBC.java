/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.persistence.dao.business.AnalystDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class AnalystDaoJDBC extends AbstractDaoJDBC<Analyst> implements AnalystDao
{
	protected static final String FIND_BY_LOGIN_QUERY = "select * from analyst where email = ? and password = ?";
	
	public AnalystDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public Analyst findByLogin(String email, String password)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_LOGIN_QUERY);
			statement.setString(1, email);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createFromResult(result);
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null;}
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected Analyst createFromResult(ResultSet result) throws SQLException
	{
		final Analyst analyst = new Analyst();
		
		analyst.setId(result.getLong("analyst_id"));
		analyst.setEmail(result.getString("email"));
		analyst.setFirstName(result.getString("first_name"));
		analyst.setLastName(result.getString("last_name"));
		
		return analyst;
	}

}
