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
	{ return createSimpleNotification("Post feedback", sender.getFullName() + " likes your post", sender); }
	
	public Notification createPostLoveFeedbackNotification( final User sender )
	{ return createSimpleNotification("Post feedback", sender.getFullName() + " loves your post", sender); }
	
	public Notification createPostCommentNotification( final User sender )
	{ return createSimpleNotification("Post comment", sender.getFullName() + " commented your post", sender); }
	
	public Notification createPostShareNotification( final User sender )
	{ return createSimpleNotification("Post share", sender.getFullName() + " shared your post", sender); }
	
	public Notification createWatchlistShareNotification( final User sender, final Watchlist watchlist )
	{ return createSimpleNotification("Watchlist share", sender.getFullName() + " shared your " + watchlist.getName() + " watchlist", sender); }
	
	public Notification createMoviePartyInvitationNotification( final MovieParty party )
	{ return createSimpleNotification("Movie party invitation", party.getAdministrator().getFullName() + " invited you to \"" + party.getName() + "\" movie party", party.getAdministrator()); }
	
	public Notification createMoviePartyParticipationNotification( final MovieParty party, final User participant, final boolean participate )
	{
		return createMoviePartyNotification
				("Movie party participation", participant.getFullName() + " will " + (participate ? "" : "not ")  + "participate at the party \"" + 
						party.getName() + "\"", party);
	}
	public Notification createMoviePartyStartedNotification( final MovieParty party )
	{
		return createMoviePartyNotification
				("Movie party event", "The movie party \"" + party.getName() + "\" is started right now", party);
	}
	public Notification createMoviePartyEndedNotification( final MovieParty party )
	{
		return createMoviePartyNotification
				("Movie party event", "The movie party \"" + party.getName() + "\" is ended right now", party);
	}
	public Notification createMoviePartyReminderNotification( final MovieParty party, final int minutesToStart )
	{
		return createMoviePartyNotification
				("Movie party event", "The movie party \"" + party.getName() + "\" will start in " +
				 minutesToStart + (minutesToStart == 1 ? " minute" : " minutes"), party);
	}
	
	private Notification createSimpleNotification( final String title, final String description, final User sender )
	{
		final Notification notification = new Notification();
		notification.setDateTime(DateUtil.getCurrent());
		notification.setTitle(title);
		notification.setDescription(description);
		notification.setIsRead(false);
		notification.setSubjectUser(sender);
		return notification;
	}
	
	private Notification createMoviePartyNotification( final String title, final String description, final MovieParty movieParty )
	{
		final Notification notification = new Notification();
		notification.setDateTime(DateUtil.getCurrent());
		notification.setTitle(title);
		notification.setDescription(description);
		notification.setIsRead(false);
		notification.setMovieParty(movieParty);
		return notification;
	}
}
