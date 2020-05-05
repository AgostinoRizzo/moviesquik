/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.Exception;
import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		final String isLogout = req.getParameter("logout");
		final String subject = req.getParameter("subject");
		if ( isLogout != null && isLogout.equals("true") && subject != null )
		{
			if ( subject.equals("user") )
			{
				final Object account = req.getSession().getAttribute("account");
				ServletUtils.removeAllSessionAttributes(req);
				req.getSession().setAttribute("account", account);
			}
			else
				req.getSession().invalidate();
			resp.sendRedirect(".");
		}
		else if ( req.getSession().getAttribute("account") == null )
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		else
			req.getRequestDispatcher("").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		Family account = (Family) req.getSession().getAttribute("account");
		if ( account != null )
		{
			req.getRequestDispatcher("").forward(req, resp);
			return;
		}
		
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");
		
		account = DBManager.getInstance().accountLogin(email, password);
		
		if ( account != null )
		{
			req.getSession().removeAttribute("invalid_login");
			req.getSession().setAttribute("account", account);
			resp.sendRedirect(".");
		}
		else
		{
			req.getSession().setAttribute("error", new Exception("invalid_login"));
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		
	}

}
