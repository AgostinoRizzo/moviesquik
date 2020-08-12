/**
 * 
 */
package it.unical.mat.moviesquik.controller.notification;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class NotificationPacketEncoder implements Encoder.Text<NotificationPacket>
{
	private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig config)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public String encode(NotificationPacket notificationPacket) throws EncodeException
	{
		return GSON.toJson(notificationPacket);
	}

}
