/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.analytics.MediaContentSharing;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentSharingDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;

/**
 * @author Agostino
 *
 */
public class MediaContentSharingDaoJDBC extends AbstractDaoJDBC<MediaContentSharing> implements MediaContentSharingDao
{
	protected static final String FIND_SHORT_SHARING_QUERY = "SELECT total_sharing_rate FROM media_short_sharing_rate WHERE media_content_id = ?";
	protected static final String FIND_LONG_SHARING_QUERY = "SELECT total_sharing_rate FROM media_long_sharing_rate WHERE media_content_id = ?";

	public MediaContentSharingDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public MediaContentSharing findShortSharing(MediaContent mc)
	{
		return findSharing(mc, FIND_SHORT_SHARING_QUERY);
	}

	@Override
	public MediaContentSharing findLongSharing(MediaContent mc)
	{
		return findSharing(mc, FIND_LONG_SHARING_QUERY);
	}
	
	private MediaContentSharing findSharing( final MediaContent mc, final String findQuery )
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(findQuery);
			
			statement.setLong(1,mc.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createFromResult(result);
			
			return new MediaContentSharing();
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return new MediaContentSharing(); }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MediaContentSharing createFromResult(ResultSet result) throws SQLException
	{
		final MediaContentSharing sharing = new MediaContentSharing();
		sharing.setValue(result.getFloat("total_sharing_rate"));
		return sharing;
	}

}
