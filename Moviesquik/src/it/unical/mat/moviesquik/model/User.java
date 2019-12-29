/**
 * 
 */
package it.unical.mat.moviesquik.model;

/**
 * @author Agostino
 *
 */
public class User
{
	private String first_name;
	private String last_name;
	private String email;
	private String birthday;
	private String gender;
	private String password;
	private String plan;
	private CreditCard creditCard;
	
	public User()
	{}

	public User(final String first_name, 
				final String last_name, 
				final String email, 
				final String birthday, 
				final String gender, 
				final String password)
	{
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.birthday = birthday;
		this.gender = gender;
		this.password = password;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPlan()
	{
		return plan;
	}

	public void setPlan(String plan)
	{
		this.plan = plan;
	}

	public CreditCard getCreditCard()
	{
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard)
	{
		this.creditCard = creditCard;
	}
	
}
