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
public class EditWatchlist extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
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
		if ( action == null || !action.equals("remove") )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final String type = req.getParameter("type");
		if ( type == null  )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		if ( type.equals("item") )
			manageRemoveItem(req, resp);
		else if ( type.equals("watchlist") )
			manageRemoveWatchlist(req, resp);
		else
			ServletUtils.manageParameterError(req, resp);
	}
	
	private void manageRemoveItem( final HttpServletRequest req, final HttpServletResponse resp ) throws ServletException, IOException
	{
		Long mediaContentId;
		Long watchlistId;
		
		try
		{ 
			mediaContentId = Long.parseLong(req.getParameter("media_content_id"));
			watchlistId = Long.parseLong(req.getParameter("watchlist_id")); 
		}
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		if ( DBManager.getInstance().getDaoFactory().getWatchlistItemDao().remove(mediaContentId, watchlistId) )
			resp.sendRedirect("watchlist?remove=item");
		else
			resp.sendRedirect("watchlist");
	}
	
	private void manageRemoveWatchlist( final HttpServletRequest req, final HttpServletResponse resp ) throws ServletException, IOException
	{
		Long watchlistId;
		
		try
		{ watchlistId = Long.parseLong(req.getParameter("watchlist_id")); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		if ( DBManager.getInstance().getDaoFactory().getWatchlistDao().remove(watchlistId) )
			resp.sendRedirect("watchlist?remove=watchlist");
		else
			resp.sendRedirect("watchlist");
	}
}
