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
import it.unical.mat.moviesquik.model.User;
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
		if ( isLogout != null && isLogout.equals("true") )
		{
			req.getSession().invalidate();
			req.getRequestDispatcher("").forward(req, resp);
		}
		else if ( req.getSession().getAttribute("user") == null )
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		else
			req.getRequestDispatcher("").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		User user = (User) req.getSession().getAttribute("user");
		if ( user != null )
		{
			req.getRequestDispatcher("").forward(req, resp);
			return;
		}
		
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");
		
		user = DBManager.getInstance().login(email, password);
		
		if ( user != null )
		{
			req.getSession().removeAttribute("invalid_login");
			req.getSession().setAttribute("user", user);
			resp.sendRedirect("");
		}
		else
		{
			req.getSession().setAttribute("error", new Exception("invalid_login"));
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		
	}

}
