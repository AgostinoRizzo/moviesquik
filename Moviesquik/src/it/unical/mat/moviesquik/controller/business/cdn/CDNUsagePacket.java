/**
 * 
 */
package it.unical.mat.moviesquik.controller.business.cdn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Agostino
 *
 */
public class CDNUsagePacket
{
	private List<String> serversKeys = new ArrayList<String>();
	private List<List<Float>> serversUsageData = new ArrayList<List<Float>>();
	
	public CDNUsagePacket()
	{}
	
	public CDNUsagePacket( final Map<String, Float[]> chartSamples )
	{
		final Set<String> keys = chartSamples.keySet();
		int i;
		
		for ( final String key : keys )
		{			
			final Float[] samplesArray = chartSamples.get(key);
			final List<Float> samples = new ArrayList<Float>();
			
			for ( i=0; i<samplesArray.length; ++i )
				samples.add(samplesArray[i]);
			
			serversKeys.add(key);
			serversUsageData.add(samples);
		}
	}
	
	public List<String> getServersKeys()
	{
		return serversKeys;
	}
	public void setServersKeys(List<String> serversKeys)
	{
		this.serversKeys = serversKeys;
	}
	
	public List<List<Float>> getServersUsageData()
	{
		return serversUsageData;
	}
	public void setServersUsageData(List<List<Float>> serversUsageData)
	{
		this.serversUsageData = serversUsageData;
	}
	
}
