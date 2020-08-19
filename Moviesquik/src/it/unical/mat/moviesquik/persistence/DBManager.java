/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

import java.util.List;

import it.unical.mat.moviesquik.model.BillingReport;
import it.unical.mat.moviesquik.model.CreditCard;
import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.jdbc.DaoFactoryJDBC;
import it.unical.mat.moviesquik.persistence.searching.DBSearchEngine;

/**
 * @author Agostino
 *
 */
public class DBManager
{
	private static DBManager instance = null;
	
	private static DataSource dataSource;
	private static DaoFactory daoFactory;
	
	private static FileSystemDataSoruce fsDataSource;
	private static DBSearchEngine searchEngine;
	
	static
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			dataSource = new DataSource("jdbc:postgresql://localhost:5432/moviesquik", "postgres", "postgres");
			daoFactory = new DaoFactoryJDBC(dataSource);
			fsDataSource = new FileSystemDataSoruce();
			searchEngine = new DBSearchEngine();
		}
		catch (ClassNotFoundException e)
		{ e.printStackTrace(); }
	}
	
	private DBFiller filler = new DBFiller();
	private List<String> mediaContentsGenres = null;
	
	
	public static DBManager getInstance()
	{
		if ( instance == null )
			instance = new DBManager();
		return instance;
	}
	
	private DBManager()
	{
		
	}
	
	public DaoFactory getDaoFactory()
	{
		return daoFactory;
	}
	
	public DBFiller getFiller()
	{
		return filler;
	}
	
	public static DataSource getDataSource()
	{
		return dataSource;
	}
	
	public static FileSystemDataSoruce getFileSystemDataSource()
	{
		return fsDataSource;
	}
	
	public static DBSearchEngine getSearchEngine()
	{
		return searchEngine;
	}
	
	public boolean canRegister( final User user )
	{
		return getDaoFactory().getUserDao().findByEmail( user.getEmail() ) == null;
	}
	
	public boolean canRegister( final Family account )
	{
		return getDaoFactory().getFamilyDao().findByEmail( account.getEmail() ) == null;
	}
	
	public boolean existsMatch( final CreditCard card )
	{
		return getDaoFactory().getCreditCardDao().existsMatch(card);
	}
	
	public boolean register( final Family family )
	{
		return getDaoFactory().getRegistrationTransaction().execute(family);
	}

	public Family accountLogin( final String email, final String password )
	{
		return getDaoFactory().getFamilyDao().findByLogin(email, password);
	}
	
	public User login( final String email, final String password )
	{
		return getDaoFactory().getUserDao().findByLogin(email, password);
	}
	
	public Analyst adminLogin( final String email, final String password )
	{
		return getDaoFactory().getAnalystDao().findByLogin(email, password);
	}
	
	public boolean updatePlanBilling( final BillingReport newBillingReport )
	{
		return getDaoFactory().getPlanBillingUpdateTransaction().execute(newBillingReport);
	}
	
	public List<String> getMediaContentsGenres()
	{
		if ( mediaContentsGenres == null )
			mediaContentsGenres = getDaoFactory().getMediaContentGenreDao().findAll();
		return mediaContentsGenres;
	}
	
}
