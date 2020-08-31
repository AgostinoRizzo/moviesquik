/**
 * 
 */
package it.unical.mat.moviesquik.controller.business.cdn;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Agostino
 *
 */
@ServerEndpoint(value="/business/cdn/usage/update", 
				encoders=CDNUsagePacketEncoder.class,
				decoders=CDNUsagePacketDecoder.class)
public class CDNUsageUpdaterEndpoint
{
	private static final CDNUsageUpdateManager cdnUsageUpdateManager = CDNUsageUpdateManager.getInstance();
	
	@OnOpen
	public void onOpen( final Session session )
	{
		cdnUsageUpdateManager.register( session );
	}
	
	@OnMessage
	public void onMessage( final CDNUsagePacket usagePacket, final Session session ) throws IOException, EncodeException
	{}
	
	@OnClose
	public void onClose( final Session session )
	{
		cdnUsageUpdateManager.unregister( session );
	}
	
	@OnError
	public void onError( final Session session, Throwable throwable )
	{}
	
}
