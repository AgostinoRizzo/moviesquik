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
public interface AnalyticsExtractor
{
	public List<MediaContent> extractMostPopular( final DataListPage page );
	public List<MediaContent> extractTrendingNow( final DataListPage page );
}
