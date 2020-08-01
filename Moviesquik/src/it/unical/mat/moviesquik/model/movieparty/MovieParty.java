/**
 * 
 */
package it.unical.mat.moviesquik.model.movieparty;

import java.util.Date;
import java.util.List;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.util.DateUtil;

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
	private Date creationDateTime;
	private MediaContent media;
	private Boolean isPrivate;
	private User administrator;
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
	public String getHumanReadableStartDateTime()
	{
		return DateUtil.toHumanReadable(startDateTime, true);
	}
	public Date getCreationDateTime()
	{
		return creationDateTime;
	}
	public void setCreationDateTime(Date creationDateTime)
	{
		this.creationDateTime = creationDateTime;
	}
	public String getHumanReadableCreationDateTime()
	{
		return DateUtil.toHumanReadable(creationDateTime);
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
	public User getAdministrator()
	{
		return administrator;
	}
	public void setAdministrator(User administrator)
	{
		this.administrator = administrator;
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
	public boolean isExpired()
	{
		return DateUtil.isExpired(startDateTime);
	}
	public boolean isPlaying()
	{
		return isExpired() && (DateUtil.getCurrent().getTime() < startDateTime.getTime() + 120*60*1000);  // TODO: manage media content duration
	}
	public Long getCurrentWatchingTimestamp()
	{
		if ( isPlaying() )
			return DateUtil.getCurrent().getTime() - startDateTime.getTime();
		return null;
	}
}
