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
	{System.out.println("ON PLAN");
		final String cancel = req.getParameter("cancel");
		req.getSession().invalidate();
		
		if ( cancel != null && cancel.equals("true") )
		{
			resp.sendRedirect("../");
			return;
		}
		
		req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	/*
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		final String choosenPlan = getChoosenPlan(req);
		
		if ( user != null && choosenPlan != null )
		{
			user.setPlan(choosenPlan);
			req.getSession().setAttribute("plan", choosenPlan);
			
			System.out.println("Choosen plan: " + choosenPlan);
			
			resp.sendRedirect("../");
		}
		else
		{
			req.getSession().invalidate();
			resp.sendRedirect("user");
		}
		
		//req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	
	private String getChoosenPlan( final HttpServletRequest req )
	{
		final String[] plans = {"basic", "standard", "premium"};
		
		for( final String plan : plans )
			if ( req.getParameter(plan) != null )
				return plan;
		return null;
	}
	*/
}
