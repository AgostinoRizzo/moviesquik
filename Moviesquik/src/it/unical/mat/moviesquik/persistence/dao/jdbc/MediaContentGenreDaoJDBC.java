/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.unical.mat.moviesquik.persistence.dao.MediaContentGenreDao;

/**
 * @author Agostino
 *
 */
public class MediaContentGenreDaoJDBC implements MediaContentGenreDao
{
	protected static final String FIND_ALL_QUERY = "select genre as genre from media_content";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public MediaContentGenreDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public List<String> findAll()
	{
		final Set<String> genres = new HashSet<String>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_QUERY);
			ResultSet result = statement.executeQuery();
			
			String item;
			String[] items;
			
			while ( result.next() )
			{
				item = result.getString("genre");
				items = item.split(", ");
				
				for ( final String i : items )
					genres.add(i);
			}
			
			return genres.stream().sorted().collect(Collectors.toList());
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return new ArrayList<String>(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

}
