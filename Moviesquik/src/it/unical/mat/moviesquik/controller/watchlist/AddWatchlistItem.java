/**
 * 
 */
package it.unical.mat.moviesquik.controller.watchlist;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.watchlist.Watchlist;

/**
 * @author Agostino
 *
 */
public class AddWatchlistItem extends HttpServlet
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
		
		Long mediaContentId;
		
		try
		{ mediaContentId = Long.parseLong(req.getParameter("media_content_id")); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final Watchlist watchlist = getWatchlist(req, resp, user);
		if ( watchlist == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		ServletUtils.sendJson(createJsonResponse(watchlist.add(mediaContentId)), resp);
	}
	
	private static Watchlist getWatchlist( final HttpServletRequest req, final HttpServletResponse resp, final User owner )
	{
		final String watchlistName = req.getParameter("watchlist_name");
		if ( watchlistName == null )
			return null;
		
		final List<Watchlist> watchlists = owner.getWatchlists();
		for( final Watchlist wl : watchlists )
			if ( wl.getName().equals(watchlistName) )
				return wl;
		return null;
	}
	
	private static JsonObject createJsonResponse( final boolean added )
	{
		final JsonObject response = new JsonObject();
		response.addProperty("added", added);
		return response;
	}
}
