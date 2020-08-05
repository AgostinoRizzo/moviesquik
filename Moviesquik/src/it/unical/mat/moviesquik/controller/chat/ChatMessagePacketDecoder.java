/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class ChatMessagePacketDecoder implements Decoder.Text<ChatMessagePacket>
{
	private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig arg0)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public ChatMessagePacket decode(String s) throws DecodeException
	{
		return GSON.fromJson(s, ChatMessagePacket.class);
	}

	@Override
	public boolean willDecode(String s)
	{
		return s!=null;
	}
	
}
