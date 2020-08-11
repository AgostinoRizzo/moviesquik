/**
 * 
 */
package it.unical.mat.moviesquik.analytics;

import java.util.List;

import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public class AnalyticsFacade
{
	private static final AnalyticsFactory analyticsFactory = AnalyticsFactory.getInstance();
	
	public static AnalyticsLogger getLogger()
	{
		return analyticsFactory.createAnalyticsLogger();
	}
	
	public static AnalyticsExtractor getExtractor()
	{
		return analyticsFactory.createAnalyticsExtractor();
	}
	
	public static List<MediaContent> getMostPopularMediaContents( final DataListPage page )
	{
		return getExtractor().extractMostPopular(page);
	}
	
	public static List<MediaContent> getTrendingNowMediaContents( final DataListPage page )
	{
		return getExtractor().extractTrendingNow(page);
	}
	
	public static List<MediaContent> getMostPopularMediaContents( final int limit )
	{
		return getExtractor().extractMostPopular( new DataListPage(limit) );
	}
	
	public static List<MediaContent> getTrendingNowMediaContents( final int limit )
	{
		return getExtractor().extractTrendingNow( new DataListPage(limit) );
	}
	
}
