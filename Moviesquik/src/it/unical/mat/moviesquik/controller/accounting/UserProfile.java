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
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class UserProfile extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		Long idUserToDisplay = null;
		
		try { idUserToDisplay = Long.parseLong(req.getParameter("id")); }
		catch (Exception e) {}
		
		User userToDisplay = null;
		
		if ( idUserToDisplay != null && 
				(userToDisplay = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(idUserToDisplay)) != null )
			addData(req, userToDisplay, false);
		else if (idUserToDisplay != null)
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		} 
		else if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		else
			addData(req, user, true);
		
		final RequestDispatcher rd = req.getRequestDispatcher("accounting/user-profile.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	private void addData( final HttpServletRequest req, final User userToDisplay, final boolean isCurrentUser )
	{
		req.setAttribute("user_to_display", userToDisplay);
		userToDisplay.getFriends();
	}
	
}
