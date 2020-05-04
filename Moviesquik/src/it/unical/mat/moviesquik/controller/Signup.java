/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.BillingPlan;
import it.unical.mat.moviesquik.model.BillingReport;
import it.unical.mat.moviesquik.model.CreditCard;
import it.unical.mat.moviesquik.model.Exception;
import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.DateUtil;

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
		
		final Family account = (Family) req.getSession().getAttribute("new_account");
		final BillingPlan choosenPlan = BillingPlan.parseBillingPlan( getChoosenPlan(req) );
		
		if ( account == null)
			req.getSession().invalidate();
		else if ( account != null && choosenPlan != null )
		{
			final BillingReport billingReport = BillingReport.createNewBillingReport(account);
			billingReport.initNextBillingUpdate(choosenPlan);
			
			account.setBillingReport(billingReport);
			
			req.getSession().setAttribute("plan", choosenPlan.toString());
		}
		
		//ServletUtils.printSessionAttributes(req);
		req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final Family account = (Family) req.getSession().getAttribute("new_account");
		
		if ( account == null )
		{			
			final Family new_account = new Family();
			new_account.setEmail( req.getParameter("email") );
			new_account.setPassword( req.getParameter("password") );
			
			new_account.setName(Family.DEFAULT_NAME);
			new_account.setMembers( new ArrayList<User>() );
			
			if ( DBManager.getInstance().canRegister(new_account) )
			{
				req.getSession().removeAttribute("existing_account");
				req.getSession().setAttribute("new_account", new_account);
			}
			else
				req.getSession().setAttribute("existing_account", new_account);
		}
		else
		{
			final String choosenPlan = getChoosenPlan(req);
			if ( choosenPlan != null )
			{
				final CreditCard card = new CreditCard
						( req.getParameter("cc-name"),
						  req.getParameter("cc-number"),
						  DateUtil.parseMonthFormat(req.getParameter("cc-expiration-month")),
						  req.getParameter("cc-cvv") );
				
				final DBManager db = DBManager.getInstance();
				
				if ( !db.existsMatch(card) )
					req.getSession().setAttribute("invalid_credit_card", card);
				else
				{
					account.setCreditCard(card);
					account.getBillingReport().initCurrentTrialBilling();
					
					if ( db.register(account) ) 
					{
						ServletUtils.removeAllSessionAttributes(req);
						req.getSession().setAttribute("registration_done", account);
						req.getSession().setAttribute("account", account);
						
						req.getRequestDispatcher("info.jsp").forward(req, resp);///
						return;
					}
					else
					{
						req.getSession().setAttribute("error", new Exception("registration_error"));
						req.getRequestDispatcher("info.jsp").forward(req, resp);
						req.getSession().invalidate();
						return;
					}
				}
			}
			else
				{ manageSessionError(req, resp); return; }
		}
		
		req.getRequestDispatcher("signup.jsp").forward(req, resp);
	}
	
	private String getChoosenPlan( final HttpServletRequest req )
	{
		final Object choosenPlanObj = req.getSession().getAttribute("plan");
		if ( choosenPlanObj != null )
			return (String) choosenPlanObj;
		
		/*
		final String[] plans = {"basic", "standard", "premium"};
		
		for( final String plan : plans )
			if ( req.getParameter(plan) != null )
				return plan;
		*/
		
		return req.getParameter("plan");
	}
	
	private void manageSessionError( HttpServletRequest req, HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		req.getSession().setAttribute("error", new Exception("session_error"));
		
		final RequestDispatcher rd = req.getRequestDispatcher("info.jsp");
		rd.forward(req, resp);
		
		req.getSession().invalidate();
	}
}
