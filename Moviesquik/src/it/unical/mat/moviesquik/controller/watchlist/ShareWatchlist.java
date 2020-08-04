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
import it.unical.mat.moviesquik.model.Notification;
import it.unical.mat.moviesquik.model.NotificationFactory;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.Watchlist;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class ShareWatchlist extends HttpServlet
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
		if ( action == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final boolean forwardMyWatchlists = action.equals("my");
		
		Long watchlistId;
		
		try
		{ watchlistId = Long.parseLong(req.getParameter("watchlist_id")); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		String text = req.getParameter("text");
		if ( text != null )
			text.trim();
		
		final Watchlist watchlist = DBManager.getInstance().getDaoFactory().getWatchlistDao().findById(watchlistId);
		if ( watchlist == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		boolean shared = !watchlist.getItems().isEmpty();
		
		if ( shared )
		{
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			final Post newPost = new Post();
			newPost.setDateTime( DateUtil.getCurrent() );
			newPost.setText(text);
			newPost.setOwner(user);
			newPost.setWatchlist(watchlist);
			
			shared = daoFactory.getPostDao().save(newPost);
			
			final User receiver = watchlist.getOwner();
			if ( shared && !receiver.getId().equals(user.getId()) )
			{
				final Notification notification = NotificationFactory.getInstance().createWatchlistShareNotification(user, watchlist);
				daoFactory.getNotificationDao().save(notification, receiver);
			}
		}
		
		req.setAttribute("on_shared", shared ? "success" : "");
		
		if ( forwardMyWatchlists )
		{
			req.setAttribute("watchlists", user.getWatchlists());
			req.getRequestDispatcher("watchlist/watchlists.jsp").forward(req, resp);
		}
		else
			resp.sendRedirect(".");
	}
}
