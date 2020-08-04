/**
 * 
 */
package it.unical.mat.moviesquik.model.movieparty;

import java.util.List;

import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class MoviePartyProxy extends MovieParty
{
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
