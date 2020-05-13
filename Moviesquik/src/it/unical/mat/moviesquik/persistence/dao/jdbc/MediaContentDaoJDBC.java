/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.persistence.dao.MediaContentDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentDaoJDBC implements MediaContentDao
{
	protected static final String INSERT_STATEMENT = 
			"insert into media_content(media_content_id, title, type, year, release_date, runtime, genre, plot, poster, production, director, actors, rating) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	protected static final String UPDATE_RATING_STATEMENT = "update media_content SET rating = ? WHERE media_content_id = ?";
	protected static final String UPDATE_NEW_VIEW_STATEMENT = "update media_content SET views = views + 1 WHERE media_content_id = ?";
	protected static final String FIND_BY_PRIMARY_KEY_QUERY = "select * from media_content where media_content_id = ?";
	protected static final String FIND_BY_TITLE_YEAR_QUERY = "select * from media_content where title = ? and year = ?";
	
	
	private final StatementPrompterJDBC statementPrompter;
	
	public MediaContentDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(MediaContent mediaContent)
	{
		try
		{
			mediaContent.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			setDataToInsertStatement(mediaContent, statement);
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public boolean updateRatings(MediaContent mediaContent)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_RATING_STATEMENT);
			
			statement.setFloat(1, mediaContent.getRating());
			statement.setLong (2, mediaContent.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<MediaContent> findByTitleYear(String title, short year)
	{
		final List<MediaContent> mediaContents = new ArrayList<MediaContent>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_TITLE_YEAR_QUERY);
			statement.setString(1, title);
			statement.setShort(2, year);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				mediaContents.add( createFromResult(result) );
			return mediaContents;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return mediaContents; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public MediaContent getMediaContentOfTheDay()
	{
		final List<MediaContent> contents = findByTitleYear("The Lion King", (short) 2019);
		if ( contents.isEmpty() )
			return null;
		return contents.get(0);
	}
	
	@Override
	public MediaContent findById(Long id)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PRIMARY_KEY_QUERY);
			statement.setLong(1, id);
			
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
	public boolean updateNewViewById(Long id)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_NEW_VIEW_STATEMENT);
			
			statement.setLong (1, id);
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	protected static void setDataToInsertStatement( final MediaContent mediaContent, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong       (1 , mediaContent.getId());
		statement.setString     (2 , mediaContent.getTitle());
		statement.setString     (3 , mediaContent.getType());
		statement.setShort      (4 , mediaContent.getYear());
		statement.setTimestamp  (5 , DateUtil.toJDBC(mediaContent.getReleased()));
		statement.setString     (6 , mediaContent.getRuntime());
		statement.setString     (7 , mediaContent.getGenre());
		statement.setString     (8 , mediaContent.getPlot());
		statement.setString     (9 , mediaContent.getPoster());
		statement.setString     (10, mediaContent.getProduction());
		statement.setString     (11, mediaContent.getDirector());
		statement.setString     (12, mediaContent.getActors());
		statement.setFloat      (13, mediaContent.getRating());
	}
	
	protected static MediaContent createFromResult( final ResultSet result ) throws SQLException
	{
		final MediaContent mediaContent = new MediaContent();
		
		mediaContent.setId(result.getLong("media_content_id"));
		mediaContent.setTitle(result.getString("title"));
		mediaContent.setType(result.getString("type"));
		mediaContent.setYear(result.getShort("year"));
		mediaContent.setReleased(DateUtil.toJava(result.getTimestamp("release_date")));
		mediaContent.setRuntime(result.getString("runtime"));
		mediaContent.setGenre(result.getString("genre"));
		mediaContent.setPlot(result.getString("plot"));
		mediaContent.setPoster(result.getString("poster"));
		mediaContent.setProduction(result.getString("production"));
		mediaContent.setDirector(result.getString("director"));
		mediaContent.setActors(result.getString("actors"));
		mediaContent.setRating(result.getFloat("rating"));
		mediaContent.setViews(result.getLong("views"));
		mediaContent.setLikes(result.getLong("likes"));
		
		return mediaContent;
	}

}
