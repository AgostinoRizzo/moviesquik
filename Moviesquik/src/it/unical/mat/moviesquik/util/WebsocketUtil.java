/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * @author Agostino
 *
 */
public class WebsocketUtil
{
	public static <T> boolean sendPacketFromSession( final T packet, final Session session )
	{
		if ( session == null )
			return false;
		try
		{ session.getBasicRemote().sendObject(packet); return true;	} 
		catch (IOException | EncodeException e)
		{ return false; }
	}
}
