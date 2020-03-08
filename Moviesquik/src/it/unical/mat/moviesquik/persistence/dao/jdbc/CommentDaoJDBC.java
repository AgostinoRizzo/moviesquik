/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Comment;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.CommentDao;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class CommentDaoJDBC extends AbstractDaoJDBC<Comment> implements CommentDao
{
	protected static final String INSERT_STATEMENT             = "insert into comment(comment_id, date_time, text, user_id, post_id) values (?,?,?,?,?)";
	protected static final String FIND_BY_POST_QUERY           = "select * from comment where post_id = ? order by date_time desc limit ?";
	
	protected CommentDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}
	
	@Override
	public boolean save(Comment comment)
	{
		try
		{
			comment.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong        (1, comment.getId());
			statement.setTimestamp   (2, DateUtil.toJDBC(comment.getDateTime()));
			statement.setString      (3, comment.getText());
			statement.setLong        (4, comment.getOwner().getId());
			statement.setLong        (5, comment.getReferredPost().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public List<Comment> findByPost(Post post, int limit)
	{
		final List<Comment> comments = new ArrayList<Comment>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_POST_QUERY);
			
			statement.setLong(1, post.getId());
			statement.setInt(2, limit);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				comments.add(createFromResult(result));
			
			return comments;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return comments; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	protected Comment createFromResult(ResultSet result) throws SQLException
	{
		final Comment comment = new Comment();
		
		comment.setId(result.getLong("comment_id"));
		comment.setDateTime(DateUtil.toJava(result.getTimestamp("date_time")));
		comment.setText(result.getString("text"));
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Long subjectUserId = result.getLong("user_id");
		
		if ( result.wasNull() )
			comment.setOwner(null);
		else
			comment.setOwner(daoFactory.getUserDao().findByPrimaryKey(subjectUserId));
		
		return comment;
	}
}
