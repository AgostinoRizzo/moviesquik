/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.business.Analyst;

/**
 * @author Agostino
 *
 */
public class BusinessHome extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
		if ( admin == null )
		{
			final RequestDispatcher rd = req.getRequestDispatcher("/business/login.jsp");
			rd.forward(req, resp);
			return;
		}
		
		final RequestDispatcher rd = req.getRequestDispatcher("/business/home.jsp");
		rd.forward(req, resp);
	}
}
