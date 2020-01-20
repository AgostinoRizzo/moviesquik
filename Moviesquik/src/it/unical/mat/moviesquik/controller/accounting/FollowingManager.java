/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.Following;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class FollowingManager extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String action = req.getParameter("action");
		if ( action == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		try
		{
			final Long otherUserId = Long.parseLong( req.getParameter("user_id") );
			
			final User currentUser = (User) req.getSession().getAttribute("user");
			if ( currentUser == null )
			{
				ServletUtils.manageSessionError(req, resp);
				return;
			}
			
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			
			final User otherUser = daoFactory.getUserDao().findByPrimaryKey(otherUserId);
			if ( otherUser == null )
			{
				ServletUtils.manageParameterError(req, resp);
				return;
			}
			
			if ( action.equals("follow") )
			{
				manageFollow(req, resp, currentUser, otherUser);
				final RequestDispatcher rd = req.getRequestDispatcher("user?id=" + otherUser.getId());
				rd.forward(req, resp);
			}
		}
		catch (Exception e)
		{ ServletUtils.manageParameterError(req, resp); }
	}
	
	protected static void manageFollow(HttpServletRequest req, HttpServletResponse resp, final User currentUser, final User otherUser )
			throws ServletException, IOException
	{
			final Following following = new Following();			
			following.setFollower(currentUser);
			following.setFollowed(otherUser);
			
			DBManager.getInstance().getDaoFactory().getFollowingDao().save(following);
	}
}
