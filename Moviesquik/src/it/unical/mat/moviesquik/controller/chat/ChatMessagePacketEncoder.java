/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class ChatMessagePacketEncoder implements Encoder.Text<ChatMessagePacket>
{
	private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig config)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public String encode(ChatMessagePacket messagePacket) throws EncodeException
	{
		return GSON.toJson(messagePacket);
	}
	
}
