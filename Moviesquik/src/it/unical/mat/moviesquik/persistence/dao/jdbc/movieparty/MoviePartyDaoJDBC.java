/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.IdBroker;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class MoviePartyDaoJDBC extends AbstractDaoJDBC<MovieParty> implements MoviePartyDao
{
	protected static final String INSERT_STATEMENT = 
			"insert into movie_party(movie_party_id, name, description, start_date_time, is_private, user_id, media_content_id) values (?,?,?,?,?,?,?)";

	public MoviePartyDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean save(MovieParty party)
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		try
		{
			party.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong     (1, party.getId());
			statement.setString   (2, party.getName());
			statement.setString   (3, party.getDescription());
			statement.setTimestamp(4, DateUtil.toJDBC(party.getStartDateTime()));
			statement.setBoolean  (5, party.isPrivate());
			statement.setLong     (6, party.getAdminstrator().getId());
			statement.setLong     (7, party.getMedia().getId());
			
			statement.executeUpdate();
			
			for( final MoviePartyInvitation invitation : party.getInvitations() )
				daoFactory.getMoviePartyInvitationDao().save(invitation);
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MovieParty createFromResult(ResultSet result) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
