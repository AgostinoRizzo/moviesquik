/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.MediaContentType;
import it.unical.mat.moviesquik.persistence.dao.MediaContentSearchDao;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class MediaContentSearchDaoJDBC implements MediaContentSearchDao
{
	protected static final String SEARCH_BY_TITLE_QUERY = "select * from media_content where lower(title) = lower(?)";
	protected static final String SEARCH_BY_TITLE_WEAK_QUERY = "select * from media_content where lower(title) like lower(concat('%', ?, '%'))";
	protected static final String SEARCH_BY_TYPE_QUERY = "select * from media_content where lower(type) like lower(concat('%', ?, '%'))";
	protected static final String SEARCH_TOP_RATED_QUERY = "select * from media_content where lower(type) like lower(concat('%', ?, '%')) order by rating desc";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public MediaContentSearchDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public List<MediaContent> searchByTitle(String title, boolean weakSearch, SortingPolicy sortingPolicy, int limit)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = weakSearch ? SEARCH_BY_TITLE_WEAK_QUERY : SEARCH_BY_TITLE_QUERY;
		query = addQuerySortingPolicy(query, sortingPolicy);
		query = DaoUtilJDBC.addQueryLimit(query, limit);
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(query);
			statement.setString(1, title);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				mediaContents.add( MediaContentDaoJDBC.createFromResult(result) );
			return mediaContents;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return mediaContents; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public List<MediaContent> searchByType(MediaContentType type, SortingPolicy sortingPolicy, int limit)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = SEARCH_BY_TYPE_QUERY;
		query = addQuerySortingPolicy(query, sortingPolicy);
		query = DaoUtilJDBC.addQueryLimit(query, limit);
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(query);
			statement.setString(1, type.toString());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				mediaContents.add( MediaContentDaoJDBC.createFromResult(result) );
			return mediaContents;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return mediaContents; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<MediaContent> searchTopRated(MediaContentType type, SortingPolicy sortingPolicy, int limit)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = SEARCH_TOP_RATED_QUERY;
		query = addQuerySortingPolicy(query, sortingPolicy);
		query = DaoUtilJDBC.addQueryLimit(query, limit);
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(query);
			statement.setString(1, type.toString());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				mediaContents.add( MediaContentDaoJDBC.createFromResult(result) );
			return mediaContents;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return mediaContents; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<MediaContent> searchMostPopular(MediaContentType type, SortingPolicy sortingPolicy, int limit)
	{
		// TODO Auto-generated method stub
		return searchTopRated(type, sortingPolicy, limit);
	}
	
	@Override
	public List<MediaContent> searchMostFavorites(MediaContentType type, SortingPolicy sortingPolicy, int limit)
	{
		// TODO Auto-generated method stub
		return searchTopRated(type, sortingPolicy, limit);
	}
	
	@Override
	public List<MediaContent> searchSuggested(MediaContentType type, it.unical.mat.moviesquik.model.User user,
			SortingPolicy sortingPolicy, int limit)
	{
		// TODO Auto-generated method stub
		return searchTopRated(type, sortingPolicy, limit);
	}
	
	@Override
	public List<MediaContent> searchRecentlyWatched(MediaContentType type,
			it.unical.mat.moviesquik.model.User user, SortingPolicy sortingPolicy, int limit)
	{
		// TODO Auto-generated method stub
		return searchTopRated(type, sortingPolicy, limit);
	}
	
	private static String addQuerySortingPolicy( final String query, final SortingPolicy policy )
	{
		String orderBy;
		
		switch ( policy )
		{
		case YEAR_RELEASED: orderBy = "year desc";  break;
		case TITLE_ASC:     orderBy = "title asc";  break;
		case TITLE_DESC:    orderBy = "title desc"; break;
		default: return query;
		}
		
		return query + " order by " + orderBy;
	}

}
