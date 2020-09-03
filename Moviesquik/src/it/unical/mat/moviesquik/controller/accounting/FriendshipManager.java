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
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.Friendship;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class FriendshipManager extends HttpServlet
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
			
			final User currentUser = SessionManager.checkUserAuthentication(req, resp, false);
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
			
			if ( action.equals("send") )
				manageSendRequest(req, resp, currentUser, otherUser);
			else if ( action.equals("accept") )
				manageAccept(req, resp, currentUser, otherUser);
		}
		catch (Exception e)
		{ ServletUtils.manageParameterError(req, resp); }
	}
	
	private void manageSendRequest(HttpServletRequest req, HttpServletResponse resp, final User currentUser, final User otherUser )
			throws ServletException, IOException
	{
			final Friendship friendship = new Friendship();			
			friendship.setStartDate(DateUtil.getCurrent());
			friendship.setFirstUser(currentUser);
			friendship.setSecondUser(otherUser);
			friendship.setFirstForApplicant(true);
			
			DBManager.getInstance().getDaoFactory().getFriendshipDao().save(friendship);
			
			final RequestDispatcher rd = req.getRequestDispatcher("user?id=" + otherUser.getId());
			rd.forward(req, resp);
	}
	
	private void manageAccept(HttpServletRequest req, HttpServletResponse resp, final User currentUser, final User otherUser )
			throws ServletException, IOException
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Friendship friendship = daoFactory.getFriendshipDao().findByMembers(currentUser, otherUser);
		
		if ( friendship == null ||  friendship != null && friendship.getApplicantUser().getId().equals(currentUser.getId()) )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		friendship.setFirstForApplicant(null);
		friendship.setStartDate(DateUtil.getCurrent());
		
		daoFactory.getFriendshipDao().update(friendship);
		
		FollowingManager.manageFollow(req, resp, currentUser, otherUser);
		
		final RequestDispatcher rd = req.getRequestDispatcher("user?id=" + otherUser.getId());
		rd.forward(req, resp);
	}
}
