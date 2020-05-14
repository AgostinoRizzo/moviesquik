/**
 * 
 */
package it.unical.mat.moviesquik.model.streaming;

/**
 * @author Agostino
 *
 */
public class StreamService
{
	private final String serverAddress;
	private final int    servicePort;
	
	public StreamService( final String serverAddress, final int servicePort )
	{
		this.serverAddress = serverAddress;
		this.servicePort = servicePort;
	}
	public String getServerAddress()
	{
		return serverAddress;
	}
	public int getServicePort()
	{
		return servicePort;
	}
	public String getUrl()
	{
		return "http://" + serverAddress + ":" + servicePort;
	}
	@Override
	public boolean equals(Object obj)
	{
		if ( this == obj )
			return true;
		if ( obj instanceof StreamService )
		{
			final StreamService other = (StreamService) obj;
			return serverAddress.equals(other.serverAddress) && servicePort == other.servicePort;
		}
		return false;
	}
}
