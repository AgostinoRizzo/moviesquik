/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Agostino
 *
 */
@ServerEndpoint(value="/groupchat/{group_id}/{user_id}", 
				encoders=ChatMessagePacketEncoder.class, 
				decoders=ChatMessagePacketDecoder.class)
public class GruopChatCommunicatorEndpoint
{
	private static final GroupChatManager groupChatManager = GroupChatManager.getInstance();
	
	@OnOpen
	public void onOpen( final Session session, @PathParam("group_id") final String groupIdStr, 
											   @PathParam("user_id")  final String userIdStr )
	{
		try
		{
			final Long groupId = Long.parseLong(groupIdStr);
			final Long userId  = Long.parseLong(userIdStr);
			
			groupChatManager.register( userId, session, groupId );
		}
		catch (NumberFormatException e) {}
	}
	
	@OnMessage
	public void onMessage( final ChatMessagePacket messagePacket, final Session session ) throws IOException, EncodeException
	{
		groupChatManager.onSendMessage(messagePacket);
	}
	
	@OnClose
	public void onClose( final Session session, @PathParam("group_id") final String groupIdStr, 
												@PathParam("user_id")  final String userIdStr )
	{
		try
		{
			final Long groupId = Long.parseLong(groupIdStr);
			final Long userId  = Long.parseLong(userIdStr);
			
			groupChatManager.unregister( userId, groupId );
		}
		catch (NumberFormatException e) {}
	}
	
	@OnError
	public void onError( final Session session, Throwable throwable )
	{}
	
}
