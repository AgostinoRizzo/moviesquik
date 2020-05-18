/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Watchlist;
import it.unical.mat.moviesquik.model.WatchlistItem;

/**
 * @author Agostino
 *
 */
public interface WatchlistItemDao
{
	public boolean insert( final WatchlistItem item, final Watchlist wl );
	public boolean remove( final Long mediaContentId, final Long watchlistId );
	public List<WatchlistItem> findAllByWatchlist( final Watchlist wl );
}
