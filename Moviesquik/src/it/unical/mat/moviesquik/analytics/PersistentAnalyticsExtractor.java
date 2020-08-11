/**
 * 
 */
package it.unical.mat.moviesquik.analytics;

import java.util.List;

import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class PersistentAnalyticsExtractor implements AnalyticsExtractor
{
	private static final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
	private static PersistentAnalyticsExtractor instance = null;
	
	public static PersistentAnalyticsExtractor getInstance()
	{
		if ( instance == null )
			instance = new PersistentAnalyticsExtractor();
		return instance;
	}
	
	private PersistentAnalyticsExtractor()
	{}
	
	@Override
	public List<MediaContent> extractMostPopular( final DataListPage page )
	{
		return daoFactory.getAnalyticsExtractorDao().findMostPopular(page);
	}

	@Override
	public List<MediaContent> extractTrendingNow( final DataListPage page )
	{
		return daoFactory.getAnalyticsExtractorDao().findTrendingNow(page);
	}

}
