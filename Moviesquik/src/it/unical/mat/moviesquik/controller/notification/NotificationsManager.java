/**
 * 
 */
package it.unical.mat.moviesquik.controller.notification;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

import it.unical.mat.moviesquik.model.Notification;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.WebsocketUtil;

/**
 * @author Agostino
 *
 */
public class NotificationsManager
{
	private static final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
	private static NotificationsManager instance = null;
	
	private final Map<Long, Session> userSessionMap = new HashMap<Long, Session>();
	private Lock lock = new ReentrantLock();
	
	public static NotificationsManager getInstance()
	{
		if ( instance == null )
			instance = new NotificationsManager();
		return instance;
	}
	
	private NotificationsManager()
	{}
	
	public void register( final Long userId, final Session session )
	{
		lock.lock();
		userSessionMap.put(userId, session);
		lock.unlock();
	}
	
	public void unregister( final Long userId )
	{
		lock.lock();
		userSessionMap.remove(userId);
		lock.unlock();
	}
	
	public boolean sendNofitication( final Notification notification, final User receiver )
	{
		final boolean ans = daoFactory.getNotificationDao().save(notification, receiver);
		
		lock.lock();
		if ( userSessionMap.containsKey(receiver.getId()) )
		{
			final Session recvSession = userSessionMap.get(receiver.getId());
			WebsocketUtil.<NotificationPacket>sendPacketFromSession( new NotificationPacket(notification), recvSession);
		}
		lock.unlock();
		
		return ans;
	}
	
	public boolean sendTrigger( final String triggerUrl, final String triggerError, final User receiver )
	{
		final NotificationPacket triggerPacket = new NotificationPacket();
		triggerPacket.setTrigger(true);
		triggerPacket.setTriggerUrl(triggerUrl);
		triggerPacket.setTriggerError(triggerError);
		
		lock.lock();
		try
		{
			if ( userSessionMap.containsKey(receiver.getId()) )
			{
				final Session recvSession = userSessionMap.get(receiver.getId());
				WebsocketUtil.<NotificationPacket>sendPacketFromSession( triggerPacket, recvSession);
				return true;
			}
			return false;
		}
		finally { lock.unlock(); }
	}
}
