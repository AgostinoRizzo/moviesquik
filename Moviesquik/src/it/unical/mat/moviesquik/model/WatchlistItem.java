/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;

import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class WatchlistItem
{
	private Date dateTime;
	private MediaContent mediaContent;
	
	public Date getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	public MediaContent getMediaContent()
	{
		return mediaContent;
	}
	public void setMediaContent(MediaContent mediaContent)
	{
		this.mediaContent = mediaContent;
	}
	
	public static WatchlistItem createNewWatchlistItem( final Long mediaContentId )
	{
		final WatchlistItem item = new WatchlistItem();
		item.setMediaContent( DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaContentId));
		item.setDateTime(DateUtil.getCurrent());
		return item;
	}
}
