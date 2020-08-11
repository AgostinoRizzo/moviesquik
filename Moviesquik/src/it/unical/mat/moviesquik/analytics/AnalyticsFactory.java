/**
 * 
 */
package it.unical.mat.moviesquik.analytics;

/**
 * @author Agostino
 *
 */
public class AnalyticsFactory
{
	private static AnalyticsFactory instance = null;
	
	public static AnalyticsFactory getInstance()
	{
		if ( instance == null )
			instance = new AnalyticsFactory();
		return instance;
	}
	
	private AnalyticsFactory()
	{}
	
	public AnalyticsLogger createAnalyticsLogger()
	{
		return PersistentAnalyticsLogger.getInstance();
	}
	
	public AnalyticsExtractor createAnalyticsExtractor()
	{
		return PersistentAnalyticsExtractor.getInstance();
	}
}
