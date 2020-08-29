/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class MoviePartyIntervalPacketEncoder implements Encoder.Text<MoviePartyIntervalPacket>
{
	private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig config)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public String encode(MoviePartyIntervalPacket intervalPacket) throws EncodeException
	{
		return GSON.toJson(intervalPacket);
	}
}
