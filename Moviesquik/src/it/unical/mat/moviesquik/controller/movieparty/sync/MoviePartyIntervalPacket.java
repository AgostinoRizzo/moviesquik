/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

/**
 * @author Agostino
 *
 */
public class MoviePartyIntervalPacket
{
	private Long senderId;
	private boolean isRequest;
	private boolean interval;
	
	public Long getSenderId()
	{
		return senderId;
	}
	public void setSenderId(Long senderId)
	{
		this.senderId = senderId;
	}
	
	public boolean isRequest()
	{
		return isRequest;
	}
	public void setRequest(boolean isRequest)
	{
		this.isRequest = isRequest;
	}
	
	public boolean getInterval()
	{
		return interval;
	}
	public void setInterval(boolean status)
	{
		this.interval = status;
	}
	
}
