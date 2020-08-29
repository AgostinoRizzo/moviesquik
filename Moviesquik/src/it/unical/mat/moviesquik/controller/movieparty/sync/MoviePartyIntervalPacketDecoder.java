/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

/**
 * @author Agostino
 *
 */
public class MoviePartyIntervalPacketDecoder implements Decoder.Text<MoviePartyIntervalPacket>
{
private static final Gson GSON = new Gson();
	
	@Override
	public void init(EndpointConfig arg0)
	{}
	
	@Override
	public void destroy()
	{}

	@Override
	public MoviePartyIntervalPacket decode(String s) throws DecodeException
	{
		return GSON.fromJson(s, MoviePartyIntervalPacket.class);
	}

	@Override
	public boolean willDecode(String s)
	{
		return s!=null;
	}
	
}
