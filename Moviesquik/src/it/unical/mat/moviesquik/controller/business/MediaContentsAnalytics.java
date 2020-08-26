/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentsAnalytics extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final int MAX_MEDIA_FIND_COUNT = 8;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
		if ( admin == null )
		{
			req.getRequestDispatcher("/business/login.jsp").forward(req, resp);
			return;
		}
		
		final String title = req.getParameter("title");
		if ( title != null && !title.isEmpty() )
		{
			manageTitleSearch(title, resp);
			return;
		}
		
		final String rating = req.getParameter("rating");
		if ( rating != null && !rating.isEmpty() )
		{
			manageRatingSearch(rating, resp);
			return;
		}
		
		final List<MediaContent> trendingMediaContents = 
				DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
					.searchTrendingNow(MediaContentType.ALL, SortingPolicy.NONE, MAX_MEDIA_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		req.setAttribute("trendingnow", trendingMediaContents);
		
		req.getRequestDispatcher("/business/media-analytics.jsp").forward(req, resp);
	}
	
	private void manageTitleSearch( final String title, final HttpServletResponse resp ) throws IOException
	{
		final List<MediaContent> mediaContents = 
				DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
				.searchByTitle(title, true, SortingPolicy.TITLE_ASC, MAX_MEDIA_FIND_COUNT);
		manageSearchResult(mediaContents, resp);
	}
	
	private void manageRatingSearch( final String rating, final HttpServletResponse resp ) throws IOException
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		List<MediaContent> mediaContents;
		
		if ( rating.equals("trendingnow") ) mediaContents = daoFactory.getMediaContentSearchDao()
				.searchTrendingNow(MediaContentType.ALL, SortingPolicy.NONE, MAX_MEDIA_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		else if ( rating.equals("popular") ) mediaContents = daoFactory.getMediaContentSearchDao()
				.searchMostPopular(MediaContentType.ALL, SortingPolicy.NONE, MAX_MEDIA_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		else if ( rating.equals("toprated") ) mediaContents = daoFactory.getMediaContentSearchDao()
				.searchTopRated(MediaContentType.ALL, SortingPolicy.NONE, MAX_MEDIA_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		else if ( rating.equals("favorites") ) mediaContents = daoFactory.getMediaContentSearchDao()
				.searchMostFavorites(MediaContentType.ALL, SortingPolicy.NONE, MAX_MEDIA_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		else
			mediaContents = new ArrayList<MediaContent>();
		
		manageSearchResult(mediaContents, resp);
	}
	
	private void manageSearchResult( final List<MediaContent> mediaContents, final HttpServletResponse resp ) throws IOException
	{
		for ( final MediaContent mc : mediaContents )
			mc.getStatistics();
		ServletUtils.sendJson(createFinalResponse(JSONUtil.fromListToJsonArray(mediaContents, MediaContent.class)), resp);
	}
	
	private JsonObject createFinalResponse( final JsonArray mediaContents )
	{
		final JsonObject response = new JsonObject();
		response.add("media", mediaContents);
		return response;
	}
}
