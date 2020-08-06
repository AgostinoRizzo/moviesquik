/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.chat.ChatMessage;

/**
 * @author Agostino
 *
 */
public interface ChatMessageDao
{
	public boolean save( final ChatMessage message );
}
