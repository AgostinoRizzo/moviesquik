/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.watchlist.Watchlist;

/**
 * @author Agostino
 *
 */
public interface WatchlistDao
{
	public boolean insert( final Watchlist wl );
	public boolean update( final Watchlist wl );
	public boolean remove( final Long id );
	public Watchlist findById( final Long id );
	public List<Watchlist> findByUser( final User usr );
	public Watchlist findByUser( final User usr, final Long watchlistId );
}
