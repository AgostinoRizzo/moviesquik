/**
 * 
 */
package it.unical.mat.moviesquik.persistence.searching;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.MediaContentType;
import it.unical.mat.moviesquik.model.MediaContentGroup;
import it.unical.mat.moviesquik.model.SearchResult;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
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
	
	public void searchMediaContentsByQuery( String query, final SortingPolicy sortingPolicy, final SearchResult result )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		query = clearQueryText(query);
		result.setQuery(query);
		
		final List<MediaContent> mediaContents = daoFactory.getMediaContentSearchDao().searchByTitle(query, false, sortingPolicy, MEDIA_CONTENTS_SEARCH_LIMIT);
		
		if ( mediaContents.size() < MEDIA_CONTENTS_SEARCH_LIMIT )
			mediaContents.addAll(daoFactory.getMediaContentSearchDao().searchByTitle(query, true, sortingPolicy, MEDIA_CONTENTS_SEARCH_LIMIT));
		
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
		return daoFactory.getMediaContentSearchDao().searchByType(type, sortingPolicy, FULL_MEDIA_CONTENTS_SEARCH_LIMIT, filter);
	}

	public Map<MediaContentGroup, List<MediaContent>> groupMediaContentSearch
		( final MediaContentType type, final String genre, final SortingPolicy sortingPolicy, 
				final User user, final MediaContentsSearchFilter filter  )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Map<MediaContentGroup, List<MediaContent>> groupMediaContents = new HashMap<MediaContentGroup, List<MediaContent>>();
		
		groupMediaContents.put(MediaContentGroup.TOP_RATED, daoFactory.getMediaContentSearchDao()
					.searchTopRated(type, sortingPolicy, GROUP_MEDIA_CONTENTS_SEARCH_LIMIT, filter));
		
		groupMediaContents.put(MediaContentGroup.MOST_POPULAR, daoFactory.getMediaContentSearchDao()
				.searchMostPopular(type, sortingPolicy, GROUP_MEDIA_CONTENTS_SEARCH_LIMIT, filter));
		
		groupMediaContents.put(MediaContentGroup.MOST_FAVORITES, daoFactory.getMediaContentSearchDao()
				.searchMostFavorites(type, sortingPolicy, GROUP_MEDIA_CONTENTS_SEARCH_LIMIT, filter));
		
		if ( user != null )
		{
			groupMediaContents.put(MediaContentGroup.SUGGESTED, daoFactory.getMediaContentSearchDao()
					.searchSuggested(type, user, sortingPolicy, GROUP_MEDIA_CONTENTS_SEARCH_LIMIT, filter));
			
			groupMediaContents.put(MediaContentGroup.RECENTLY_WATCHED, daoFactory.getMediaContentSearchDao()
					.searchRecentlyWatched(type, user, sortingPolicy, GROUP_MEDIA_CONTENTS_SEARCH_LIMIT, filter));
		}
		
		return groupMediaContents;
	}
	
	private static String clearQueryText( final String query )
	{
		return query.trim();
	}
	
}
