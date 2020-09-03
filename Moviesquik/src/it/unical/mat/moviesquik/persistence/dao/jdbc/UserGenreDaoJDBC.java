/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.persistence.dao.UserGenreDao;

/**
 * @author Agostino
 *
 */
public class UserGenreDaoJDBC extends AbstractDaoJDBC<String> implements UserGenreDao
{
	protected static final String FIND_FAVORITE_QUERY = "SELECT * FROM user_genre_rates WHERE user_id = ? ORDER BY genre_rate DESC LIMIT 9";
	
	public UserGenreDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public List<String> findFavorites(User user)
	{
		final List<String> favoriteGenres = new ArrayList<String>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_FAVORITE_QUERY);
			
			statement.setLong(1, user.getId());
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				favoriteGenres.add( createFromResult(result) );
			return favoriteGenres;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return favoriteGenres; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected String createFromResult(ResultSet result) throws SQLException
	{
		return result.getString("genre");
	}

}
