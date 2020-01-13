/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import it.unical.mat.moviesquik.persistence.DataSource;
import it.unical.mat.moviesquik.persistence.dao.CreditCardDao;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.FamilyDao;
import it.unical.mat.moviesquik.persistence.dao.RegistrationTransaction;
import it.unical.mat.moviesquik.persistence.dao.UserDao;

/**
 * @author Agostino
 *
 */
public class DaoFactoryJDBC implements DaoFactory
{
	private final StatementPrompterJDBC statementPrompter;
	
	public DaoFactoryJDBC( final DataSource dataSource )
	{
		statementPrompter = new StatementPrompterJDBC(dataSource);
	}
	
	@Override
	public UserDao getUserDao()
	{
		return new UserDaoJDBC(statementPrompter);
	}

	@Override
	public FamilyDao getFamilyDao()
	{
		return new FamilyDaoJDBC(statementPrompter);
	}
	
	@Override
	public CreditCardDao getCreditCardDao()
	{
		return new CreditCardDaoJDBC(statementPrompter);
	}
	
	@Override
	public RegistrationTransaction getRegistrationTransaction()
	{
		return new RegistrationTransactionJDBC(statementPrompter);
	}

}
