/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public interface MediaContentSearchDao
{
	public List<MediaContent> searchByTitle( final String title, final boolean weakSearch, final SortingPolicy sortingPolicy, final DataListPage dataListPage);
	public List<MediaContent> searchByType
		( final MediaContentType type, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchTopRated
		( final MediaContentType type, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchTrendingNow
		( final MediaContentType type, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchMostPopular
		( final MediaContentType type, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchMostFavorites
		( final MediaContentType type, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchSuggested
		( final MediaContentType type, final User user, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
	public List<MediaContent> searchRecentlyWatched
		( final MediaContentType type, final User user, final SortingPolicy sortingPolicy, final DataListPage dataListPage, final MediaContentsSearchFilter filter );
}
