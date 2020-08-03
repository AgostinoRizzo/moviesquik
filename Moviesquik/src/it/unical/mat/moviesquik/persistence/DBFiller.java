/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

import java.io.File;
import java.util.List;

import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.dao.MediaContentDao;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class DBFiller
{
	private static final String MEDIA_CONTENTS_JSON_FILE = "data/media_contents.json";
	
	public void fillMediaContents( final String root_path )
	{		
		final MediaContent[] mcs = JSONUtil.readFromFile(root_path + File.separator + MEDIA_CONTENTS_JSON_FILE,
													    MediaContent[].class);
		
		for ( final MediaContent mc : mcs )
		{
			final MediaContentDao mcdao = DBManager.getInstance().getDaoFactory().getMediaContentDao();
			final List<MediaContent> found = mcdao.findByTitleYear(mc.getTitle(), mc.getYear());
			
			if ( found.isEmpty() )
				mcdao.save(mc);
			else
			{
				mc.setId(found.get(0).getId());
				mc.setRating(mc.getImdbRating());
				mcdao.updateRatings(mc);
			}
			
		}
	}
}
