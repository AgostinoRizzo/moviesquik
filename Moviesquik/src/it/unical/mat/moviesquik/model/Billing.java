/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Calendar;
import java.util.Date;

import it.unical.mat.moviesquik.util.DateUtil;

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
	public String getStartDateString()
	{
		return DateUtil.toString(startDate);
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
	
	public Date getEndDate()
	{
		final Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.MONTH, 1);
		
		return c.getTime();
	}
	
	public String getEndDateString()
	{
		return DateUtil.toString( getEndDate() );
	}
	
	public Float getBalance()
	{
		return PlanPricesManager.getInstance().getBillingPrice(this);
	}
	
	public Boolean canCreateMovieParty()
	{
		return (plan.equals("standard") || plan.equals("premium"));
	}
	
	public Boolean canAccessDeveloperAPI()
	{
		return plan.equals("premium");
	}
	
}
