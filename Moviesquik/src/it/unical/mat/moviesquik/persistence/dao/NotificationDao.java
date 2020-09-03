/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.posting.Notification;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public interface NotificationDao
{
	public boolean save( final Notification notification, final User user );
	public List<Notification> findByUser( final User user, final DataListPage page );
	public List<Notification> findUnreadByUser( final User user, final DataListPage page );
	public boolean readAll( final User user );
}
