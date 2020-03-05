/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import it.unical.mat.moviesquik.persistence.DataSource;
import it.unical.mat.moviesquik.persistence.dao.CreditCardDao;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.FamilyDao;
import it.unical.mat.moviesquik.persistence.dao.FollowingDao;
import it.unical.mat.moviesquik.persistence.dao.FriendshipDao;
import it.unical.mat.moviesquik.persistence.dao.MediaContentDao;
import it.unical.mat.moviesquik.persistence.dao.MediaContentGenreDao;
import it.unical.mat.moviesquik.persistence.dao.MediaContentSearchDao;
import it.unical.mat.moviesquik.persistence.dao.NotificationDao;
import it.unical.mat.moviesquik.persistence.dao.PostDao;
import it.unical.mat.moviesquik.persistence.dao.RegistrationTransaction;
import it.unical.mat.moviesquik.persistence.dao.UserDao;

/**
 * @author Agostino
 *
 */
public class DaoFactoryJDBC implements DaoFactory
{
	private final DataSource dataSource;
	
	public DaoFactoryJDBC( final DataSource dataSource )
	{
		this.dataSource = dataSource;
	}
	
	@Override
	public UserDao getUserDao()
	{
		return new UserDaoJDBC(getNewStatementPrompter());
	}

	@Override
	public FamilyDao getFamilyDao()
	{
		return new FamilyDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public CreditCardDao getCreditCardDao()
	{
		return new CreditCardDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public RegistrationTransaction getRegistrationTransaction()
	{
		return new RegistrationTransactionJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentDao getMediaContentDao()
	{
		return new MediaContentDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentSearchDao getMediaContentSearchDao()
	{
		return new MediaContentSearchDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentGenreDao getMediaContentGenreDao()
	{
		return new MediaContentGenreDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public FriendshipDao getFriendshipDao()
	{
		return new FriendshipDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public FollowingDao getFollowingDao()
	{
		return new FollowingDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public NotificationDao getNotificationDao()
	{
		return new NotificationDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public PostDao getPostDao()
	{
		return new PostDaoJDBC(getNewStatementPrompter());
	}
	
	private StatementPrompterJDBC getNewStatementPrompter()
	{
		return new StatementPrompterJDBC(dataSource);
	}

}
