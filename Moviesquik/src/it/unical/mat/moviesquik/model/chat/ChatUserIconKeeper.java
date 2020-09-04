/**
 * 
 */
package it.unical.mat.moviesquik.model.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Agostino
 *
 */
public class ChatUserIconKeeper
{
	private static ChatUserIconKeeper instance = null;
	
	private final Map<Long, String> userIconMap = new HashMap<Long, String>();
	private final Lock lock = new ReentrantLock();
	
	public static ChatUserIconKeeper getInstance()
	{
		if ( instance == null )
			instance = new ChatUserIconKeeper();
		return instance;
	}
	
	private ChatUserIconKeeper()
	{}
	
	public void keepUserIcon( final Long userId, final String icon )
	{
		lock.lock();
		userIconMap.put(userId, icon);
		lock.unlock();
	}
	
	public String getUserIcon( final Long userId )
	{
		lock.lock();
		try
		{ 
			if ( userIconMap.containsKey(userId) )
				return userIconMap.get(userId);
			return null;
		}
		finally { lock.unlock(); }
	}
}
