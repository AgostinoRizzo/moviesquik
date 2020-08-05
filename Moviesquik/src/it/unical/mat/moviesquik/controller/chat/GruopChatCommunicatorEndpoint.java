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

import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
@ServerEndpoint(value="/groupchat/{group_id}/{user_id}", 
				encoders=ChatMessagePacketEncoder.class, 
				decoders=ChatMessagePacketDecoder.class)
public class GruopChatCommunicatorEndpoint
{
	@OnOpen
	public void onOpen( final Session session, @PathParam("group_id") final String groupIdStr, 
											   @PathParam("user_id")  final String userIdStr )
	{
		System.out.println("New group chat connecton [group_id: " + groupIdStr + ", user_id: " + userIdStr + "]: " + session.getId());
	}
	
	@OnMessage
	public void onMessage( final ChatMessagePacket messagePacket, final Session session ) throws IOException, EncodeException
	{
		System.out.println("On message packet from " + session.getId() + ": " + messagePacket.getText());
		
		messagePacket.setAck(true);
		messagePacket.setTime(DateUtil.getCurrentClockTime());
		session.getBasicRemote().sendObject(messagePacket);
		
		final ChatMessagePacket newMessagePacket = new ChatMessagePacket();
		newMessagePacket.setAck(false);
		newMessagePacket.setText(messagePacket.getText());
		newMessagePacket.setSenderIconSrc(messagePacket.getSenderIconSrc());
		newMessagePacket.setSenderId((long) 3540);
		newMessagePacket.setTime(DateUtil.getCurrentClockTime());
		session.getBasicRemote().sendObject(newMessagePacket);
	}
	
	@OnClose
	public void onClose( final Session session )
	{
		System.out.println("On close: " + session.getId());
	}
}
