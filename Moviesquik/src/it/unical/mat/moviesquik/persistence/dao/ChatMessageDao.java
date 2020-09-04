/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.chat.ChatMessage;
import it.unical.mat.moviesquik.model.chat.ChatMessageProxy;

/**
 * @author Agostino
 *
 */
public interface ChatMessageDao
{
	public boolean save( final ChatMessage message );
	public List<ChatMessageProxy> findAllGroup( final Long groupId, final Long messageOffsetId );
	public List<ChatMessageProxy> findAllUser( final Long userId, final Long messageOffsetId );
	public boolean readAllUser( final Long userId );
}
