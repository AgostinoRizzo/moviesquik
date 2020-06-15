/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyInvitationDao;

/**
 * @author Agostino
 *
 */
public class MoviePartyInvitationDaoJDBC extends AbstractDaoJDBC<MoviePartyInvitation> implements MoviePartyInvitationDao
{
	protected static final String INSERT_STATEMENT = "insert into movie_party_invitation(movie_party_id, user_id) values (?,?)";
	
	public MoviePartyInvitationDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean save(MoviePartyInvitation invitation)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong     (1, invitation.getParty().getId());
			statement.setLong     (2, invitation.getGuest().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MoviePartyInvitation createFromResult(ResultSet result) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
