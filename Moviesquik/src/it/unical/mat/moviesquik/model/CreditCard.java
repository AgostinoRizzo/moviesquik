/**
 * 
 */
package it.unical.mat.moviesquik.model;

/**
 * @author Agostino
 *
 */
public class CreditCard
{
	private String name;
	private String number;
	private String expiration;
	private String cvv;
	
	public CreditCard()
	{}

	public CreditCard(String name, String number, String expiration, String cvv)
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

	public String getExpiration()
	{
		return expiration;
	}

	public void setExpiration(String expiration)
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
	
}
