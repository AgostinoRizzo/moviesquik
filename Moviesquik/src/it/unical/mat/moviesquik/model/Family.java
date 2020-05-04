/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.List;

/**
 * @author Agostino
 *
 */
public class Family
{
	public static final String DEFAULT_NAME = "MyFamily";
	
	private Long id;
	private String name;
	private List<User> members;
	private CreditCard creditCard;
	private BillingReport billingReport;
	private String email;
	private String password;
	private Boolean autoUpdate;
	
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
	
	public List<User> getMembers()
	{
		return members;
	}
	public void setMembers(List<User> members)
	{
		this.members = members;
	}
	
	public CreditCard getCreditCard()
	{
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard)
	{
		this.creditCard = creditCard;
	}
	
	public BillingReport getBillingReport()
	{
		return billingReport;
	}
	public void setBillingReport(BillingReport billingReport)
	{
		this.billingReport = billingReport;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public Boolean getAutoUpdate()
	{
		return autoUpdate;
	}
	public void setAutoUpdate(Boolean autoUpdate)
	{
		this.autoUpdate = autoUpdate;
	}
	
}
