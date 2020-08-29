/**
 * 
 */
package it.unical.mat.moviesquik.persistence.searching;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.SearchResult;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentGroup;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class DBSearchEngine
{
	public static final int MEDIA_CONTENTS_SEARCH_LIMIT = 12;
	public static final int USERS_SEARCH_LIMIT = 12;
	public static final int FULL_MEDIA_CONTENTS_SEARCH_LIMIT = 12;
	public static final int GROUP_MEDIA_CONTENTS_SEARCH_LIMIT = 6;
	
	public static final DataListPage SEARCH_DATA_LIST_PAGE = new DataListPage(MEDIA_CONTENTS_SEARCH_LIMIT);
	public static final DataListPage GROUP_SEARCH_DATA_LIST_PAGE = new DataListPage(GROUP_MEDIA_CONTENTS_SEARCH_LIMIT);
	public static final DataListPage FULL_SEARCH_DATA_LIST_PAGE = new DataListPage(FULL_MEDIA_CONTENTS_SEARCH_LIMIT);
	
	public void searchMediaContentsByQuery( String query, final SortingPolicy sortingPolicy, final SearchResult result )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		query = clearQueryText(query);
		result.setQuery(query);
		
		final List<MediaContent> mediaContents = daoFactory.getMediaContentSearchDao().searchByTitle(query, false, sortingPolicy, SEARCH_DATA_LIST_PAGE);
		
		if ( mediaContents.size() < MEDIA_CONTENTS_SEARCH_LIMIT )
			mediaContents.addAll(daoFactory.getMediaContentSearchDao().searchByTitle(query, true, sortingPolicy, SEARCH_DATA_LIST_PAGE));
		
		result.setContents(mediaContents.stream()
				.distinct()
				.limit(MEDIA_CONTENTS_SEARCH_LIMIT)
				.collect(Collectors.toList()));
	}
	
	public void searchUsersByQuery( String query, final SearchResult result )
	{
		searchUsersByQuery(query, result, null);
	}
	
	public void searchUsersByQuery( String query, final SearchResult result, final User usr )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		query = clearQueryText(query);
		result.setQuery(query);
		
		final List<User> users = usr == null 
									? daoFactory.getUserDao().findByName(query, MEDIA_CONTENTS_SEARCH_LIMIT)
									: daoFactory.getUserDao().findFriendsByName(query, usr, MEDIA_CONTENTS_SEARCH_LIMIT);

		result.setUsers(users.stream()
				.distinct()
				.limit(MEDIA_CONTENTS_SEARCH_LIMIT)
				.collect(Collectors.toList()));
	}
	
	public List<MediaContent> searchTopRatedMediaContents()
	{
		return null;
	}
	
	public List<MediaContent> fullMediaContentSearch
		( final MediaContentType type, final String genre, final SortingPolicy sortingPolicy, 
				final User user, final MediaContentsSearchFilter filter )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		return daoFactory.getMediaContentSearchDao().searchByType(type, sortingPolicy, FULL_SEARCH_DATA_LIST_PAGE, filter);
	}

	public Map<MediaContentGroup, List<MediaContent>> groupMediaContentSearch
		( final MediaContentType type, final String genre, final SortingPolicy sortingPolicy, 
				final User user, final MediaContentsSearchFilter filter  )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Map<MediaContentGroup, List<MediaContent>> groupMediaContents = new HashMap<MediaContentGroup, List<MediaContent>>();
		
		groupMediaContents.put(MediaContentGroup.TOP_RATED, daoFactory.getMediaContentSearchDao()
					.searchTopRated(type, sortingPolicy, GROUP_SEARCH_DATA_LIST_PAGE, filter));
		
		groupMediaContents.put(MediaContentGroup.MOST_POPULAR, daoFactory.getMediaContentSearchDao()
				.searchMostPopular(type, sortingPolicy, GROUP_SEARCH_DATA_LIST_PAGE, filter));
		
		groupMediaContents.put(MediaContentGroup.MOST_FAVORITES, daoFactory.getMediaContentSearchDao()
				.searchMostFavorites(type, sortingPolicy, GROUP_SEARCH_DATA_LIST_PAGE, filter));
		
		if ( user != null )
		{
			groupMediaContents.put(MediaContentGroup.SUGGESTED, daoFactory.getMediaContentSearchDao()
					.searchSuggested(type, user, sortingPolicy, GROUP_SEARCH_DATA_LIST_PAGE, filter));
			
			groupMediaContents.put(MediaContentGroup.RECENTLY_WATCHED, daoFactory.getMediaContentSearchDao()
					.searchRecentlyWatched(type, user, sortingPolicy, GROUP_SEARCH_DATA_LIST_PAGE, filter));
		}
		
		return groupMediaContents;
	}
	
	private static String clearQueryText( final String query )
	{
		return query.trim();
	}
	
}
