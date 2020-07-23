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
import it.unical.mat.moviesquik.model.movieparty.MoviePartyParticipation;
import it.unical.mat.moviesquik.persistence.DBManager;
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
	protected static final String FIND_BY_PARTY_QUERY = "select * from movie_party_participation where movie_party_id = ?";
	
	private MovieParty party = null;
	
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
	public List<MoviePartyParticipation> findByMovieParty(MovieParty party)
	{
		final List<MoviePartyParticipation> participations = new ArrayList<MoviePartyParticipation>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PARTY_QUERY);
			
			statement.setLong(1, party.getId());
			
			ResultSet result = statement.executeQuery();
			this.party = party;
			
			while ( result.next() )
				participations.add(createFromResult(result));
			
			return participations;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return participations; }
		
		finally 
		{ statementPrompter.onFinalize(); }	
	}

	@Override
	protected MoviePartyParticipation createFromResult(ResultSet result) throws SQLException
	{
		final MoviePartyParticipation participation = new MoviePartyParticipation();
		final Long participantId = result.getLong("user_id");
		
		participation.setParty(party);
		participation.setParticipant( DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(participantId) );
		
		return participation;
	}

}
