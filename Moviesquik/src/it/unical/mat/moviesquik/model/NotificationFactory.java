/**
 * 
 */
package it.unical.mat.moviesquik.model;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class NotificationFactory
{
	private static NotificationFactory instance = null;
	
	public static NotificationFactory getInstance()
	{
		if ( instance == null )
			instance = new NotificationFactory();
		return instance;
	}
	
	private NotificationFactory()
	{}
	
	public Notification createPostLikeFeedbackNotification( final User sender )
	{ return createNotification("Post feedback", sender.getFullName() + " likes your post", sender); }
	
	public Notification createPostLoveFeedbackNotification( final User sender )
	{ return createNotification("Post feedback", sender.getFullName() + " loves your post", sender); }
	
	public Notification createPostCommentNotification( final User sender )
	{ return createNotification("Post comment", sender.getFullName() + " commented your post", sender); }
	
	public Notification createPostShareNotification( final User sender )
	{ return createNotification("Post share", sender.getFullName() + " shared your post", sender); }
	
	public Notification createWatchlistShareNotification( final User sender, final Watchlist watchlist )
	{ return createNotification("Watchlist share", sender.getFullName() + " shared your " + watchlist.getName() + " watchlist", sender); }
	
	public Notification createMoviePartyInvitationNotification( final MovieParty party )
	{ return createNotification("Movie party invitation", party.getAdminstrator().getFullName() + " invited you to \"" + party.getName() + "\" movie party", party.getAdminstrator()); }
	
	private Notification createNotification( final String title, final String description, final User sender )
	{
		final Notification notification = new Notification();
		notification.setDateTime(DateUtil.getCurrent());
		notification.setTitle(title);
		notification.setDescription(description);
		notification.setIsRead(false);
		notification.setSubjectUser(sender);
		return notification;
	}
}
