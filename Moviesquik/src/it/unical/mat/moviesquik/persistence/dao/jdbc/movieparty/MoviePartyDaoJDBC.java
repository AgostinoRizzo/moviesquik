/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.controller.movieparty.MoviePartySearchFilter;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
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
			"insert into movie_party(movie_party_id, name, description, start_date_time, date_time, is_private, user_id, media_content_id) values (?,?,?,?,?,?,?,?)";
	protected static final String FIND_ALL_QUERY   = "select * from movie_party order by start_date_time desc, date_time desc limit ? offset ?";
	protected static final String FIND_ALL_BY_USER_QUERY   = "select * from movie_party " + 
			"where movie_party_id in (select MP_INV.movie_party_id from movie_party_invitation as MP_INV where MP_INV.user_id = ?) or user_id = ? or not is_private " + 
			"order by start_date_time desc, date_time desc";
	protected static final String FIND_BY_ID_QUERY = "select * from movie_party " + 
			"where movie_party_id = ? and (movie_party_id in (select MP_INV.movie_party_id from movie_party_invitation as MP_INV where MP_INV.user_id = ?) or user_id = ? or not is_private)";
	
	protected static final String PLAYING_PARTY_CONDITION  = " start_date_time <= now() and (start_date_time + (\"getMediaContentStreamTime\"(movie_party_id) * interval '1 second')) > now() ";
	protected static final String UPCOMING_PARTY_CONDITION = " start_date_time > now() ";
	protected static final String EXPIRED_PARTY_CONDITION  = " (start_date_time + (\"getMediaContentStreamTime\"(movie_party_id) * interval '1 second')) <= now() ";
	protected static final String FIND_ALL_BY_USER_FILTER_QUERY = "with PARTIES as (" + FIND_ALL_BY_USER_QUERY + ") " +
																  "select * from ( " +
																  		"(select * from PARTIES where " + PLAYING_PARTY_CONDITION +
																  		"order by start_date_time desc, date_time desc) union all " + 
																  		"(select * from PARTIES where " + UPCOMING_PARTY_CONDITION +
																  		"order by start_date_time, date_time desc) union all " +
																  		"(select * from PARTIES where " + EXPIRED_PARTY_CONDITION +
																  		"order by start_date_time desc, date_time desc) " + 
																  ") as ALL_PARTIES ";
	protected static final String DATA_LIST_PAGE_QUERY = " limit ? offset ?";
	protected static final String COMMITMENT_CONSTRAINT_QUERY = " (? = user_id or ? in (select INV.user_id from movie_party_invitation as INV where INV.movie_party_id = ALL_PARTIES.movie_party_id and answer = 'P')) ";
	
	
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
			statement.setTimestamp(5, DateUtil.toJDBC(party.getCreationDateTime()));
			statement.setBoolean  (6, party.isPrivate());
			statement.setLong     (7, party.getAdministrator().getId());
			statement.setLong     (8, party.getMedia().getId());
			
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
	public List<MovieParty> findAll(DataListPage page)
	{
		final List<MovieParty> parties = new ArrayList<MovieParty>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_QUERY);
			
			statement.setInt(1, page.getLimit());
			statement.setInt(2, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				parties.add(createFromResult(result));
			
			return parties;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return parties; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<MovieParty> findAllByUser(User user, MoviePartySearchFilter filter, DataListPage page)
	{
		final List<MovieParty> parties = new ArrayList<MovieParty>();
		
		try
		{
			final StringBuilder query = new StringBuilder( FIND_ALL_BY_USER_FILTER_QUERY );
			query.append( getFilterConstraintQuery(filter, false) );
			query.append(DATA_LIST_PAGE_QUERY);
			
			final PreparedStatement statement = statementPrompter.prepareStatement(query.toString());
			
			statement.setLong(1, user.getId());
			statement.setLong(2, user.getId());
			statement.setInt (3, page.getLimit());
			statement.setInt (4, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				parties.add(createFromResult(result));
			
			return parties;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return parties; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<MovieParty> findCommitmentsByUser(User user, MoviePartySearchFilter filter, DataListPage page)
	{
		final List<MovieParty> parties = new ArrayList<MovieParty>();
		
		try
		{
			final StringBuilder query = new StringBuilder( FIND_ALL_BY_USER_FILTER_QUERY );
			query.append( getFilterConstraintQuery(filter, true) );
			query.append(DATA_LIST_PAGE_QUERY);
			
			final PreparedStatement statement = statementPrompter.prepareStatement(query.toString());
			
			statement.setLong(1, user.getId());
			statement.setLong(2, user.getId());
			statement.setLong(3, user.getId());
			statement.setLong(4, user.getId());
			statement.setInt (5, page.getLimit());
			statement.setInt (6, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				parties.add(createFromResult(result));
			
			return parties;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return parties; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public MovieParty findById(Long id, User user)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_ID_QUERY);
			
			statement.setLong(1, id);
			statement.setLong(2, user.getId());
			statement.setLong(3, user.getId());
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

	@Override
	protected MovieParty createFromResult(ResultSet result) throws SQLException
	{
		final MovieParty party = new MovieParty();
		
		party.setId( result.getLong("movie_party_id") );
		party.setName( result.getString("name") );
		party.setDescription( result.getString("description") );
		party.setStartDateTime( DateUtil.toJava(result.getTimestamp("start_date_time")) );
		party.setCreationDateTime( DateUtil.toJava(result.getTimestamp("date_time")) );
		party.setPrivate( result.getBoolean("is_private") );
		
		final Long mediaId = result.getLong("media_content_id");
		final Long adminId = result.getLong("user_id");
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		party.setMedia( daoFactory.getMediaContentDao().findById(mediaId) );
		party.setAdministrator( daoFactory.getUserDao().findByPrimaryKey(adminId) );
		
		party.setInvitations( daoFactory.getMoviePartyInvitationDao().findByMovieParty(party) );
		party.setParticipations( daoFactory.getMoviePartyParticipationDao().findByMovieParty(party) );
		
		return party;
	}
	
	private static String getFilterConstraintQuery( final MoviePartySearchFilter filter, final boolean commitmentsOnly )
	{
		String constraint = "";
		switch (filter) {
		case PUBLIC:   constraint = " where not is_private "; break;
		case PRIVATE:  constraint = " where is_private "; break;
		case PLAYING:  constraint = " where " + PLAYING_PARTY_CONDITION; break;
		case UPCOMING: constraint = " where " + UPCOMING_PARTY_CONDITION; break;
		case EXPIRED:  constraint = " where " + EXPIRED_PARTY_CONDITION; break;
		default: break;
		}
		
		if ( commitmentsOnly )
		{
			constraint += constraint.isEmpty() ? " where " : " and ";
			constraint += COMMITMENT_CONSTRAINT_QUERY;
		}
		
		return constraint;
	}

}
