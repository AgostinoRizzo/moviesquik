/**
 * 
 */
package it.unical.mat.moviesquik.model.business;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class CDNUsageDataReceiver extends Thread
{
public static final short CDN_USAGE_SAMPLES_EXCANGE_PORT = 3001;
	
	private static final long OPEN_SOCKET_INTERVAL = 3000;  // expressed in milliseconds.
	private static final long RECV_PACKET_INTERVAL = 3000;  // expressed in milliseconds.
	private static final int  RECV_BUFFER_CAPACITY = 1024;
	
	private static CDNUsageDataReceiver instance = null;
	
	private DatagramSocket socket = null;
	private byte[] recv_buffer = null;
	
	public static CDNUsageDataReceiver getInstance()
	{
		if ( instance == null )
			instance = new CDNUsageDataReceiver();
		return instance;
	}
	private CDNUsageDataReceiver()
	{
		setDaemon(true);
	}
	@Override
	public void run()
	{
		openSocket();
		recv_buffer = new byte[RECV_BUFFER_CAPACITY];
		
		final DatagramPacket packet = new DatagramPacket(recv_buffer, recv_buffer.length);
		
		while (true)
			try
			{
				socket.receive(packet);
				
				final String data = new String(packet.getData()).trim();
				final JsonObject jsonDataObject = JSONUtil.fromStringToJsonObject(data);
				final String serverKey = jsonDataObject.get("key").getAsString();
				final JsonArray jsonArraySamples = jsonDataObject.getAsJsonArray("samples");
				
				final int arraySize = jsonArraySamples.size();
				final Float[] samplesArray = new Float[arraySize];
				
				for ( int i=0; i<arraySize; ++i )
					samplesArray[i] = jsonArraySamples.get(i).getAsFloat();
				
				CDNUsageChart.getInstance().update(serverKey, samplesArray);
			} 
			catch (IOException e)
				{ waitForInterval(RECV_PACKET_INTERVAL); }
		
	}
	private void openSocket()
	{
		boolean opened = false;
		do
			try
			{
				socket = new DatagramSocket(CDN_USAGE_SAMPLES_EXCANGE_PORT);
				opened = true;
			} 
			catch (SocketException e)
				{ waitForInterval(OPEN_SOCKET_INTERVAL); }
		while ( !opened );
	}
	
	private static void waitForInterval( final long millis )
	{
		try
		{ Thread.sleep(millis); } 
		catch (InterruptedException e)
		{}
	}
}
