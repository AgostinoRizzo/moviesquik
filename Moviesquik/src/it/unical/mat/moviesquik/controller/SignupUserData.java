/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class SignupUserData extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{		
		final String cancel = req.getParameter("cancel");
		req.getSession().invalidate();
		
		if ( cancel != null && cancel.equals("true") )
		{
			resp.sendRedirect("../");
			return;
		}
		
		req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{		
		final User new_user = new User
			  ( req.getParameter("first_name"),
				req.getParameter("last_name"),
				req.getParameter("email"),
				DateUtil.parse(req.getParameter("birthday")),
				req.getParameter("gender"),
				req.getParameter("password") );
		
		if ( DBManager.getInstance().canRegister(new_user) )
		{
			req.getSession().setAttribute("existing_user", new_user);
			req.getRequestDispatcher("signup.jsp").forward(req, resp);
		}
		else
		{
			req.getSession().removeAttribute("existing_user");
			req.getSession().setAttribute("user", new_user);
			//req.getRequestDispatcher("signup?plans=true").forward(req, resp);
			System.out.println("GO TO PLAN");
			req.getRequestDispatcher("plan").forward(req, resp);
		}
	}
}
