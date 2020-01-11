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
	public static final String DEFAULT_NAME = "Family";
	
	private Long id;
	private String name;
	private List<User> members;
	private CreditCard creditCard;
	private Billing currentBilling;
	
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
	
	public Billing getCurrentBilling()
	{
		return currentBilling;
	}
	public void setCurrentBilling(Billing currentBilling)
	{
		this.currentBilling = currentBilling;
	}
	
}
