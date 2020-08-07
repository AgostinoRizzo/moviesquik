/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.chat.ChatMessage;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class UserChatManager implements ChatManager
{
	private static UserChatManager instance = null;
	
	private final Map<Long, Session> userSessionMap = new HashMap<Long, Session>();
	private Lock lock = new ReentrantLock();
	
	public static UserChatManager getInstance()
	{
		if ( instance == null )
			instance = new UserChatManager();
		return instance;
	}
	
	private UserChatManager()
	{}
	
	public void register( final Long userId, final Session session )
	{
		lock.lock();
		userSessionMap.put(userId, session);
		lock.unlock();
	}
	
	public void unregister( final Long userId )
	{
		lock.lock();
		userSessionMap.remove(userId);
		lock.unlock();
	}
	
	@Override
	public void onSendMessage(ChatMessagePacket messagePacket)
	{
		try
		{
			lock.lock();
			
			if ( messagePacket.getInfo() )
			{
				manageInfoMessagePacket(messagePacket);
				return;
			}
			
			final Long senderId = messagePacket.getSenderId();
			final Long receiverId = messagePacket.getReceiverId();
			
			if ( senderId.equals(receiverId) )
				return;
			
			// complete message packet info
			messagePacket.setAck(false);
			messagePacket.setTime(DateUtil.getCurrentClockTime());
			
			// store message to DB
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			final User sender = daoFactory.getUserDao().findByPrimaryKey( senderId );
			if ( sender == null ) return;
			final User receiver = daoFactory.getUserDao().findByPrimaryKey( receiverId );
			if ( receiver == null ) return;
			
			final ChatMessage message = new ChatMessage(messagePacket, sender, receiver);
			final boolean saved = DBManager.getInstance().getDaoFactory().getChatMessageDao().save(message);
			if ( !saved ) return;
			
			ChatManager.sendMessageFromSession(messagePacket, userSessionMap.get(receiverId));
			
			// send ACK to sender
			messagePacket.setAck(true);
			ChatManager.sendMessageFromSession(messagePacket, userSessionMap.get(senderId));
		}
		finally 
		{
			lock.unlock();
		}
	}
	
	private void manageInfoMessagePacket( final ChatMessagePacket infoMessagePacket )
	{
		if ( !infoMessagePacket.getInfo() )
			return;
		
		final String infoText = infoMessagePacket.getText();
		final Long userId = infoMessagePacket.getSenderId();
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		if ( infoText.equals("readall") )
		{
			daoFactory.getChatMessageDao().readAllUser(userId);
		}
		else if ( infoText.equals("starttyping") || infoText.equals("stoptyping") )
		{
			final User user = daoFactory.getUserDao().findByPrimaryKey(userId);
			final List<User> friends = daoFactory.getUserDao().findFriends(user, Integer.MAX_VALUE);
			
			Long friendId;
			for ( final User friend : friends )
			{
				friendId = friend.getId();
				if ( userSessionMap.containsKey(friendId) )
					ChatManager.sendMessageFromSession(infoMessagePacket, userSessionMap.get(friendId));
			}
		}
	}
	
}
