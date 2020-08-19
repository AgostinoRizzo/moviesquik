/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.IllegalServletSessionException;
import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.Exception;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class BusinessLogin extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
			if ( admin == null )
				throw new IllegalServletSessionException();
			
			final String isLogout = req.getParameter("logout");
			if ( isLogout != null && isLogout.equals("true") )
				req.getSession().invalidate();
		}
		catch (java.lang.Exception e) {}
		finally 
		{
			resp.sendRedirect("../business");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
		if ( admin != null )
		{
			resp.sendRedirect("../business");
			return;
		}
		
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");
		
		if ( email == null || password == null )
		{
			ServletUtils.manageSessionError(req, resp, "../info.jsp");
			return;
		}
		
		admin = DBManager.getInstance().adminLogin(email, password);
		
		if ( admin != null )
		{
			ServletUtils.removeAllSessionAttributes(req);
			req.getSession().setAttribute("admin", admin);
			resp.sendRedirect("../business");
		}
		else
		{
			req.getSession().setAttribute("error", new Exception("invalid_login"));
			resp.sendRedirect("../business");
		}
	}
}
