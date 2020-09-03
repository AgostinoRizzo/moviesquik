/**
 * 
 */
package it.unical.mat.moviesquik.controller.watchlist;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class Watchlist extends HttpServlet
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
		
		final String action = req.getParameter("action");
		
		if ( action != null && action.equals("page") )
		{
			Long watchlistId;
			
			try
			{ watchlistId = Long.parseLong(req.getParameter("key")); }
			catch (NumberFormatException e) 
			{
				ServletUtils.manageParameterError(req, resp);
				return;
			}
			
			final it.unical.mat.moviesquik.model.watchlist.Watchlist watchlist = 
					DBManager.getInstance().getDaoFactory().getWatchlistDao().findById(watchlistId);
			
			if ( watchlist == null )
			{
				ServletUtils.manageParameterError(req, resp);
				return;
			}
			
			req.setAttribute("watchlist", watchlist);
			req.getRequestDispatcher("watchlist/watchlist-page.jsp").forward(req, resp);
		}
		else
		{
			final String onRemove = req.getParameter("remove");
			if ( onRemove != null )
				req.setAttribute("on_" + onRemove + "_removed", true);
				
			
			req.setAttribute("watchlists", user.getWatchlists());
			req.getRequestDispatcher("watchlist/watchlists.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final String action = req.getParameter("action");
		if ( action == null || !action.equals("create") )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final String watchlistName = req.getParameter("name");
		if ( watchlistName == null || watchlistName.isEmpty() )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final String watchlistDescription = req.getParameter("description");
		
		final it.unical.mat.moviesquik.model.watchlist.Watchlist newWatchlist = 
				it.unical.mat.moviesquik.model.watchlist.Watchlist.createsNewWatchlist(watchlistName, watchlistDescription, user);
		
		final boolean created = DBManager.getInstance().getDaoFactory().getWatchlistDao().insert(newWatchlist);
		req.setAttribute("on_create", created);
		
		doGet(req, resp);
	}
}
