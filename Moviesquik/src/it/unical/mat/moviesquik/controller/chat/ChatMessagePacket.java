/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.chat.ChatMessage;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class ChatMessagePacket
{
	private String text;
	private Long id;
	private boolean ack = false;
	private String time;
	private Long senderId;
	private String senderIconSrc;
	private Long receiverId;
	
	public ChatMessagePacket()
	{}
	public ChatMessagePacket( final ChatMessage message )
	{
		final User sender = message.getSender();
		final User receiverUser = message.getReceiver();
		
		text = message.getText();
		id = message.getId();
		time = DateUtil.getClockTime(message.getDateTime());
		senderId = sender.getId();
		senderIconSrc = sender.getProfileImagePath();
		receiverId = receiverUser == null ? message.getMovieParty().getId() : receiverUser.getId();
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
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
	public Long getReceiverId()
	{
		return receiverId;
	}
	public void setReceiverId(Long receiverId)
	{
		this.receiverId = receiverId;
	}
	
}
