/**
 * 
 */
package it.unical.mat.moviesquik.model.movieparty;

import java.util.Date;
import java.util.List;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public class MovieParty
{
	private Long id;
	private String name;
	private String description;
	private Date startDateTime;
	private MediaContent media;
	private Boolean isPrivate;
	private User adminstrator;
	private List<MoviePartyInvitation> invitations;
	private List<MoviePartyParticipation> participations;
	
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
	public Date getStartDateTime()
	{
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime)
	{
		this.startDateTime = startDateTime;
	}
	public MediaContent getMedia()
	{
		return media;
	}
	public void setMedia(MediaContent media)
	{
		this.media = media;
	}
	public Boolean isPrivate()
	{
		return isPrivate;
	}
	public void setPrivate(Boolean isPrivate)
	{
		this.isPrivate = isPrivate;
	}
	public User getAdminstrator()
	{
		return adminstrator;
	}
	public void setAdminstrator(User adminstrator)
	{
		this.adminstrator = adminstrator;
	}
	public List<MoviePartyInvitation> getInvitations()
	{
		return invitations;
	}
	public void setInvitations(List<MoviePartyInvitation> invitations)
	{
		this.invitations = invitations;
	}
	public List<MoviePartyParticipation> getParticipations()
	{
		return participations;
	}
	public void setParticipations(List<MoviePartyParticipation> participations)
	{
		this.participations = participations;
	}
}
