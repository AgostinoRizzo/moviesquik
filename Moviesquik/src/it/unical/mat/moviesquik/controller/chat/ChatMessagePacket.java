/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

/**
 * @author Agostino
 *
 */
public class ChatMessagePacket
{
	private String text;
	private int id;
	private boolean ack;
	private String time;
	private Long senderId;
	private String senderIconSrc;
	
	public ChatMessagePacket()
	{}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public boolean getAck()
	{
		return ack;
	}
	public void setAck(boolean ack)
	{
		this.ack = ack;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public Long getSenderId()
	{
		return senderId;
	}
	public void setSenderId(Long senderId)
	{
		this.senderId = senderId;
	}
	public String getSenderIconSrc()
	{
		return senderIconSrc;
	}
	public void setSenderIconSrc(String senderIconSrc)
	{
		this.senderIconSrc = senderIconSrc;
	}
	
}
