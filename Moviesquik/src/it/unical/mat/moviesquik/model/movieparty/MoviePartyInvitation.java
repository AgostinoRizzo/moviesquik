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
	private InvitationAnswer answer;
	
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
	public InvitationAnswer getAnswer()
	{
		return answer;
	}
	public void setAnswer(InvitationAnswer answer)
	{
		this.answer = answer;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if ( this == obj )
			return true;
		if ( obj instanceof MoviePartyInvitation )
		{
			final MoviePartyInvitation other = (MoviePartyInvitation) obj;
			return this.party.getId().equals(other.party.getId()) &&
				   this.guest.getId().equals(other.guest.getId()) &&
				   this.answer.equals(other.answer);
		}
		return false;
	}
}
