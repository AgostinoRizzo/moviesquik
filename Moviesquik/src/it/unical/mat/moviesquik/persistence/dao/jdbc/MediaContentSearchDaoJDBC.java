/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.MediaContentType;
import it.unical.mat.moviesquik.model.User;
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
	
	protected static final String ANONYMUS_SEARCH_QUERY = "select * from media_content where lower(type) like lower(concat('%', ?, '%'))";
	protected static final String SEARCH_TOP_RATED_ORDER_BY_QUERY = " order by rating desc";
	protected static final String SEARCH_MOST_POPULAR_ORDER_BY_QUERY = " order by views desc";
	protected static final String SEARCH_MOST_FAVORITES_ORDER_BY_QUERY = " order by likes desc";
	
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
		query = addQuerySortingPolicy(query, sortingPolicy, false);
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
	public List<MediaContent> searchByType(MediaContentType type, SortingPolicy sortingPolicy, int limit, MediaContentsSearchFilter filter)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = SEARCH_BY_TYPE_QUERY;
		query = addGenresFilter(query, filter, true);
		query = addQuerySortingPolicy(query, sortingPolicy, false);
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
	public List<MediaContent> searchTopRated(MediaContentType type, SortingPolicy sortingPolicy, int limit, MediaContentsSearchFilter filter)
	{
		return anonymusSearch(SEARCH_TOP_RATED_ORDER_BY_QUERY, type, sortingPolicy, limit, filter);
	}
	
	@Override
	public List<MediaContent> searchMostPopular(MediaContentType type, SortingPolicy sortingPolicy, int limit, MediaContentsSearchFilter filter)
	{
		return anonymusSearch(SEARCH_MOST_POPULAR_ORDER_BY_QUERY, type, sortingPolicy, limit, filter);
	}
	
	@Override
	public List<MediaContent> searchMostFavorites(MediaContentType type, SortingPolicy sortingPolicy, int limit, MediaContentsSearchFilter filter)
	{
		return anonymusSearch(SEARCH_MOST_FAVORITES_ORDER_BY_QUERY, type, sortingPolicy, limit, filter);
	}
	
	@Override
	public List<MediaContent> searchSuggested(MediaContentType type, User user,
			SortingPolicy sortingPolicy, int limit, MediaContentsSearchFilter filter)
	{
		// TODO Auto-generated method stub
		return searchTopRated(type, sortingPolicy, limit, filter);
	}
	
	@Override
	public List<MediaContent> searchRecentlyWatched(MediaContentType type, User user, 
			SortingPolicy sortingPolicy, int limit, MediaContentsSearchFilter filter)
	{
		// TODO Auto-generated method stub
		return searchTopRated(type, sortingPolicy, limit, filter);
	}
	
	private List<MediaContent> anonymusSearch( final String orderByQuery, final MediaContentType type, final SortingPolicy sortingPolicy, 
												final int limit, final MediaContentsSearchFilter filter)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = ANONYMUS_SEARCH_QUERY;
		query = addGenresFilter(query, filter, true);
		query = query + orderByQuery;
		query = addQuerySortingPolicy(query, sortingPolicy, true);
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
	
	private static String addQuerySortingPolicy( final String query, final SortingPolicy policy, final boolean append )
	{
		String orderBy;
		
		switch ( policy )
		{
		case YEAR_RELEASED: orderBy = "year desc";  break;
		case TITLE_ASC:     orderBy = "title asc";  break;
		case TITLE_DESC:    orderBy = "title desc"; break;
		default: return query;
		}
		
		return (append) ? query + ", " + orderBy : query + " order by " + orderBy;
	}
	
	private static String addGenresFilter( final String query, final MediaContentsSearchFilter filter, final boolean append )
	{
		final StringBuilder whereConditions = new StringBuilder();
		boolean first = true;
		for ( final String genre : filter.getGenres() )
		{
			whereConditions.append( first ? "(" : " or " );
			whereConditions.append("lower(genre) like lower('%" + genre + "%')");
			first = false;
		}
		
		if ( whereConditions.length() > 0 )
		{
			whereConditions.append(") ");
			return (append) ? query + " and " + whereConditions.toString() 
							: query + " where " + whereConditions.toString();
		}
		return query;
	}

}
