/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.unical.mat.moviesquik.model.analytics.MediaContentReview;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentReviewDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class MediaContentReviewDaoJDBC extends AbstractDaoJDBC<MediaContentReview> implements MediaContentReviewDao
{
	protected static final String INSERT_STATEMENT = "INSERT INTO review(user_id, media_content_id, rate, like) VALUES (?,?,?,?) " +
													 "ON CONFLICT (user_id, media_content_id) DO UPDATE SET rate = EXCLUDED.rate, like = EXCLUDED.like";
	
	protected static final String FIND_QUERY       = "SELECT * FROM review WHERE user_id = ? AND media_content_id = ?";
	
	
	public MediaContentReviewDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public boolean save(MediaContentReview review)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			statement.setLong(1, review.getSubject().getId());
			statement.setLong(2, review.getMediaContent().getId());
			statement.setInt (3, review.getRate());
			
			final Boolean like = review.getLike();
			if ( like == null ) statement.setNull(4, Types.NULL);
			else                statement.setBoolean(4, like);
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public MediaContentReview find(Long subjectUserId, Long mediaContentId)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_QUERY);
			statement.setLong(1, subjectUserId);
			statement.setLong(2, mediaContentId);
			
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
	protected MediaContentReview createFromResult(ResultSet result) throws SQLException
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final MediaContentReview review = new MediaContentReview();
		
		review.setSubject( daoFactory.getUserDao().findByPrimaryKey(result.getLong("user_id")) );
		review.setMediaContent( daoFactory.getMediaContentDao().findById(result.getLong("media_content_id")) );
		review.setRate(result.getInt("rate"));
		
		final Boolean like = result.getBoolean("link");
		review.setLike( result.wasNull() ? null : like );
		
		return review;
	}
	
}
