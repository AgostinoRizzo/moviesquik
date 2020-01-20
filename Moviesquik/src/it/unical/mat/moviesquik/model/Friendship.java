/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;

/**
 * @author Agostino
 *
 */
public class Friendship
{
	private Long id;
	private Date startDate;
	private User firstUser;
	private User secondUser;
	private Boolean firstForApplicant;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public User getFirstUser()
	{
		return firstUser;
	}
	public void setFirstUser(User aUser)
	{
		this.firstUser = aUser;
	}
	public User getSecondUser()
	{
		return secondUser;
	}
	public void setSecondUser(User bUser)
	{
		this.secondUser = bUser;
	}
	public Boolean getFirstForApplicant()
	{
		return firstForApplicant;
	}
	public void setFirstForApplicant(Boolean aForApplicant)
	{
		this.firstForApplicant = aForApplicant;
	}
	public User getApplicantUser()
	{
		if ( firstForApplicant == null )
			return null;
		return (firstForApplicant) ? firstUser : secondUser;
	}
}
