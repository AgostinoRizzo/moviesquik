/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Agostino
 *
 */
@ServerEndpoint(value="/userchat/{user_id}", 
				encoders=ChatMessagePacketEncoder.class, 
				decoders=ChatMessagePacketDecoder.class)
public class UserChatCommunicatorEndpoint
{
	private static final UserChatManager userChatManager = UserChatManager.getInstance();
	
	@OnOpen
	public void onOpen( final Session session, @PathParam("user_id")  final String userIdStr )
	{
		try
		{
			final Long userId  = Long.parseLong(userIdStr);
			userChatManager.register(userId, session);
		}
		catch (NumberFormatException e) {}
	}
	
	@OnMessage
	public void onMessage( final ChatMessagePacket messagePacket, final Session session ) throws IOException, EncodeException
	{
		userChatManager.onSendMessage(messagePacket);
	}
	
	@OnClose
	public void onClose( final Session session, @PathParam("user_id")  final String userIdStr )
	{
		try
		{
			final Long userId  = Long.parseLong(userIdStr);
			userChatManager.unregister( userId );
		}
		catch (NumberFormatException e) {}
	}
	
}
