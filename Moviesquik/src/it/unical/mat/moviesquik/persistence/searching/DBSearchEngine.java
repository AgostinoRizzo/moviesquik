/**
 * 
 */
package it.unical.mat.moviesquik.persistence.searching;

import java.util.List;
import java.util.stream.Collectors;

import it.unical.mat.moviesquik.model.MediaContent;
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
	
	public void searchMediaContentsByQuery( final String query, final SortingPolicy sortingPolicy, final SearchResult result )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		clearQueryText(query);
		result.setQuery(query);
		
		final List<MediaContent> mediaContents = daoFactory.getMediaContentDao().findByTitle(query, false, sortingPolicy, MEDIA_CONTENTS_SEARCH_LIMIT);
		
		if ( mediaContents.size() < MEDIA_CONTENTS_SEARCH_LIMIT )
			mediaContents.addAll(daoFactory.getMediaContentDao().findByTitle(query, true, sortingPolicy, MEDIA_CONTENTS_SEARCH_LIMIT));
		
		result.setContents(mediaContents.stream()
				.distinct()
				.limit(MEDIA_CONTENTS_SEARCH_LIMIT)
				.collect(Collectors.toList()));
	}
	
	public void searchUsersByQuery( final String query, final SearchResult result )
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		clearQueryText(query);
		result.setQuery(query);
		
		final List<User> users = daoFactory.getUserDao().findByName(query, MEDIA_CONTENTS_SEARCH_LIMIT);

		result.setUsers(users.stream()
				.distinct()
				.limit(MEDIA_CONTENTS_SEARCH_LIMIT)
				.collect(Collectors.toList()));
	}
	
	private static void clearQueryText( final String query )
	{
		query.trim();
	}
	
}
