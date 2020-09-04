/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import it.unical.mat.moviesquik.model.chat.ChatMessageProxy;
import it.unical.mat.moviesquik.model.chat.ChatUserIconKeeper;
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
	private boolean isRead;
	private boolean info;
	
	public ChatMessagePacket()
	{}
	public ChatMessagePacket( final ChatMessageProxy message )
	{
		final Long senderId = message.getSenderId();
		final Long receiverId = message.getReceiverId();
		
		text = message.getText();
		id = message.getId();
		time = DateUtil.getClockTime(message.getDateTime());
		this.senderId = senderId;
		
		final String senderIcon = ChatUserIconKeeper.getInstance().getUserIcon(senderId);
		if ( senderIcon != null )
			senderIconSrc = senderIcon;
		else
		{
			senderIconSrc = message.getSender().getProfileImagePath();
			ChatUserIconKeeper.getInstance().keepUserIcon(senderId, senderIconSrc);
		}
		this.receiverId = receiverId == null ? message.getMoviePartyId() : receiverId;
		isRead = message.getIsRead();
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
	public boolean getIsRead()
	{
		return isRead;
	}
	public void setIsRead(boolean isRead)
	{
		this.isRead = isRead;
	}
	public boolean getInfo()
	{
		return info;
	}
	public void setInfo(boolean info)
	{
		this.info = info;
	}
	
}
