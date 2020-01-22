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
	public FriendshipDao getFriendshipDao();
	public FollowingDao getFollowingDao();
	public NotificationDao getNotificationDao();
	
	public RegistrationTransaction getRegistrationTransaction();
}
