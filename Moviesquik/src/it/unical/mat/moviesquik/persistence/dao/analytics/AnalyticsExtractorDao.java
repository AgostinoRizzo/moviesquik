/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

import java.util.List;

import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public interface AnalyticsExtractorDao
{
	public List<MediaContent> findMostPopular( final DataListPage page );
	public List<MediaContent> findTrendingNow( final DataListPage page );
	public List<MediaContent> findSuggested( final Long subjectId, final DataListPage page );
}
