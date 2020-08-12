/**
 * 
 */
package it.unical.mat.moviesquik.controller.notification;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class NotificationPacketDecoder implements Decoder.Text<NotificationPacket>
{
	private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig arg0)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public NotificationPacket decode(String s) throws DecodeException
	{
		return GSON.fromJson(s, NotificationPacket.class);
	}

	@Override
	public boolean willDecode(String s)
	{
		return s!=null;
	}
	
}
