/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class Watchlist
{
	private Long id;
	private String name;
	private String description;
	private Date dateTime;
	private Boolean isDefault;
	private User owner;
	private List<WatchlistItem> items;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Date getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	public Boolean getIsDefault()
	{
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault)
	{
		this.isDefault = isDefault;
	}
	public User getOwner()
	{
		return owner;
	}
	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	public List<WatchlistItem> getItems()
	{
		return items;
	}
	public void setItems(List<WatchlistItem> items)
	{
		this.items = items;
	}
	
	public boolean contains( final Long mediaContentId )
	{
		for( final WatchlistItem item : items )
			if ( item.getMediaContent().getId().equals(mediaContentId) )
				return true;
		return false;
	}
	
	public boolean add( final Long mediaContentId )
	{
		if ( contains(mediaContentId) )
			return false;
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final WatchlistItem newItem = WatchlistItem.createNewWatchlistItem(mediaContentId);
		
		if ( daoFactory.getWatchlistItemDao().insert(newItem, this) )
		{
			items = daoFactory.getWatchlistItemDao().findAllByWatchlist(this);
			return true;
		}
		return false;
	}
	
	public static Watchlist createNewWatchLaterWatchlist( final User owner )
	{
		return createNewWatchlist
				("Watch Later", "Insert in this Watch List all the media content that you want to watch later.", owner, true);
	}
	
	public static Watchlist createNewFavoritesWatchlist( final User owner )
	{
		return createNewWatchlist
				("Favorites", "Insert in this Watch List all the media content that you love.", owner, true);
	}
	
	public static Watchlist createsNewWatchlist( final String name, final String description, final User owner )
	{
		return createNewWatchlist(name, description, owner, false);
	}
	
	public static Watchlist createNewWatchlist( final String name, final String description, final User owner, final boolean isDefault )
	{
		final Watchlist wl = new Watchlist();
		wl.setName(name);
		wl.setDescription(description);
		wl.setDateTime(DateUtil.getCurrent());
		wl.setIsDefault(isDefault);
		wl.setOwner(owner);
		wl.setItems(new ArrayList<WatchlistItem>());
		return wl;
	}
}
