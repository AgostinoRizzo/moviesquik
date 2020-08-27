/**
 * 
 */
package it.unical.mat.moviesquik.controller.business.cdn;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

import it.unical.mat.moviesquik.controller.async.Worker;
import it.unical.mat.moviesquik.model.business.CDNUsageChart;
import it.unical.mat.moviesquik.model.business.CDNUsageSamples;
import it.unical.mat.moviesquik.util.WebsocketUtil;

/**
 * @author Agostino
 *
 */
public class CDNUsageUpdateManager extends Worker
{
	private static final long UPDATE_INTERVAL = CDNUsageSamples.CDN_USAGE_CHART_SAMPLES_INTERVAL;  // expressed in milliseconds
	private static CDNUsageUpdateManager instance = null;
	
	private final Map<Session, Boolean> sessionsMap = new HashMap<Session, Boolean>();
	private final Lock lock = new ReentrantLock();
	
	public static CDNUsageUpdateManager getInstance()
	{
		if ( instance == null )
			instance = new CDNUsageUpdateManager();
		return instance;
	}
	
	private CDNUsageUpdateManager()
	{}
	
	public void register( final Session session )
	{
		lock.lock();
		sessionsMap.put(session, true);
		lock.unlock();
	}
	
	public void unregister( final Session session )
	{
		lock.lock();
		sessionsMap.remove(session);
		lock.unlock();
	}

	@Override
	protected void doWork()
	{
		final Set<Session> allSessions = sessionsMap.keySet();
		if ( !allSessions.isEmpty() )
		{
			final Map<String, Float[]> chartSamples = CDNUsageChart.getInstance().getCurrentChartSamples();
			final CDNUsagePacket cdnUsagePacket = new CDNUsagePacket(chartSamples);
			
			for ( final Session session : allSessions )
				sendUsagePacketFromSession(cdnUsagePacket, session);
		}
			
		waitForNextWork(UPDATE_INTERVAL);
	}

	@Override
	protected void onFinalize()
	{}
	
	private static boolean sendUsagePacketFromSession( final CDNUsagePacket usagePacket, final Session session )
	{
		return WebsocketUtil.<CDNUsagePacket>sendPacketFromSession( usagePacket, session );
	}
	
}
