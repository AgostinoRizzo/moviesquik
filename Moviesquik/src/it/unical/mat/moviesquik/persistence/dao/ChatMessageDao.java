/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.chat.ChatMessage;

/**
 * @author Agostino
 *
 */
public interface ChatMessageDao
{
	public boolean save( final ChatMessage message );
	public List<ChatMessage> findAllGroup( final Long groupId, final Long messageOffsetId );
	public List<ChatMessage> findAllUser( final Long userId, final Long messageOffsetId );
}
