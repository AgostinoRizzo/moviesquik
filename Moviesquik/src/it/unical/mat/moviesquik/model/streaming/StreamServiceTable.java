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

import it.unical.mat.moviesquik.model.business.CDNServer;
import it.unical.mat.moviesquik.model.business.CDNServerLocation;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class StreamServiceTable implements Runnable
{
	public static final int EXPIRATION_TIME = 120000;  // expressed in seconds.
	public static final int MAX_STREAM_SERVICE_DISTANCE = 10000;  // expressed in kilometers
	private static StreamServiceTable instance = null;
	
	private final Map<StreamService, Long> services = new HashMap<StreamService, Long>();
	private Map<String, CDNServer> serverDataMap = null;
	
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
		services.put(streamServiceAlreadySync(service), System.currentTimeMillis() + EXPIRATION_TIME);
		lock.unlock();
	}
	public List<StreamService> getStreamServices( final ClientGeolocation geolocation )
	{
		final List<StreamService> ans = new ArrayList<StreamService>();
		lock.lock();
		try
		{
			final Set<StreamService> keys = services.keySet();
			for ( final StreamService s : keys )
				if ( geolocation == null || getStreamServiceDistance(s.getServerKey(), geolocation) 
											<= MAX_STREAM_SERVICE_DISTANCE )
					ans.add(s);
			
			if ( ans.isEmpty() )
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
	
	private int getStreamServiceDistance( final String cdnServerKey, final ClientGeolocation geolocation )
	{
		loadServiceDataMap();
		
		final CDNServer cdnServer = serverDataMap.get(cdnServerKey);
		final CDNServerLocation serverLocation = cdnServer.getLocation();
		
		final float lat1  = serverLocation.getLatitude();
		final float long1 = serverLocation.getLongitude();
		
		final float lat2  = geolocation.getLatitude();
		final float long2 = geolocation.getLongitude();
		
		float distance = (float) Math.acos(Math.sin(lat2 * Math.PI / 180.0) * Math.sin(lat1 * Math.PI / 180.0) +
				Math.cos(lat2 * Math.PI / 180.0) * Math.cos(lat1 * Math.PI / 180.0) *
				Math.cos((long1 - long2) * Math.PI / 180.0)) * 6371;
		return (int) distance;
	}
	
	private void loadServiceDataMap()
	{
		if ( serverDataMap == null )
		{
			final List<CDNServer> allServers = DBManager.getInstance().getDaoFactory().getCDNServerDao().findAll();
			serverDataMap = new HashMap<String, CDNServer>();
			for ( final CDNServer server : allServers )
				serverDataMap.put(server.getKey(), server);
		}
	}
	
	private StreamService streamServiceAlreadySync( final StreamService service )
	{
		final Set<StreamService> allServices = services.keySet();
		for ( final StreamService s : allServices )
			if ( s.getServerKey().equals(service.getServerKey()) )
				return s;
		return service;
	}
	
}
