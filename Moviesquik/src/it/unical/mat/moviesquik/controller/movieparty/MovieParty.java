/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public class MovieParty extends HttpServlet
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
		
		/*
		final String action = req.getParameter("action");
		
		if ( action != null && action.equals("page") )
		{
			
		}
		*/
		
		req.setAttribute("watchlist", user.getWatchlists().get(0));
		req.getRequestDispatcher("movieparty/create-party.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
	}
}
