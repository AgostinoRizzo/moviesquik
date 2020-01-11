/**
 * 
 */
package it.unical.mat.moviesquik.model;

/**
 * @author Agostino
 *
 */
public class Billing
{
	private Long id;
	private String startDate;
	private String plan;
	private boolean isTrial;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public void setStartDate(String startDate)
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
	
}
