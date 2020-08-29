/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

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
@ServerEndpoint(value="/movieparty/interval/{party_id}",
				encoders=MoviePartyIntervalPacketEncoder.class, 
				decoders=MoviePartyIntervalPacketDecoder.class)
public class MoviePartyIntervalCommunicator
{
	private static final MoviePartyIntervalManager moviePartyIntervalManager = 
			MoviePartyIntervalManager.getInstance();
	
	@OnOpen
	public void onOpen( final Session session, @PathParam("party_id")  final String partyIdStr )
	{
		try
		{
			final Long partyId  = Long.parseLong(partyIdStr);
			moviePartyIntervalManager.register(session, partyId);
		}
		catch (NumberFormatException e) {}
	}
	
	@OnMessage
	public void onMessage( final MoviePartyIntervalPacket intervalPacket, final Session session ) throws IOException, EncodeException
	{
		moviePartyIntervalManager.onIntervalPacket(intervalPacket, session);
	}
	
	@OnClose
	public void onClose( final Session session, @PathParam("party_id")  final String partyIdStr )
	{
		moviePartyIntervalManager.unregister( session );
	}
	
}
