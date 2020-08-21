/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class MediaContentsManagement extends HttpServlet
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
		
		final List<MediaContent> trendingMediaContents = 
				DBManager.getInstance().getDaoFactory().getMediaContentSearchDao()
					.searchTrendingNow(MediaContentType.ALL, SortingPolicy.NONE, MAX_MEDIA_FIND_COUNT, MediaContentsSearchFilter.EMPTY);
		req.setAttribute("trendingnow", trendingMediaContents);
		
		req.getRequestDispatcher("/business/media-management.jsp").forward(req, resp);
	}
}
