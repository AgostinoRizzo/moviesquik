/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;
import it.unical.mat.moviesquik.persistence.DBManager;
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
	protected static final String FIND_BY_PARTY_QUERY = "select * from movie_party_invitation where movie_party_id = ?";
	
	private MovieParty party = null;
	
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
	public List<MoviePartyInvitation> findByMovieParty(MovieParty party)
	{
		final List<MoviePartyInvitation> invitations = new ArrayList<MoviePartyInvitation>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PARTY_QUERY);
			
			statement.setLong(1, party.getId());
			
			ResultSet result = statement.executeQuery();
			this.party = party;
			
			while ( result.next() )
				invitations.add(createFromResult(result));
			
			return invitations;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return invitations; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MoviePartyInvitation createFromResult(ResultSet result) throws SQLException
	{
		final MoviePartyInvitation invitation = new MoviePartyInvitation();
		final Long guestId = result.getLong("user_id");
		
		invitation.setParty(party);
		invitation.setGuest( DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(guestId) );
		
		return invitation;
	}

}
