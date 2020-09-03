/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.analytics.AnalyticsFacade;
import it.unical.mat.moviesquik.controller.searching.MediaContentsSearch;
import it.unical.mat.moviesquik.controller.searching.MediaContentsSearchFilter;
import it.unical.mat.moviesquik.controller.searching.MediaContentsViewTemplate;
import it.unical.mat.moviesquik.model.Showcase;
import it.unical.mat.moviesquik.model.accounting.Family;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.media.MediaContentType;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class Home extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final MediaContent mediaContentOfTheDay =
				DBManager.getInstance().getDaoFactory().getMediaContentDao().getMediaContentOfTheDay();
		if ( mediaContentOfTheDay != null )
			req.setAttribute("media_content_of_the_day", mediaContentOfTheDay);
		req.setAttribute("genres", DBManager.getInstance().getMediaContentsGenres());
		
		final Family account = SessionManager.checkAccountAuthentication(req, resp, false, true);
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		
		if ( account == null )
		{
			final DaoFactory daofactory = DBManager.getInstance().getDaoFactory();
			final Showcase sc = new Showcase();
			final DataListPage dataListPage = new DataListPage(10);
			
			sc.setTopRated( daofactory.getMediaContentSearchDao()
					.searchTopRated(MediaContentType.ALL, SortingPolicy.NONE, dataListPage, MediaContentsSearchFilter.EMPTY));
			sc.setMostPopular( daofactory.getMediaContentSearchDao()
					.searchMostPopular(MediaContentType.ALL, SortingPolicy.NONE, dataListPage, MediaContentsSearchFilter.EMPTY));
			sc.setMostFavorites( daofactory.getMediaContentSearchDao()
					.searchMostFavorites(MediaContentType.ALL, SortingPolicy.NONE, dataListPage, MediaContentsSearchFilter.EMPTY));
			sc.setTrendingNow( AnalyticsFacade.getTrendingNowMediaContents(10) );
			
			req.setAttribute( "showcase", sc );
			
			final RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, resp);
		}
		else if ( user == null )
		{
			final RequestDispatcher rd = req.getRequestDispatcher("whoiswatching.jsp");
			rd.forward(req, resp);
		}
		else
		{
			MediaContentsSearch.manageGroupViewTemplate
				(req, resp, MediaContentType.ALL, MediaContentsViewTemplate.GROUP, user, MediaContentsSearchFilter.EMPTY, false);
			
			final RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
}
