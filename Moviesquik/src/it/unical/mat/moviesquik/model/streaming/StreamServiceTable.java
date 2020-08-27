/**
 * 
 */
package it.unical.mat.moviesquik.model.streaming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Agostino
 *
 */
public class StreamServiceTable implements Runnable
{
	public static final int EXPIRATION_TIME = 120000;  // expressed in seconds.
	private static StreamServiceTable instance = null;
	
	private final Map<StreamService, Long> services = new HashMap<StreamService, Long>();
	
	private final Lock lock = new ReentrantLock();
	private final Condition emptyCond = lock.newCondition();
	private final Condition sleepCond = lock.newCondition();
	
	private final Thread tableUpdaterThread;

	public static StreamServiceTable getInstance()
	{
		if ( instance == null )
			instance = new StreamServiceTable();
		return instance;
	}
	private StreamServiceTable()
	{
		tableUpdaterThread = new Thread(this);
		tableUpdaterThread.setDaemon(true);
		tableUpdaterThread.start();
	}
	public void onSync( final StreamService service )
	{	
		lock.lock();
		if ( services.isEmpty() )
			emptyCond.signal();
		services.put(service, System.currentTimeMillis() + EXPIRATION_TIME);
		lock.unlock();
	}
	public List<StreamService> getStreamServices()
	{
		final List<StreamService> ans = new ArrayList<StreamService>();
		lock.lock();
		try
		{
			final Set<StreamService> keys = services.keySet();
			for ( final StreamService s : keys )
				ans.add(s);
			return ans;
		}
		finally 
		{
			lock.unlock();
		}
	}
	public boolean isStreamServiceOn( final String serverKey )
	{
		lock.lock();
		try
		{
			final Set<StreamService> activeServices = services.keySet();
			for ( final StreamService s : activeServices )
				if ( s.getServerKey().equals(serverKey) )
					return true;
			return false;
		}
		finally { lock.unlock(); }
	}
	@Override
	public void run()
	{
		lock.lock();
		
		try
		{
			while (true)
			{
				while ( services.isEmpty() )
					emptyCond.await();
				
				final long minTime = Collections.min( services.values() );
				final long currTime = System.currentTimeMillis();
				final long timeToWait = minTime - currTime;
				
				if ( timeToWait > 0 )
					sleepCond.await(timeToWait, TimeUnit.MILLISECONDS);
				else
					removeExpiredStreamServices(currTime);
			}
		}
		catch (InterruptedException e)
		{}
		finally 
		{ lock.unlock(); }
	}
	
	private void removeExpiredStreamServices( final long targetTime )
	{
		final List<StreamService> toRemove = new ArrayList<StreamService>();
		
		for ( final Entry<StreamService, Long> keyValue : services.entrySet() )
			if ( keyValue.getValue().compareTo(targetTime)  < 0)
				toRemove.add(keyValue.getKey());
		
		for ( final StreamService s : toRemove )
			services.remove(s);
	}
}
