/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.Exception;
import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class ProfilesManager extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		manageGetPost(req, resp, true);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		manageGetPost(req, resp, false);
	}
	
	private void manageGetPost(HttpServletRequest req, HttpServletResponse resp, final boolean get) throws ServletException, IOException
	{
		final Family account = SessionManager.checkAccountAuthentication(req, resp);
		User user = SessionManager.checkUserAuthentication(req, resp, false);
		
		if ( account == null )
			return;
		if ( user != null )
		{
			final RequestDispatcher rd = req.getRequestDispatcher(".");
			rd.forward(req, resp);
			return;
		}
		
		final String action = req.getParameter("action");
		if ( action != null && action.equals("login") )
		{
			final Long userid = Long.parseLong( req.getParameter("userid") );
			final User login_user = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(userid);
			
			if ( get )
			{
				final String password = login_user.getPassword();
				if ( password == null || password.length() == 0 )
					onLoginEnabled(req, resp, login_user);
				else
				{
					req.setAttribute( "login_user", login_user );
					
					final RequestDispatcher rd = req.getRequestDispatcher("whoiswatching.jsp");
					rd.forward(req, resp);
				}
			}
			else
			{
				final String password = req.getParameter("pin");
				user = DBManager.getInstance().login(login_user.getEmail(), password);
				
				if ( user != null )
					onLoginEnabled(req, resp, user);
				else
				{
					req.setAttribute( "login_user", login_user );
					req.getSession().setAttribute("error", new Exception("invalid_login"));
					req.getRequestDispatcher("whoiswatching.jsp").forward(req, resp);
				}
			}
		}
	}
	
	private void onLoginEnabled( final HttpServletRequest req, final HttpServletResponse resp, final User user ) throws IOException
	{
		req.getSession().removeAttribute("login_user");
		req.getSession().removeAttribute("invalid_login");
		req.getSession().setAttribute("user", user);
		resp.sendRedirect(".");
	}
}
