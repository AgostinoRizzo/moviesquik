/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.chat.ChatMessage;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class GroupChatManager extends ChatManager
{
	private static GroupChatManager instance = null;
	
	private final Map<Long, Map<Long, Session>> groupChatMap = new HashMap<Long, Map<Long,Session>>();
	private final Map<Long, Long>               userGroupMap = new HashMap<Long, Long>();
	private final Lock lock = new ReentrantLock();
	
	public static GroupChatManager getInstance()
	{
		if ( instance == null )
			instance = new GroupChatManager();
		return instance;
	}
	
	private GroupChatManager()
	{}
	
	public void register( final Long userId, final Session session, final Long groupId )
	{
		lock.lock();
		
		unregister(userId, groupId);
		final Map<Long, Session> usersMap = getUserMap(groupId);
		usersMap.put(userId, session);
		userGroupMap.put(userId, groupId);
		
		lock.unlock();
	}
	
	public void unregister( final Long userId, final Long groupId )
	{
		lock.lock();
		
		final Map<Long, Session> usersMap = getUserMap(groupId);
		usersMap.remove(userId);
		userGroupMap.remove(userId);
		
		lock.unlock();
	}
	
	public void onSendMessage( final ChatMessagePacket messagePacket )
	{
		try
		{
			lock.lock();
			
			final Long senderId = messagePacket.getSenderId();
			final Long groupId = userGroupMap.get(senderId);
			final Map<Long, Session> usersMap = getUserMap(groupId);
			final Set<Long> usersIds = usersMap.keySet();
			
			// complete message packet info
			messagePacket.setAck(false);
			messagePacket.setTime(DateUtil.getCurrentClockTime());
			
			// store message to DB
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			
			final User sender = daoFactory.getUserDao().findByPrimaryKey( messagePacket.getSenderId() );
			if ( sender == null ) return;
			final MovieParty movieParty = daoFactory.getMoviePartyDao().findById(groupId, sender);
			if ( movieParty == null ) return;
			
			final ChatMessage message = new ChatMessage(messagePacket, sender, movieParty);
			final boolean saved = DBManager.getInstance().getDaoFactory().getChatMessageDao().save(message);
			if ( !saved ) return;
			
			for ( final Long uId : usersIds )
				if ( !uId.equals(senderId) )
					sendMessageFromSession(messagePacket, usersMap.get(uId));
			
			// send ACK to sender
			messagePacket.setAck(true);
			sendMessageFromSession(messagePacket, usersMap.get(senderId));
		}
		finally 
		{
			lock.unlock();
		}
	}
	
	private Map<Long, Session> getUserMap( final Long groupId )
	{
		Map<Long, Session> usersMap = groupChatMap.get(groupId);
		if ( usersMap == null )
		{
			usersMap = new HashMap<Long, Session>();
			groupChatMap.put(groupId, usersMap);
		}
		return usersMap;
	}
	
	private boolean sendMessageFromSession( final ChatMessagePacket messagePacket, final Session session )
	{
		try
		{ session.getBasicRemote().sendObject(messagePacket); return true;	} 
		catch (IOException | EncodeException e)
		{ return false; }
	}
}
