/**
 * 
 */
package it.unical.mat.moviesquik.model.business;

/**
 * @author Agostino
 *
 */
public class CDNServer
{
	private Long id;
	private String key;
	private String name;
	private CDNServerLocation location;
	private String description;
	private Boolean status;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public CDNServerLocation getLocation()
	{
		return location;
	}
	public void setLocation(CDNServerLocation location)
	{
		this.location = location;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Boolean getStatus()
	{
		return status;
	}
	public void setStatus(Boolean status)
	{
		this.status = status;
	}
	
}
