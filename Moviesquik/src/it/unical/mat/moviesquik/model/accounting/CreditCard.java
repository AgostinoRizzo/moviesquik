/**
 * 
 */
package it.unical.mat.moviesquik.model.accounting;

import java.util.Date;

import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class CreditCard
{
	private Long id;
	private String name;
	private String number;
	private Date expiration;
	private String cvv;
	private Float balance;
	
	public CreditCard()
	{}

	public CreditCard(String name, String number, Date expiration, String cvv)
	{
		this.name = name;
		this.number = number;
		this.expiration = expiration;
		this.cvv = cvv;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if ( obj instanceof CreditCard )
			return number.equals(((CreditCard) obj).getNumber());
		return false;
	}

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

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Date getExpiration()
	{
		return expiration;
	}
	
	public String getExpirationString()
	{
		return DateUtil.toString(expiration);
	}

	public void setExpiration(Date expiration)
	{
		this.expiration = expiration;
	}

	public String getCvv()
	{
		return cvv;
	}

	public void setCvv(String cvv)
	{
		this.cvv = cvv;
	}
	
	public Float getBalance()
	{
		return balance;
	}
	
	public void setBalance(Float balance)
	{
		this.balance = balance;
	}
	
}
