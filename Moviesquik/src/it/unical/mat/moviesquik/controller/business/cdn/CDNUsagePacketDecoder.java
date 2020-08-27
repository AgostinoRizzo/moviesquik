/**
 * 
 */
package it.unical.mat.moviesquik.controller.business.cdn;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class CDNUsagePacketDecoder implements Decoder.Text<CDNUsagePacket>
{
	private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig arg0)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public CDNUsagePacket decode(String s) throws DecodeException
	{
		return GSON.fromJson(s, CDNUsagePacket.class);
	}

	@Override
	public boolean willDecode(String s)
	{
		return s!=null;
	}
	
}
