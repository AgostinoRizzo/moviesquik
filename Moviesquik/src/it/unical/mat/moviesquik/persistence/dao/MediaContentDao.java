/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.MediaContent;

/**
 * @author Agostino
 *
 */
public interface MediaContentDao
{
	public boolean save( final MediaContent mediaContent );
	public boolean updateRatings( final MediaContent mediaContent );
	public List<MediaContent> findByTitleYear( final String title, final short year );
	public MediaContent getMediaContentOfTheDay();
	public MediaContent findById( final Long id );
	public boolean updateNewViewById( final Long id );
}
