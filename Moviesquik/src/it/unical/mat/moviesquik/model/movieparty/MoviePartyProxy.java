/**
 * 
 */
package it.unical.mat.moviesquik.model.movieparty;

import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class MoviePartyProxy extends MovieParty
{
	private Long mediaId = null;
	private Long adminId = null;
	
	public void setMediaId(Long mediaId)
	{
		this.mediaId = mediaId;
	}
	
	public void setAdminId(Long adminId)
	{
		this.adminId = adminId;
	}
	
	@Override
	public MediaContent getMedia()
	{
		if ( media == null && mediaId != null )
			media = DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaId);
		return media;
	}
	
	@Override
	public User getAdministrator()
	{
		if ( administrator == null && adminId != null )
			administrator = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(adminId);
		return administrator;
	}
	
	@Override
	public List<MoviePartyInvitation> getInvitations()
	{
		setInvitations( DBManager.getInstance().getDaoFactory().getMoviePartyInvitationDao().findByMovieParty(this) );
		return super.getInvitations();
	}
	
	@Override
	public List<MoviePartyParticipation> getParticipations()
	{
		setParticipations( DBManager.getInstance().getDaoFactory().getMoviePartyParticipationDao().findByMovieParty(this) );
		return super.getParticipations();
	}
}
