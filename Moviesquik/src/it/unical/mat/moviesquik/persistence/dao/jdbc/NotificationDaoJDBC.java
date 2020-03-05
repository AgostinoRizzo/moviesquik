/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Notification;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.NotificationDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class NotificationDaoJDBC implements NotificationDao
{
	protected static final String INSERT_STATEMENT          = "insert into notification(notification_id, date_time, title, description, is_read, user_id, subject_user_id) values (?,?,?,?,?,?,?)";
	protected static final String FIND_BY_USER_QUERY        = "select * from notification where user_id = ? order by date_time desc limit ?";
	protected static final String FIND_UNREAD_BY_USER_QUERY = "select * from notification where user_id = ? and is_read is false order by date_time desc limit ?";
	
	private final StatementPrompterJDBC statementPrompter;
	
	public NotificationDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(Notification notification, User user)
	{
		try
		{
			notification.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong        (1, notification.getId());
			statement.setTimestamp   (2, DateUtil.toJDBC(notification.getDateTime()));
			statement.setString      (3, notification.getTitle());
			statement.setString      (4, notification.getDescription());
			statement.setBoolean     (5, notification.getIsRead());
			statement.setLong        (6, user.getId());
			statement.setLong        (7, notification.getSubjectUser().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<Notification> findByUser(User user, int limit)
	{
		final List<Notification> notifications = new ArrayList<Notification>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_USER_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setInt(2, limit);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				notifications.add(createFromResult(result));
			
			return notifications;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return notifications; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<Notification> findUnreadByUser(User user, int limit)
	{
		final List<Notification> notifications = new ArrayList<Notification>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_UNREAD_BY_USER_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setInt(2, limit);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				notifications.add(createFromResult(result));
			
			return notifications;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return notifications; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	private Notification createFromResult( final ResultSet result ) throws SQLException
	{
		final Notification notification = new Notification();
		
		notification.setId(result.getLong("notification_id"));
		notification.setDateTime(DateUtil.toJava(result.getTimestamp("date_time")));
		notification.setTitle(result.getString("title"));
		notification.setDescription(result.getString("description"));
		notification.setIsRead(result.getBoolean("is_read"));
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Long subjectUserId = result.getLong("subject_user_id");
		
		if ( result.wasNull() )
			notification.setSubjectUser(null);
		else
			notification.setSubjectUser(daoFactory.getUserDao().findByPrimaryKey(subjectUserId));
		
		return notification;
	}

}
