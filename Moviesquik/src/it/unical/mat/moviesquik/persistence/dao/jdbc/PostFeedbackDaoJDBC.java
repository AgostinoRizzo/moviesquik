/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.posting.Post;
import it.unical.mat.moviesquik.model.posting.PostFeedback;
import it.unical.mat.moviesquik.persistence.dao.PostFeedbackDao;

/**
 * @author Agostino
 *
 */
public class PostFeedbackDaoJDBC extends AbstractDaoJDBC<PostFeedback> implements PostFeedbackDao
{
	protected static final String INSERT_STATEMENT             = "insert into post_feedback(feedback_id, is_like, user_id, post_id) values (?,?,?,?)";
	protected static final String FIND_BY_USER_POST_QUERY      = "select * from post_feedback where user_id = ? and post_id = ? and is_like = ?";
	
	protected PostFeedbackDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean save(PostFeedback feedback)
	{
		try
		{
			if ( findByUserPost( feedback.getOwner(), feedback.getReferredPost(), feedback.isLike() ) != null )
				return false;
			
			feedback.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong        (1, feedback.getId());
			statement.setBoolean     (2, feedback.isLike());
			statement.setLong        (3, feedback.getOwner().getId());
			statement.setLong        (4, feedback.getReferredPost().getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public PostFeedback findByUserPost(User user, Post post, boolean is_like)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_USER_POST_QUERY);
			
			statement.setLong(1, user.getId());
			statement.setLong(2, post.getId());
			statement.setBoolean(3, is_like);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createFromResult(result);
			
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected PostFeedback createFromResult(ResultSet result) throws SQLException
	{
		final PostFeedback feedback = new PostFeedback();
		
		feedback.setId(result.getLong("feedback_id"));
		feedback.setLike(result.getBoolean("is_like"));
		
		//final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		final Long subjectUserId = result.getLong("user_id");
		//feedback.setOwner(daoFactory.getUserDao().findByPrimaryKey(subjectUserId));
		final User owner = new User();
		owner.setId(subjectUserId);
		feedback.setOwner(owner);
		
		final Long referredPostId = result.getLong("post_id");
		//feedback.setReferredPost(daoFactory.getPostDao().findById(referredPostId));
		final Post referredPost = new Post();
		referredPost.setId(referredPostId);
		feedback.setReferredPost(referredPost);
		
		return feedback;
	}
}
