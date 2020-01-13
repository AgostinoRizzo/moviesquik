/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;

/**
 * @author Agostino
 *
 */
public class Billing
{
	private Long id;
	private Date startDate;
	private String plan;
	private boolean isTrial;
	private Family family;
	
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
	public String getPlan()
	{
		return plan;
	}
	public void setPlan(String plan)
	{
		this.plan = plan;
	}
	public boolean isTrial()
	{
		return isTrial;
	}
	public void setTrial(boolean isTrial)
	{
		this.isTrial = isTrial;
	}
	
	public Family getFamily()
	{
		return family;
	}
	public void setFamily(Family family)
	{
		this.family = family;
	}
	
}
