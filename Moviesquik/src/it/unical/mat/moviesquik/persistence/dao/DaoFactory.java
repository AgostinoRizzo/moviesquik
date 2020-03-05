/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

/**
 * @author Agostino
 *
 */
public interface DaoFactory
{
	public UserDao getUserDao();
	public FamilyDao getFamilyDao();
	public CreditCardDao getCreditCardDao();
	public MediaContentDao getMediaContentDao();
	public MediaContentSearchDao getMediaContentSearchDao();
	public MediaContentGenreDao getMediaContentGenreDao();
	public FriendshipDao getFriendshipDao();
	public FollowingDao getFollowingDao();
	public NotificationDao getNotificationDao();
	public PostDao getPostDao();
	
	public RegistrationTransaction getRegistrationTransaction();
}
