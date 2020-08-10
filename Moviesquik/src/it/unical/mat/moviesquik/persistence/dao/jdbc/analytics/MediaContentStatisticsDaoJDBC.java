/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc.analytics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unical.mat.moviesquik.model.analytics.MediaContentStatistics;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentStatisticsDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.AbstractDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.StatementPrompterJDBC;
import it.unical.mat.moviesquik.util.MathUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentStatisticsDaoJDBC extends AbstractDaoJDBC<MediaContentStatistics> implements MediaContentStatisticsDao
{
	protected static final String FIND_QUERY = "select * from media_content_statistics where media_content_id = ?";
	
	public MediaContentStatisticsDaoJDBC(StatementPrompterJDBC statementPrompter)
	{
		super(statementPrompter);
	}

	@Override
	public MediaContentStatistics find(Long mediaContentId)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_QUERY);
			statement.setLong(1, mediaContentId);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createFromResult(result);
			
			return createEmptyStatistics(mediaContentId);
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return createEmptyStatistics(mediaContentId); }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	protected MediaContentStatistics createFromResult(ResultSet result) throws SQLException
	{
		final MediaContentStatistics stats = new MediaContentStatistics();
		
		// FIND_QUERY do not returns NULL values
		final Long rateCount = result.getLong("rate_count");
		final Integer[] ratingBreakdown = { 
											computeRateBreakdownPercentage(result.getLong("rate_5_count"), rateCount),
											computeRateBreakdownPercentage(result.getLong("rate_4_count"), rateCount),
											computeRateBreakdownPercentage(result.getLong("rate_3_count"), rateCount),
											computeRateBreakdownPercentage(result.getLong("rate_2_count"), rateCount),
											computeRateBreakdownPercentage(result.getLong("rate_1_count"), rateCount) 
										  };
		final Float actualRate = computeRate(result.getFloat("avg_rate"), result.getFloat("imdb_rate"), false);
		stats.setRate( Math.round(actualRate) );
		stats.setRatingBreakdown(ratingBreakdown);
		stats.setActualRate( MathUtil.roundRateValue(actualRate) );
		stats.setLikes(result.getLong("likes_count"));
		stats.setNolikes(result.getLong("nolikes_count"));
		stats.setViews(result.getLong("views_count"));
		
		return stats;
	}
	
	private Integer computeRateBreakdownPercentage( final Long rateBreakdownCount, final Long rateCount )
	{
		if ( rateCount == 0 )
			return 0;
		return (int) (rateBreakdownCount / rateCount * 100);
	}
	
	private Float computeRate( final Float avgRate, final Float imdbRate, final boolean percentage )
	{
		final Float mappedImdbRate = mapImdbRate(imdbRate);
		
		if ( percentage )
			return (avgRate * 0.8f) + (mappedImdbRate * 0.2f); // compute rate based on 80% of avgRate and 20% of mappedImdbRate
		
		return avgRate == 0.0f ? mappedImdbRate : avgRate;
	}
	
	private Float mapImdbRate( final Float imdbRate )
	{
		// map imdbRate from 0-10 to 0-5 interval
		return imdbRate * 5.0f / 10.0f;
	}
	
	private MediaContentStatistics createEmptyStatistics( final Long mediaContentId )
	{
		final MediaContentStatistics stats = new MediaContentStatistics();
		final Integer[] ratingBreakdown = { 0, 0, 0, 0, 0 };
		final MediaContent mediaContent = DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaContentId);
		
		final Float actualRate = mediaContent != null ? mapImdbRate(mediaContent.getRating()) : 0.0f;
		stats.setRate( Math.round(actualRate) );
		stats.setRatingBreakdown(ratingBreakdown);
		stats.setActualRate( MathUtil.roundRateValue(actualRate) );
		stats.setLikes((long) 0);
		stats.setNolikes((long) 0);
		stats.setViews((long) 0);
		
		return stats;
	}
	
}
