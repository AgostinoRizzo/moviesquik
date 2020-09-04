/**
 * 
 */
package it.unical.mat.moviesquik.model.chat;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class ChatMessageProxy extends ChatMessage
{
	private Long senderId = null;
	private Long receiverId = null;
	private Long moviePartyId = null;
	
	public void setSenderId(Long senderId)
	{
		this.senderId = senderId;
	}
	public Long getSenderId()
	{
		return senderId;
	}
	
	public void setReceiverId(Long receiverId)
	{
		this.receiverId = receiverId;
	}
	public Long getReceiverId()
	{
		return receiverId;
	}
	
	public void setMoviePartyId(Long moviePartyId)
	{
		this.moviePartyId = moviePartyId;
	}
	public Long getMoviePartyId()
	{
		return moviePartyId;
	}
	
	@Override
	public User getSender()
	{
		if ( sender == null && senderId != null )
			sender = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(senderId);
		return sender;
	}
	
	@Override
	public User getReceiver()
	{
		if ( receiver == null && receiverId != null )
			receiver = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(receiverId);
		return receiver;
	}
	
	@Override
	public MovieParty getMovieParty()
	{
		if ( movieParty == null && moviePartyId != null )
			movieParty = DBManager.getInstance().getDaoFactory().getMoviePartyDao().findById(moviePartyId);
		return movieParty;
	}
}
