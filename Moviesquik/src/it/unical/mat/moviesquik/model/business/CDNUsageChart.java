/**
 * 
 */
package it.unical.mat.moviesquik.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class CDNUsageChart
{	
	private static CDNUsageChart instance = null;
	private final Map<String, CDNUsageSamples> serversUsageSamplesMap = new HashMap<String, CDNUsageSamples>();
	private final List<String> allServersKeys = new ArrayList<String>();
	private CDNUsageChartUpdateCallback updateCallback = null;
	private final Lock lock = new ReentrantLock();
	
	public static CDNUsageChart getInstance()
	{
		if ( instance == null )
			instance = new CDNUsageChart();
		return instance;
	}
	
	private CDNUsageChart()
	{
		final List<CDNServer> allServers = DBManager.getInstance().getDaoFactory().getCDNServerDao().findAll();
		if ( allServers != null )
			for ( final CDNServer server : allServers )
			{
				final String serverKey = server.getKey();
				allServersKeys.add(serverKey);
				update(serverKey, null);
			}
	}
	
	public void setUpdateCallback( final CDNUsageChartUpdateCallback callback )
	{
		lock.lock();
		updateCallback = callback;
		lock.unlock();
	}
	
	public void update( final String serverKey, final Float[] samples )
	{
		final CDNUsageSamples usageSamples = new CDNUsageSamples(samples);
		
		lock.lock();
		
		serversUsageSamplesMap.put(serverKey, usageSamples);
		if ( updateCallback != null )
			updateCallback.onUpdate( getCurrentUsageChart(usageSamples.getTimestamp()) );
		
		lock.unlock();
	}
	
	public Map<String, Float[]> getCurrentChartSamples()
	{
		lock.lock();
		try { return getCurrentUsageChart( System.currentTimeMillis() ); }
		finally { lock.unlock(); }
	}
	
	public List<String> getAllServersKeysList()
	{
		lock.lock();
		try { return new ArrayList<String>(allServersKeys); }
		finally { lock.unlock(); }
	}
	
	private Map<String, Float[]> getCurrentUsageChart( final long currentTimestamp )
	{
		final Map<String, Float[]> usageChart = new HashMap<String, Float[]>();
		final Set<String> serversKeys = serversUsageSamplesMap.keySet();
		
		for ( final String serverKey : serversKeys )
			usageChart.put(serverKey, CDNUsageSamples.createNewSamplesArray());
		
		long timestamp;
		int i=0;
		for( int t=CDNUsageSamples.CDN_USAGE_CHART_SAMPLES_WINDOW; t>0; --t )
		{
			timestamp = currentTimestamp - ( t * CDNUsageSamples.CDN_USAGE_CHART_SAMPLES_INTERVAL );
			
			for ( final String serverKey : serversKeys )
				usageChart.get(serverKey)[i] = getServerUsageValue(serverKey, timestamp);
			
			++i;
		}
		
		return usageChart;
	}
	
	private Float getServerUsageValue( final String serverKey, long timestamp )
	{
		final CDNUsageSamples usageSamples = serversUsageSamplesMap.get(serverKey);
		final long sampleEndTime = usageSamples.getTimestamp();
		
		if ( timestamp >= sampleEndTime )
			return 0.0f;
		
		final long samplesStartTime = sampleEndTime - 
				(CDNUsageSamples.CDN_USAGE_CHART_SAMPLES_WINDOW * CDNUsageSamples.CDN_USAGE_CHART_SAMPLES_INTERVAL);
		
		if ( timestamp < samplesStartTime )
			return 0.0f;
		
		timestamp -= samplesStartTime;
		final int sampleIndex = (int) (timestamp / CDNUsageSamples.CDN_USAGE_CHART_SAMPLES_INTERVAL);
		
		return usageSamples.getSamples()[sampleIndex];
	}
}
