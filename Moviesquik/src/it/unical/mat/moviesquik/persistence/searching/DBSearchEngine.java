/**
 * 
 */
package it.unical.mat.moviesquik.persistence.searching;

import java.util.List;
import java.util.stream.Collectors;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.SearchResult;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class DBSearchEngine
{
	public static final int SEARCH_LIMIT = 12;
	
	public SearchResult searchByQuery( final String query, final SortingPolicy sortingPolicy )
	{
		final SearchResult result = new SearchResult();
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		clearQueryText(query);
		result.setQuery(query);
		
		final List<MediaContent> mediaContents = daoFactory.getMediaContentDao().findByTitle(query, false, sortingPolicy, SEARCH_LIMIT);
		
		if ( mediaContents.size() < SEARCH_LIMIT )
			mediaContents.addAll(daoFactory.getMediaContentDao().findByTitle(query, true, sortingPolicy, SEARCH_LIMIT));
		
		result.setContents(mediaContents.stream()
				.distinct()
				.collect(Collectors.toList()));
		
		return result;
	}
	
	private static void clearQueryText( final String query )
	{
		query.trim();
	}
	
}
