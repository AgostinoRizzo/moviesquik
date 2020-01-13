/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;

/**
 * @author Agostino
 *
 */
public class User
{
	private Long id;
	private String first_name;
	private String last_name;
	private String email;
	private Date birthday;
	private String gender;
	private String password;
	private Family family;
	
	public User()
	{}

	public User(final String first_name, 
				final String last_name, 
				final String email, 
				final Date birthday, 
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

	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getFirstName()
	{
		return first_name;
	}

	public void setFirstName(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLastName()
	{
		return last_name;
	}

	public void setLastName(String last_name)
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

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
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

	public Family getFamily()
	{
		return family;
	}
	
	public void setFamily(Family family)
	{
		this.family = family;
	}
	
}
