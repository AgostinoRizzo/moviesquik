/**
 * 
 */
package it.unical.mat.moviesquik.model.business;

import java.util.Map;

/**
 * @author Agostino
 *
 */
public interface CDNUsageChartUpdateCallback
{
	public void onUpdate( final Map<String, Float[]> usageChart );
}
