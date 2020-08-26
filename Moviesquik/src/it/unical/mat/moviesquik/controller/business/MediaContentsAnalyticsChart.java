/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryLog;
import it.unical.mat.moviesquik.model.analytics.MediaAnalyticsHistoryWindow;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentsAnalyticsChart extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String[] CHARTS_NAMES = { "trending", "ratings", "likes", "views" };
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try 
		{
			Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
			if ( admin == null )
				throw new RuntimeException();
			
			final Long mediaId = Long.parseLong(req.getParameter("media"));
			final MediaContent media = DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaId);
			
			if ( media == null )
				throw new RuntimeException();
			
			final String chartsWindow = req.getParameter("win");
			
			ServletUtils.sendJson(createChartsDataJsonResponse( media, chartsWindow ), resp);
		}
		catch (Exception e) 
		{ ServletUtils.sendJson( new JsonObject(), resp ); }
	}
	
	private JsonObject createChartsDataJsonResponse( final MediaContent media, final String chartsWindow )
	{
		final MediaAnalyticsHistoryWindow historyWin = MediaAnalyticsHistoryWindow.parse(chartsWindow);
		final List<MediaAnalyticsHistoryLog> analyticsHistory = DBManager.getInstance().getDaoFactory().getMediaAnalyticsHistoryLogDao()
					.findByMediaContent(media, historyWin);
		final Map<Date, MediaAnalyticsHistoryLog> analyticsHistoryMap = createAnalyticsHistoryLogMap(analyticsHistory);
		final List<Date> chartDays = DateUtil.getLastDaysList( MediaAnalyticsHistoryWindow.getDaysCount(historyWin) );
		final JsonObject chartsData = new JsonObject();
		
		for ( final String chartName : CHARTS_NAMES )
		{
			final JsonArray values = new JsonArray();
			
			JsonObject value;
			for ( final Date day : chartDays )
			{
				final MediaAnalyticsHistoryLog log = getAnalyticsHistoryLogFromMap(analyticsHistoryMap, media, day);
				final Number[] analyticsValues = log.getAnalyticsValues();
				final Number[] chartValues = getChartValues(chartName, analyticsValues);
				
				value = new JsonObject();
				value.addProperty("day", DateUtil.toDayString( log.getLogDate() ));
				value.addProperty("value1", chartValues[0]);
				value.addProperty("value2", chartValues[1]);
				values.add(value);
			}
			
			chartsData.add(chartName, values);
		}
		
		return chartsData;
	}
	
	private static Map<Date, MediaAnalyticsHistoryLog> createAnalyticsHistoryLogMap( final List<MediaAnalyticsHistoryLog> historyList )
	{
		final Map<Date, MediaAnalyticsHistoryLog> historyLogMap = new HashMap<Date, MediaAnalyticsHistoryLog>();
		
		for ( final MediaAnalyticsHistoryLog log : historyList )
			historyLogMap.put(log.getLogDate(), log);
		
		return historyLogMap;
	}
	
	private static MediaAnalyticsHistoryLog getAnalyticsHistoryLogFromMap( final Map<Date, MediaAnalyticsHistoryLog> historyLogMap, final MediaContent media, final Date day )
	{
		if ( historyLogMap.containsKey(day) )
			return historyLogMap.get(day);
		return new MediaAnalyticsHistoryLog(media, day);
	}
	
	private static Number[] getChartValues( final String chartName, final Number[] analyticsValues )
	{
		final Number[] values = {null, null};
		
		if ( chartName.equals("trending") ) 
		{ 
			values[0] = analyticsValues[MediaAnalyticsHistoryLog.TRENDING_VALUE];
			values[1] = analyticsValues[MediaAnalyticsHistoryLog.POPULARITY_VALUE];
		}
		else if ( chartName.equals("ratings") ) 
			values[0] = analyticsValues[MediaAnalyticsHistoryLog.RATE_VALUE];
		else if ( chartName.equals("likes") ) 
		{ 
			values[0] = analyticsValues[MediaAnalyticsHistoryLog.LIKES_VALUE];
			values[1] = analyticsValues[MediaAnalyticsHistoryLog.NOLIKES_VALUE];
		}
		else if ( chartName.equals("views") ) 
			values[0] = analyticsValues[MediaAnalyticsHistoryLog.VIEWS_VALUE];
		
		return values;
	}
}
