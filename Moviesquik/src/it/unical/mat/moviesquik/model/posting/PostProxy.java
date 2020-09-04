/**
 * 
 */
package it.unical.mat.moviesquik.model.posting;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.watchlist.Watchlist;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class PostProxy extends Post
{
	private Long ownerId = null;
	private Long watchlistId = null;
	
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}
	
	public void setWatchlistId(Long watchlistId)
	{
		this.watchlistId = watchlistId;
	}
	
	@Override
	public User getOwner()
	{
		if ( owner == null && ownerId != null )
			owner = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(ownerId);
		return owner;
	}
	
	@Override
	public Watchlist getWatchlist()
	{
		if ( watchlist == null && watchlistId != null )
			watchlist = DBManager.getInstance().getDaoFactory().getWatchlistDao().findById(watchlistId);
		return watchlist;
	}
}
