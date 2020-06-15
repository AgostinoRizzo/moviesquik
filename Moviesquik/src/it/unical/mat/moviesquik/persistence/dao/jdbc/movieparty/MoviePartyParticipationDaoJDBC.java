/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.movieparty.MoviePartyParticipation;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyParticipationDao;

/**
 * @author Agostino
 *
 */
public class MoviePartyParticipationDaoJDBC extends AbstractDaoJDBC<MoviePartyParticipation> implements MoviePartyParticipationDao
{
	protected static final String INSERT_STATEMENT = "insert into movie_party_participation(movie_party_id, user_id) values (?,?)";
	
	public MoviePartyParticipationDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean save(MoviePartyParticipation participation)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong     (1, participation.getParty().getId());
			statement.setLong     (2, participation.getParticipant().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MoviePartyParticipation createFromResult(ResultSet result) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
