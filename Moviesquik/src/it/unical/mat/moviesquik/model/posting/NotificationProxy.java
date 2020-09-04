/**
 * 
 */
package it.unical.mat.moviesquik.model.posting;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class NotificationProxy extends Notification
{
	private Long moviePartyId = null;
	
	public NotificationProxy()
	{}
	
	public NotificationProxy( final Long moviePartyId )
	{
		this.moviePartyId = moviePartyId;
	}
	
	public void setMoviePartyId(Long moviePartyId)
	{
		this.moviePartyId = moviePartyId;
	}
	
	@Override
	public MovieParty getMovieParty()
	{
		if ( movieParty == null && moviePartyId != null )
			movieParty = DBManager.getInstance().getDaoFactory().getMoviePartyDao().findById(moviePartyId);
		return movieParty;
	}
}
