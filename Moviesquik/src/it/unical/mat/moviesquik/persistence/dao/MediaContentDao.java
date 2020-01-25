/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public interface MediaContentDao
{
	public boolean save( final MediaContent mediaContent );
	public boolean updateRatings( final MediaContent mediaContent );
	public List<MediaContent> findByTitle( final String title, final boolean weakSearch, final SortingPolicy sortingPolicy, final int limit);
	public List<MediaContent> findByTitleYear( final String title, final short year );
	public MediaContent getMediaContentOfTheDay();
	public List<MediaContent> findMostRated( final int maxfindCount );
	public List<MediaContent> findMostPopular( final int maxfindCount );
	public List<MediaContent> findMostFavorites( final int maxfindCount );
	public List<MediaContent> findSuggested( final int maxfindCount, final User user );
	public List<MediaContent> findRecentlyWatched( final int maxfindCount, final User user );
}
