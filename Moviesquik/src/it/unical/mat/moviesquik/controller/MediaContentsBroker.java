/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.analytics.AnalyticsFacade;
import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentsBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final int MAX_FIND_COUNT = 10;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String policy = req.getParameter("policy");
		
		if ( policy == null )
			return;
		
		List<MediaContent> mediaContents = null;
		
		if ( policy.equals("trending") )
			mediaContents = 
				DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
					.searchTrendingNow(MediaContentType.ALL, SortingPolicy.NONE, MAX_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		
		else if ( policy.equals("most_popular") )
			mediaContents = 
			DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
				.searchMostPopular(MediaContentType.ALL, SortingPolicy.NONE, MAX_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		
		else if ( policy.equals("most_rated") )
			mediaContents = 
			DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
				.searchTopRated(MediaContentType.ALL, SortingPolicy.NONE, MAX_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		
		else if ( policy.equals("most_favorites") )
			mediaContents = 
			DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
				.searchMostFavorites(MediaContentType.ALL, SortingPolicy.NONE, MAX_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		
		else if ( policy.equals("suggested") || policy.equals("maylike") || policy.equals("recently") )
		{
			final User user = SessionManager.checkUserAuthentication(req, resp, false);
			if ( user != null )
			{
				if ( policy.equals("suggested") ) 	 mediaContents = DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
													 .searchSuggested(MediaContentType.ALL, user, SortingPolicy.NONE, MAX_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
				else if ( policy.equals("maylike") ) mediaContents = AnalyticsFacade.getMayLikeMediaContents(user.getId(), MAX_FIND_COUNT);
				else 								 mediaContents = DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
													 .searchRecentlyWatched(MediaContentType.ALL, user, SortingPolicy.NONE, MAX_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
			}
			else
				mediaContents = new ArrayList<MediaContent>();
		}
		
		else
			mediaContents = new ArrayList<MediaContent>();
		
		filterByCount(mediaContents, req);
		
		// trigger media content statistics load from DB
		for ( final MediaContent mc : mediaContents )
			mc.getStatistics();
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		final PrintWriter out = resp.getWriter();
		out.print(JSONUtil.fromListToString(mediaContents));
		out.flush();
	}
	
	private void filterByCount( final List<MediaContent> contents, final HttpServletRequest req )
	{
		if ( contents.isEmpty() )
			return;
		
		try
		{
			final int count = Integer.parseInt( req.getParameter("count") );
			contents.stream().limit(count);
		}
		catch (Exception e) 
		{}
	}
}
