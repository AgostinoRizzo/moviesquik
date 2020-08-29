/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.WebsocketUtil;

/**
 * @author Agostino
 *
 */
public class MoviePartyIntervalManager
{
	private static MoviePartyIntervalManager instance = null;
	
	private final Map<Session, MovieParty> sessionsPartyMap = new HashMap<Session, MovieParty>();
	private final Map<MovieParty, List<Session>> partySessionsListMap = new HashMap<MovieParty, List<Session>>();
	private final Map<Long, Boolean> partyIntervalStatusMap = new HashMap<Long, Boolean>();
	
	private final Lock lock = new ReentrantLock();
	
	public static MoviePartyIntervalManager getInstance()
	{
		if ( instance == null )
			instance = new MoviePartyIntervalManager();
		return instance;
	}
	
	private MoviePartyIntervalManager()
	{}
	
	public void register( final Session session, final Long partyId )
	{
		lock.lock();
		
		try
		{
			if ( sessionsPartyMap.containsKey(session) )
				return;
			
			final MovieParty party = getMovieParty(partyId);
			if ( party == null )
				return;
			
			sessionsPartyMap.put(session, party);
			
			if ( partySessionsListMap.containsKey(party) )
				partySessionsListMap.get(party).add(session);
			else
			{
				final List<Session> partySessions = new LinkedList<Session>();
				partySessions.add(session);
				partySessionsListMap.put(party, partySessions);
			}
			
			if ( getMoviePartyIntervalStatus(party) )
			{
				final MoviePartyIntervalPacket intervalPacket = new MoviePartyIntervalPacket();
				intervalPacket.setRequest(false);
				intervalPacket.setInterval(true);
				WebsocketUtil.<MoviePartyIntervalPacket>sendPacketFromSession( intervalPacket, session );
			}
		}
		finally { lock.unlock(); }
	}
	
	public void unregister( final Session session )
	{
		lock.lock();
		
		try
		{
			if ( !sessionsPartyMap.containsKey(session) )
				return;
			
			final MovieParty party = sessionsPartyMap.get(session);
			
			sessionsPartyMap.remove(session);
			removeSessionFromPartySessionsMap(party, session);
		}
		finally { lock.unlock(); }
	}
	
	public void onIntervalPacket( final MoviePartyIntervalPacket intervalPacket, final Session session )
	{
		lock.lock();
		
		try
		{
			if ( !intervalPacket.isRequest() || !sessionsPartyMap.containsKey(session) )
				return;
			
			final MovieParty party = sessionsPartyMap.get(session);
			
			if ( !intervalPacket.getSenderId().equals(party.getAdministrator().getId()) )
				return;
			
			updateMoviePartyIntervalStatus( party, intervalPacket.getInterval() );
			
			intervalPacket.setRequest(false);
			final List<Session> allPartySessions = partySessionsListMap.get(party);
			
			for ( final Session s : allPartySessions )
				WebsocketUtil.<MoviePartyIntervalPacket>sendPacketFromSession( intervalPacket, s );
		}
		finally { lock.unlock(); }
	}
	
	private MovieParty getMovieParty( final Long partyId )
	{
		final Set<MovieParty> allParties = partySessionsListMap.keySet();
		for ( final MovieParty party : allParties )
			if ( party.getId().equals(partyId) )
				return party;
		return DBManager.getInstance().getDaoFactory().getMoviePartyDao().findById(partyId);
	}
	
	private void removeSessionFromPartySessionsMap( final MovieParty party, final Session session )
	{
		final List<Session> allPartySessions = partySessionsListMap.get(party);
		
		Session s;
		for ( final Iterator<Session> iter=allPartySessions.iterator(); iter.hasNext(); )
		{
			s = iter.next();
			if ( s.equals(session) )
			{
				iter.remove();
				if ( allPartySessions.isEmpty() )
					partySessionsListMap.remove(party);
				return;
			}
		}
	}
	
	private void updateMoviePartyIntervalStatus( final MovieParty party, final boolean newIntervalStatus )
	{
		final Long partyId = party.getId();
		
		if ( newIntervalStatus )
			partyIntervalStatusMap.put(partyId, true);
		else
		{
			if ( partyIntervalStatusMap.containsKey(partyId) )
				partyIntervalStatusMap.remove(partyId);
		}
			
	}
	
	private boolean getMoviePartyIntervalStatus( final MovieParty party )
	{
		return partyIntervalStatusMap.containsKey(party.getId());
	}
	
}
