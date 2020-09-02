/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public class SignupPlan extends HttpServlet
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
	
}
