/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.analytics.AnalyticsExtractorDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.media.MediaContentDaoJDBC;

/**
 * @author Agostino
 *
 */
public class AnalyticsExtractorDaoJDBC extends AbstractDaoJDBC<MediaContent> implements AnalyticsExtractorDao
{
	protected static final String FIND_MOST_POPULAR_QUERY = "SELECT MC.* FROM media_long_sharing_rate AS MLSHAR INNER JOIN media_content AS MC " + 
															"ON MLSHAR.media_content_id = MC.media_content_id " + 
															"ORDER BY MLSHAR.total_sharing_rate DESC LIMIT ? OFFSET ?";
	protected static final String FIND_TRENDING_NOW_QUERY = "SELECT MC.* FROM media_short_sharing_rate AS MLSHAR INNER JOIN media_content AS MC " + 
															"ON MLSHAR.media_content_id = MC.media_content_id " + 
															"ORDER BY MLSHAR.total_sharing_rate DESC LIMIT ? OFFSET ?";
	protected static final String FIND_SUGGESTED_QUERY    = "SELECT MC.*, AVG(genre_rate) AS avg_genre_rate\r\n" + 
															"FROM user_genre_rates INNER JOIN media_content AS MC " + 
															"ON lower(MC.genre) like lower(concat('%', user_genre_rates.genre, '%')) " + 
															"WHERE user_id = ? " + 
															"GROUP BY MC.media_content_id " + 
															"ORDER BY avg_genre_rate DESC LIMIT ? OFFSET ?";
	
	public AnalyticsExtractorDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public List<MediaContent> findMostPopular( final DataListPage page )
	{
		return simpleFind(FIND_MOST_POPULAR_QUERY, page);
	}

	@Override
	public List<MediaContent> findTrendingNow( final DataListPage page )
	{
		return simpleFind(FIND_TRENDING_NOW_QUERY, page);
	}
	
	@Override
	public List<MediaContent> findSuggested(Long subjectId, DataListPage page)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_SUGGESTED_QUERY);
			
			statement.setLong(1, subjectId);
			statement.setInt (2, page.getLimit());
			statement.setInt (3, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				mediaContents.add(createFromResult(result));
			
			return mediaContents;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return mediaContents; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MediaContent createFromResult(ResultSet result) throws SQLException
	{
		return MediaContentDaoJDBC.createFromResult(result);
	}
	
	private List<MediaContent> simpleFind( final String query, final DataListPage page )
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(query);
			
			statement.setInt(1, page.getLimit());
			statement.setInt(2, page.getOffset());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				mediaContents.add(createFromResult(result));
			
			return mediaContents;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return mediaContents; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

}
