/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.Watchlist;

/**
 * @author Agostino
 *
 */
public interface WatchlistDao
{
	public boolean insert( final Watchlist wl );
	public boolean update( final Watchlist wl );
	public boolean remove( final Long id );
	public List<Watchlist> findByUser( final User usr );
}
