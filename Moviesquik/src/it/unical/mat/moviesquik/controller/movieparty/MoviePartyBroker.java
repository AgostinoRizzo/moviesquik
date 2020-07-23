/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public class MoviePartyBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final String pageIndexString = req.getParameter("pageid");
		int page_index;
		
		try
		{ page_index = Integer.parseInt(pageIndexString); }
		catch (Exception e)
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final int pageIndex = (pageIndexString == null) ? 0 : page_index;
		final DataListPage page = new DataListPage(pageIndex, DataListPage.MOVIE_PARTIES_PAGE_LIMIT);
		
		final List<MovieParty> parties = DBManager.getInstance().getDaoFactory().getMoviePartyDao().findAll(user, page);
		
		req.setAttribute("parties", parties);
		sendView(req, resp);		
	}
	
	public static void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String jspPage = "movieparty/party-list.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}
}
