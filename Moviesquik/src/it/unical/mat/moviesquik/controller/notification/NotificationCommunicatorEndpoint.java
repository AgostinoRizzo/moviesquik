/**
 * 
 */
package it.unical.mat.moviesquik.controller.notification;

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
@ServerEndpoint(value="/notifsocket/{user_id}", 
				encoders=NotificationPacketEncoder.class, 
				decoders=NotificationPacketDecoder.class)
public class NotificationCommunicatorEndpoint
{
	private static final NotificationsManager notificationManager = NotificationsManager.getInstance();
	
	@OnOpen
	public void onOpen( final Session session, @PathParam("user_id")  final String userIdStr )
	{
		try
		{
			final Long userId  = Long.parseLong(userIdStr);
			notificationManager.register(userId, session);
		}
		catch (NumberFormatException e) {}
	}
	
	@OnMessage
	public void onMessage( final NotificationPacket notificationPacket, final Session session ) throws IOException, EncodeException
	{}
	
	@OnClose
	public void onClose( final Session session, @PathParam("user_id")  final String userIdStr )
	{
		try
		{
			final Long userId  = Long.parseLong(userIdStr);
			notificationManager.unregister( userId );
		}
		catch (NumberFormatException e) {}
	}
}
