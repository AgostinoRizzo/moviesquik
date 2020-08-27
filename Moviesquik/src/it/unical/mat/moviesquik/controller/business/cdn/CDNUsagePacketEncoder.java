/**
 * 
 */
package it.unical.mat.moviesquik.controller.business.cdn;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class CDNUsagePacketEncoder implements Encoder.Text<CDNUsagePacket>
{
private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig config)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public String encode(CDNUsagePacket usagePacket) throws EncodeException
	{
		return GSON.toJson(usagePacket);
	}
}
