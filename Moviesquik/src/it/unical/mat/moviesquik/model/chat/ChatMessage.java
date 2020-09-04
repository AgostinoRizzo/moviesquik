/**
 * 
 */
package it.unical.mat.moviesquik.model.chat;

import java.util.Date;

import it.unical.mat.moviesquik.controller.chat.ChatMessagePacket;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class ChatMessage
{
	protected Long id;
	protected String text;
	protected Date dateTime;
	protected User sender;
	protected User receiver;
	protected MovieParty movieParty;
	protected Boolean isRead;
	
	public ChatMessage()
	{}
	public ChatMessage( final ChatMessagePacket messagePacket, final User sender, final MovieParty movieParty )
	{		
		this.text = messagePacket.getText();
		this.dateTime = DateUtil.getCurrent();
		
		this.sender = sender;
		this.movieParty = movieParty;
	}
	public ChatMessage( final ChatMessagePacket messagePacket, final User sender, final User receiver )
	{		
		this.text = messagePacket.getText();
		this.dateTime = DateUtil.getCurrent();
		
		this.sender = sender;
		this.receiver = receiver;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Date getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	public User getSender()
	{
		return sender;
	}
	public void setSender(User sender)
	{
		this.sender = sender;
	}
	public User getReceiver()
	{
		return receiver;
	}
	public void setReceiver(User receiver)
	{
		this.receiver = receiver;
	}
	public MovieParty getMovieParty()
	{
		return movieParty;
	}
	public void setMovieParty(MovieParty movieParty)
	{
		this.movieParty = movieParty;
	}
	public Boolean getIsRead()
	{
		return isRead;
	}
	public void setIsRead(Boolean isRead)
	{
		this.isRead = isRead;
	}
	
}
