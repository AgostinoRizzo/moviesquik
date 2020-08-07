/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * @author Agostino
 *
 */
public interface ChatManager
{
	public void onSendMessage( final ChatMessagePacket messagePacket );
	
	static boolean sendMessageFromSession( final ChatMessagePacket messagePacket, final Session session )
	{
		if ( session == null )
			return false;
		try
		{ session.getBasicRemote().sendObject(messagePacket); return true;	} 
		catch (IOException | EncodeException e)
		{ return false; }
	}
}
