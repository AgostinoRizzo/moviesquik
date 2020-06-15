/**
 * 
 */
package it.unical.mat.moviesquik.model.movieparty;

import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public class MoviePartyParticipation
{
	private MovieParty party;
	private User participant;
	
	public MovieParty getParty()
	{
		return party;
	}
	public void setParty(MovieParty party)
	{
		this.party = party;
	}
	public User getParticipant()
	{
		return participant;
	}
	public void setParticipant(User participant)
	{
		this.participant = participant;
	}
}
