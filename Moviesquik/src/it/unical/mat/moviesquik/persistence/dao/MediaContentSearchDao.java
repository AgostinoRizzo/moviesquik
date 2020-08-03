/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public interface MediaContentSearchDao
{
	public List<MediaContent> searchByTitle( final String title, final boolean weakSearch, final SortingPolicy sortingPolicy, final int limit);
	public List<MediaContent> searchByType
		( final MediaContentType type, final SortingPolicy sortingPolicy, final int limit, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchTopRated
		( final MediaContentType type, final SortingPolicy sortingPolicy, final int limit, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchMostPopular
		( final MediaContentType type, final SortingPolicy sortingPolicy, final int limit, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchMostFavorites
		( final MediaContentType type, final SortingPolicy sortingPolicy, final int limit, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchSuggested
		( final MediaContentType type, final User user, final SortingPolicy sortingPolicy, final int limit, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchRecentlyWatched
		( final MediaContentType type, final User user, final SortingPolicy sortingPolicy, final int limit, final MediaContentsSearchFilter filter );
}
