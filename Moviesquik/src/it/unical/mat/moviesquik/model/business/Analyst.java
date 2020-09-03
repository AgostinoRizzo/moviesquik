/**
 * 
 */
package it.unical.mat.moviesquik.model.business;

import it.unical.mat.moviesquik.model.accounting.User;

/**
 * @author Agostino
 *
 */
public class Analyst
{
	private Long id;
	private String email;
	private String firstName;
	private String LastName;
	private String profileImagePath;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return LastName;
	}
	public void setLastName(String lastName)
	{
		LastName = lastName;
	}
	
	public String getProfileImagePath()
	{
		return (profileImagePath == null || profileImagePath.isEmpty()) 
				? User.DEFAULT_USER_PROFILE_IMG_PATH : User.USER_PROFILE_IMG_PATH + profileImagePath;
	}
	
	public void setProfileImagePath(String profileImagePath)
	{
		this.profileImagePath = profileImagePath;
	}
	
}
