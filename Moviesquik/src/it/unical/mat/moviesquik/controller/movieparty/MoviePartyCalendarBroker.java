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
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public class MoviePartyCalendarBroker extends HttpServlet
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
		
		final List<MovieParty> parties = DBManager.getInstance().getDaoFactory().getMoviePartyDao()
				.findCommitmentsByUser(user, MoviePartySearchFilter.UPCOMING, DataListPage.DEFAULT_MOVIE_PARTIES_CALENDAR_PAGE);
		
		req.setAttribute("parties", parties);
		sendView(req, resp);
	}
	
	public static void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String jspPage = "../movieparty/movie-party-calendar.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}
}
