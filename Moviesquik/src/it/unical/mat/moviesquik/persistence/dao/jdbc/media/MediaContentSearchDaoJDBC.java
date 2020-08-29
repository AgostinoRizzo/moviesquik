/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.analytics.AnalyticsFacade;
import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.MediaContentSearchDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.DaoUtilJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
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
	
	protected static final String ANONYMUS_SEARCH_FROM_WHERE_QUERY = " FROM media_content AS MC LEFT OUTER JOIN media_content_statistics AS MCSTAT ON MC.media_content_id = MCSTAT.media_content_id " + 
																	 " WHERE lower(type) like lower(concat('%', ?, '%'))";
	protected static final String ANONYMUS_SEARCH_QUERY = "select * " + ANONYMUS_SEARCH_FROM_WHERE_QUERY;
	
	protected static final String ANONYMUS_CATEGORY_SEARCH_QUERY     = "select MC.*, COALESCE(NULLIF(avg_rate, 0), rating * 5.0 / 10.0, 0) AS actual_rate, likes_count, nolikes_count, views_count " + 
																		ANONYMUS_SEARCH_FROM_WHERE_QUERY;
	
	protected static final String SEARCH_TOP_RATED_ORDER_BY_QUERY = " ORDER BY actual_rate DESC NULLS LAST, likes_count DESC NULLS LAST, nolikes_count ASC NULLS LAST, views_count DESC NULLS LAST";
	protected static final String SEARCH_MOST_POPULAR_ORDER_BY_QUERY = " order by views desc NULLS LAST";
	protected static final String SEARCH_MOST_FAVORITES_ORDER_BY_QUERY = " ORDER BY likes_count DESC NULLS LAST, nolikes_count ASC NULLS LAST, actual_rate DESC NULLS LAST, views_count DESC NULLS LAST";
	
	protected static final String SEARCH_RECENTLY_WATCHED_QUERY = "SELECT MC.* FROM watch_history_log AS WHLOG INNER JOIN media_content AS MC ON WHLOG.media_content_id = MC.media_content_id " + 
																  "WHERE WHLOG.user_id = ? ORDER BY WHLOG.date_time DESC";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public MediaContentSearchDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public List<MediaContent> searchByTitle(String title, boolean weakSearch, SortingPolicy sortingPolicy, DataListPage dataListPage)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = weakSearch ? SEARCH_BY_TITLE_WEAK_QUERY : SEARCH_BY_TITLE_QUERY;
		query = addQuerySortingPolicy(query, sortingPolicy, false);
		query = DaoUtilJDBC.addQueryOffsetLimit(query, dataListPage);
		
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
	public List<MediaContent> searchByType(MediaContentType type, SortingPolicy sortingPolicy, DataListPage dataListPage, MediaContentsSearchFilter filter)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = SEARCH_BY_TYPE_QUERY;
		query = addGenresFilter(query, filter, true);
		query = addQuerySortingPolicy(query, sortingPolicy, false);
		query = DaoUtilJDBC.addQueryOffsetLimit(query, dataListPage);
		
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
	public List<MediaContent> searchTopRated(MediaContentType type, SortingPolicy sortingPolicy, DataListPage dataListPage, MediaContentsSearchFilter filter)
	{
		return anonymusSearch(SEARCH_TOP_RATED_ORDER_BY_QUERY, type, sortingPolicy, dataListPage, filter, ANONYMUS_CATEGORY_SEARCH_QUERY);
	}
	
	@Override
	public List<MediaContent> searchTrendingNow(MediaContentType type, SortingPolicy sortingPolicy, DataListPage dataListPage,
			MediaContentsSearchFilter filter)
	{
		return AnalyticsFacade.getTrendingNowMediaContents(dataListPage);
	}
	
	@Override
	public List<MediaContent> searchMostPopular(MediaContentType type, SortingPolicy sortingPolicy, DataListPage dataListPage, MediaContentsSearchFilter filter)
	{
		return AnalyticsFacade.getMostPopularMediaContents(dataListPage);
	}
	
	@Override
	public List<MediaContent> searchMostFavorites(MediaContentType type, SortingPolicy sortingPolicy, DataListPage dataListPage, MediaContentsSearchFilter filter)
	{
		return anonymusSearch(SEARCH_MOST_FAVORITES_ORDER_BY_QUERY, type, sortingPolicy, dataListPage, filter, ANONYMUS_CATEGORY_SEARCH_QUERY);
	}
	
	@Override
	public List<MediaContent> searchSuggested(MediaContentType type, User user,
			SortingPolicy sortingPolicy, DataListPage dataListPage, MediaContentsSearchFilter filter)
	{
		return AnalyticsFacade.getSuggestedMediaContents(user.getId(), dataListPage);
	}
	
	@Override
	public List<MediaContent> searchRecentlyWatched(MediaContentType type, User user, 
			SortingPolicy sortingPolicy, DataListPage dataListPage, MediaContentsSearchFilter filter)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = SEARCH_RECENTLY_WATCHED_QUERY;
		query = DaoUtilJDBC.addQueryOffsetLimit(query, dataListPage);
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(query);
			statement.setLong(1, user.getId());
			
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
	
	private List<MediaContent> anonymusSearch( final String orderByQuery, final MediaContentType type, final SortingPolicy sortingPolicy, 
												final DataListPage dataListPage, final MediaContentsSearchFilter filter, final String anonymusSearchQuery )
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		String query = anonymusSearchQuery;
		query = addGenresFilter(query, filter, true);
		query = query + orderByQuery;
		query = addQuerySortingPolicy(query, sortingPolicy, true);
		query = DaoUtilJDBC.addQueryOffsetLimit(query, dataListPage);
		
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
