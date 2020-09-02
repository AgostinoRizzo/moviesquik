/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
@ServerEndpoint("/movieparty/sync")
public class MoviePartySynchronizer
{
	private static Set<Session> moviePartySyncSessions = new CopyOnWriteArraySet<Session>();
	
	@OnOpen
	public void onOpen( final Session session )
	{}
	
	@OnMessage
	public void onMessage( final String message, final Session session ) throws IOException
	{
		session.getBasicRemote().sendText("Welcome!");
		
		final Long[] ids = parseIdsFromMessage(message, 2);
		if ( ids == null )
			return;
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final MovieParty party = daoFactory.getMoviePartyDao().findById(ids[1], daoFactory.getUserDao().findByPrimaryKey(ids[0]));
		if ( party == null )
			return;
		
		if ( DateUtil.isInCurrentDay(party.getStartDateTime()) )
			moviePartySyncSessions.add(session);
	}
	
	@OnClose
	public void onClose( final Session session )
	{
		moviePartySyncSessions.remove(session);
	}
	
	@OnError
	public void onError( final Session session, Throwable throwable )
	{}
	
	protected static void sendUpdateCheck()
	{
		moviePartySyncSessions.forEach
		( session -> 
		{
			synchronized (session)
			{
				try
				{ session.getBasicRemote().sendText("update"); } 
				catch (IOException e)
				{}
			}
		});
	}
	
	private static Long[] parseIdsFromMessage( final String message, final int count )
	{
		final String[] ids = message.split("\\s+");
		if ( ids.length != count )
			return null;
		
		final Long[] parsedIds = new Long[count];
		
		for ( int i=0; i<count; ++i )
			try
			{ parsedIds[i] = Long.parseLong( ids[i] ); }
			catch (NumberFormatException e) 
			{ return null; }
		
		return parsedIds;
	}
}
