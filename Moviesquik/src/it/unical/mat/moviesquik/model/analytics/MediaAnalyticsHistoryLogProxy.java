/**
 * 
 */
package it.unical.mat.moviesquik.model.analytics;

import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class MediaAnalyticsHistoryLogProxy extends MediaAnalyticsHistoryLog
{
	private Long mediaContentId;
	
	public Long getMediaContentId()
	{
		return mediaContentId;
	}
	public void setMediaContentId(Long mediaContentId)
	{
		this.mediaContentId = mediaContentId;
	}
	
	@Override
	public MediaContent getMedia()
	{
		if ( media == null )
			media = DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaContentId);
		return media;
	}
}
