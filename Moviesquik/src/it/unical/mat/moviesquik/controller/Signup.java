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

import it.unical.mat.moviesquik.model.CreditCard;
import it.unical.mat.moviesquik.model.Exception;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class Signup extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final String cancel = req.getParameter("cancel");
		if ( cancel != null && cancel.equals("true") )
		{
			req.getSession().invalidate();
			req.getRequestDispatcher("").forward(req, resp);
			return;
		}
		
		final User user = (User) req.getSession().getAttribute("user");
		final String choosenPlan = getChoosenPlan(req);
		
		if ( user == null)
			req.getSession().invalidate();
		else if ( user != null && choosenPlan != null )
		{
			user.setPlan(choosenPlan);
			req.getSession().setAttribute("plan", choosenPlan);
		}
		
		ServletUtils.printSessionAttributes(req);
		req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		
		if ( user == null )
		{
			final User new_user = new User
					( req.getParameter("first_name"),
					  req.getParameter("last_name"),
					  req.getParameter("email"),
					  req.getParameter("birthday"),
					  req.getParameter("gender"),
					  req.getParameter("password") );
			
			if ( DBManager.getInstance().exists(new_user) )
				req.getSession().setAttribute("existing_user", new_user);
			else
			{
				req.getSession().removeAttribute("existing_user");
				req.getSession().setAttribute("user", new_user);
			}
		}
		else
		{
			final String choosenPlan = getChoosenPlan(req);
			if ( choosenPlan != null )
			{
				final CreditCard card = new CreditCard
						( req.getParameter("cc-name"),
						  req.getParameter("cc-number"),
						  req.getParameter("cc-expiration-month"),
						  req.getParameter("cc-cvv") );
				
				// TODO: check credit card data and add account.
				final DBManager db = DBManager.getInstance();
				if ( !db.exists(card) || db.used(card) )
					req.getSession().setAttribute("invalid_credit_card", card);
				else
				{
					user.setCreditCard(card);
					
					if ( db.registerUser(user) ) req.getSession().setAttribute("registration_done", user);
					else req.getSession().setAttribute("error", new Exception("registration_error"));
					
					req.getRequestDispatcher("info.jsp").forward(req, resp);
					req.getSession().invalidate();
					return;
				}
			}
			else
				{ manageSessionError(req, resp); return; }
		}
		
		req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	
	private String getChoosenPlan( final HttpServletRequest req )
	{
		final String choosenPlan = (String) req.getSession().getAttribute("plan");
		if ( choosenPlan != null )
			return choosenPlan;
		
		final String[] plans = {"basic", "standard", "premium"};
		
		for( final String plan : plans )
			if ( req.getParameter(plan) != null )
				return plan;
		return null;
	}
	
	private void manageSessionError( HttpServletRequest req, HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		req.getSession().setAttribute("error", new Exception("session_error"));
		
		final RequestDispatcher rd = req.getRequestDispatcher("info.jsp");
		rd.forward(req, resp);
	}
}
