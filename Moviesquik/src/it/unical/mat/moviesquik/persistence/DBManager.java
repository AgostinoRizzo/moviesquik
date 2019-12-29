/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.CreditCard;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public class DBManager
{
	private static DBManager instance = null;
	
	private List<User> users = new ArrayList<User>();
	private List<CreditCard> creditCards = new ArrayList<>();
	
	public static DBManager getInstance()
	{
		if ( instance == null )
			instance = new DBManager();
		return instance;
	}
	
	private DBManager()
	{
		users.add(new User("Admin", "Admin", "admin@email.com", "", "", "adminadmin"));
		creditCards.add(new CreditCard("Admin", "1234123412341234", "2019-12", "123"));
	}
	
	public boolean exists( final User user )
	{
		for( final User u: users )
			if ( user.getEmail().equals(u.getEmail()) )
				return true;
		return false;
	}
	
	public boolean exists( final CreditCard card )
	{
		for( final CreditCard c: creditCards )
			if ( card.getNumber().equals(c.getNumber()) )
				return true;
		return false;
	}
	
	public boolean userCreditCard( final CreditCard card )
	{
		for( final User u: users )
			if ( u.getCreditCard().equals(card) )
				return true;
		return false;
	}
	
	public boolean used( final CreditCard card )
	{
		return false;
	}
	
	public boolean registerUser( final User user )
	{
		// TODO: add checks.
		users.add(user);
		return true;
	}

	public User login( final String email, final String password )
	{
		for( final User usr : users )
			if ( usr.getEmail().equals(email) && usr.getPassword().equals(password) )
				return usr;
		return null;
	}
}
