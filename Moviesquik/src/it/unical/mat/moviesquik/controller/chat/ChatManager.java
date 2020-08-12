/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import javax.websocket.Session;

import it.unical.mat.moviesquik.util.WebsocketUtil;

/**
 * @author Agostino
 *
 */
public interface ChatManager
{
	public void onSendMessage( final ChatMessagePacket messagePacket );
	
	static boolean sendMessageFromSession( final ChatMessagePacket messagePacket, final Session session )
	{
		return WebsocketUtil.<ChatMessagePacket>sendPacketFromSession( messagePacket, session );
	}
}
