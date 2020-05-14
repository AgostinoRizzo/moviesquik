/**
 * 
 */
package it.unical.mat.moviesquik.model.streaming;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author Agostino
 *
 */
public class StreamServiceSync extends Thread
{
	public static final short SYNC_PORT = 3000;
	
	private static final long OPEN_SOCKET_INTERVAL = 3000;  // expressed in milliseconds.
	private static final long RECV_PACKET_INTERVAL = 3000;  // expressed in milliseconds.
	private static final int  RECV_BUFFER_CAPACITY = 1024;
	
	private static StreamServiceSync instance = null;
	
	private DatagramSocket socket = null;
	private byte[] recv_buffer = null;
	
	public static StreamServiceSync getInstance()
	{
		if ( instance == null )
			instance = new StreamServiceSync();
		return instance;
	}
	private StreamServiceSync()
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
				
				final InetAddress address = packet.getAddress();
				final String data = new String(packet.getData());
				System.out.println("Received data from " + address.getHostAddress() + ": " + data);
				
				sendAck(address);
				
				try
				{
					final int servicePort = Integer.parseInt(data.trim());
					StreamServiceTable.getInstance().onSync( new StreamService( address.getHostAddress(), servicePort ) );
				}
				catch (NumberFormatException e) {System.out.println("E");}
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
				socket = new DatagramSocket(SYNC_PORT);
				opened = true;
			} 
			catch (SocketException e)
				{ waitForInterval(OPEN_SOCKET_INTERVAL); }
		while ( !opened );
	}
	private void sendAck( final InetAddress address )
	{
		final byte[] data = "ACK".getBytes();
		final DatagramPacket packet = new DatagramPacket(data, data.length, address, SYNC_PORT);
		try
		{ socket.send(packet); } 
		catch (IOException e)
		{}
	}
	private static void waitForInterval( final long millis )
	{
		try
		{ Thread.sleep(millis); } 
		catch (InterruptedException e)
		{}
	}
}
