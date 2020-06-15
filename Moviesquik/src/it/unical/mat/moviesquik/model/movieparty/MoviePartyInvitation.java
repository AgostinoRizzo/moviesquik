/**
 * 
 */
package it.unical.mat.moviesquik.model.movieparty;

import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public class MoviePartyInvitation
{
	private MovieParty party;
	private User guest;
	
	public MovieParty getParty()
	{
		return party;
	}
	public void setParty(MovieParty party)
	{
		this.party = party;
	}
	public User getGuest()
	{
		return guest;
	}
	public void setGuest(User guest)
	{
		this.guest = guest;
	}
}
