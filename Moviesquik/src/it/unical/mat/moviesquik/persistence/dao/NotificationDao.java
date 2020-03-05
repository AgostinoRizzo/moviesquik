/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Notification;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface NotificationDao
{
	public boolean save( final Notification notification, final User user );
	public List<Notification> findByUser( final User user, final int limit );
	public List<Notification> findUnreadByUser( final User user, final int limit );
}
