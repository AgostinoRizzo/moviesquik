/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.chat.ChatMessage;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.ChatMessageDao;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class ChatMessageDaoJDBC extends AbstractDaoJDBC<ChatMessage> implements ChatMessageDao
{
	protected static final String INSERT_STATEMENT = 
			"insert into message(message_id, text, date_time, sender_id, receiver_id, movie_party_id) values (?,?,?,?,?,?)";
	
	protected static final String FIND_ALL_GROUP_QUERY = 
			"select * from message where movie_party_id = ? order by date_time desc";
	protected static final String FIND_ALL_GROUP_WITH_OFFSET_QUERY = 
			"select * from message where movie_party_id = ? and message_id <= ? order by date_time desc";
	
	protected static final String FIND_ALL_USER_QUERY = 
			"select * from message where movie_party_id is null and (sender_id = ? or receiver_id = ?) order by date_time desc";
	protected static final String FIND_ALL_USER_WITH_OFFSET_QUERY = 
			"select * from message where movie_party_id is null and (sender_id = ? or receiver_id = ?) and message_id <= ? order by date_time desc";
	
	protected static final String READ_ALL_USER_STATEMENT = 
			"UPDATE message SET is_read = true WHERE movie_party_id is null and receiver_id = ? AND NOT is_read";
	
	protected ChatMessageDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean save(ChatMessage message)
	{
		try
		{
			message.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong     (1, message.getId());
			statement.setString   (2, message.getText());
			statement.setTimestamp(3, DateUtil.toTimestampJDBC(message.getDateTime()));
			statement.setLong     (4, message.getSender().getId());
			
			final User receiver = message.getReceiver();
			if ( receiver != null ) statement.setLong(5, receiver.getId());
			else                    statement.setNull(5, Types.NULL);
			
			final MovieParty movieParty = message.getMovieParty();
			if ( movieParty != null ) statement.setLong(6, movieParty.getId());
			else                      statement.setNull(6, Types.NULL);
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<ChatMessage> findAllGroup(Long groupId, Long messageOffsetId)
	{
		final List<ChatMessage> messages = new ArrayList<ChatMessage>();
		
		try
		{
			final PreparedStatement statement = 
					statementPrompter.prepareStatement(messageOffsetId == null ? FIND_ALL_GROUP_QUERY : FIND_ALL_GROUP_WITH_OFFSET_QUERY);
			
			statement.setLong(1, groupId);
			if ( messageOffsetId != null )
				statement.setLong(2, messageOffsetId);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				messages.add(createFromResult(result));
			
			return messages;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return messages; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<ChatMessage> findAllUser(Long userId, Long messageOffsetId)
	{
		final List<ChatMessage> messages = new ArrayList<ChatMessage>();
		
		try
		{
			final PreparedStatement statement = 
					statementPrompter.prepareStatement(messageOffsetId == null ? FIND_ALL_USER_QUERY : FIND_ALL_USER_WITH_OFFSET_QUERY);
			
			statement.setLong(1, userId);
			statement.setLong(2, userId);
			if ( messageOffsetId != null )
				statement.setLong(3, messageOffsetId);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				messages.add(createFromResult(result));
			
			return messages;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return messages; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean readAllUser(Long userId)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(READ_ALL_USER_STATEMENT);
			statement.setLong(1, userId);
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected ChatMessage createFromResult(ResultSet result) throws SQLException
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final ChatMessage message = new ChatMessage();
		
		message.setId(result.getLong("message_id"));
		message.setText(result.getString("text"));
		message.setDateTime( DateUtil.toJava(result.getTimestamp("date_time")) );
		
		final User sender = daoFactory.getUserDao().findByPrimaryKey(result.getLong("sender_id"));
		message.setSender( sender );
		
		final Long receiverId = result.getLong("receiver_id");
		if ( receiverId != null )
			message.setReceiver( daoFactory.getUserDao().findByPrimaryKey(receiverId) );
		
		final Long moviePartyId = result.getLong("movie_party_id");
		if ( moviePartyId != null )
			message.setMovieParty( daoFactory.getMoviePartyDao().findById(moviePartyId, sender) );
		
		if ( message.getMovieParty() == null )
			message.setIsRead(result.getBoolean("is_read"));
		else
			message.setIsRead(true);
		
		return message;
	}

}
