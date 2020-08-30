/**
 * 
 */
package it.unical.mat.moviesquik.controller.developer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.mat.moviesquik.controller.notification.NotificationsManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.developer.DeveloperSetting;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class APITriggerQueryManager
{
	private static final Pattern MEDIA_CONTENTS_PLAYER_REGEX = Pattern.compile("^\\s*play\\s+(\\w[\\w\\s]*)");
	private static final NotificationsManager notificationsManager = NotificationsManager.getInstance();
	
	public static boolean manageTriggerQuery( final DeveloperSetting developerSetting, final User usr, final String query )
	{
		if ( !developerSetting.getActive() )
		{
			notificationsManager.sendTrigger(null, "API Key not activated.", usr);
			return false;
		}
		
		final Matcher m = MEDIA_CONTENTS_PLAYER_REGEX.matcher(query);
		
		if ( m.find() && m.groupCount() > 0 )
			return manageMediaContentsPlayerTrigger(developerSetting, usr, m.group(1));
		return false;
	}
	
	private static boolean manageMediaContentsPlayerTrigger( final DeveloperSetting developerSetting, final User usr, final String mediaTitle )
	{
		if ( !developerSetting.getPlayAction() )
		{
			notificationsManager.sendTrigger(null, "Service trigger action disabled.", usr);
			return false;
		}
		
		if ( mediaTitle == null || mediaTitle.isEmpty() )
		{
			notificationsManager.sendTrigger(null, "Invalid media title.", usr);
			return false;
		}
		
		final List<MediaContent> mediaContents = DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
				.searchByTitle(mediaTitle, true, SortingPolicy.NONE, new DataListPage(1));
		if ( mediaContents.isEmpty() )
		{
			notificationsManager.sendTrigger(null, "Cannot find any media like " + mediaTitle + ".", usr);
			return false;
		}
		
		final MediaContent mcToPlay = mediaContents.get(0);
		return notificationsManager.sendTrigger("watch?key=" + mcToPlay.getId().toString(), null, usr);
	}
}
