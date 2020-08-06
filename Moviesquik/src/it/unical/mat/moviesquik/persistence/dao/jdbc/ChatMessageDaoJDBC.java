/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.chat.ChatMessage;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.dao.ChatMessageDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class ChatMessageDaoJDBC extends AbstractDaoJDBC<ChatMessage> implements ChatMessageDao
{
	protected static final String INSERT_STATEMENT = 
			"insert into message(message_id, text, date_time, sender_id, receiver_id, movie_party_id) values (?,?,?,?,?,?)";
	
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
			statement.setTimestamp(3, DateUtil.toJDBC(message.getDateTime()));
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
	protected ChatMessage createFromResult(ResultSet result) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
