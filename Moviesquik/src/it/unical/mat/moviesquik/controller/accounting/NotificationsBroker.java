/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import java.io.IOException;

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
public class NotificationsBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final String action = req.getParameter("action");
		
		if ( action != null && action.equals("readall") )
			DBManager.getInstance().getDaoFactory().getNotificationDao().readAll(user);
		else
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
	}
}
