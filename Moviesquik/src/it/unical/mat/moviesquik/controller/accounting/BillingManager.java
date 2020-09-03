/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.Billing;
import it.unical.mat.moviesquik.model.accounting.BillingPlan;
import it.unical.mat.moviesquik.model.accounting.Family;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class BillingManager extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGetPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGetPost(req, resp);
	}
	
	private void doGetPost( final HttpServletRequest req, final HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		final Family account = SessionManager.checkAccountAuthentication(req, resp, true, false);
		if ( account == null )
			return;
		
		final boolean forward = manageAction(req, resp, account);
		account.getBillingReport().reload();
		
		if ( forward )
		{
			final RequestDispatcher rd = req.getRequestDispatcher("accounting/billing.jsp");
			rd.forward(req, resp);
		}
	}
	
	private boolean manageAction( final HttpServletRequest req, final HttpServletResponse resp, final Family account ) 
			throws ServletException, IOException
	{
		final String action = req.getParameter("action");
		if ( action == null )
			return true;
		if ( action.equals("autoupdate") )      return manageAutoUpdate(req, account);
		else if ( action.equals("changeplan") ) return manageChangePlan(req, resp, account);
		
		return true;
	}
	
	private boolean manageAutoUpdate( final HttpServletRequest req, final Family account )
	{
		final String value = req.getParameter("value");
		if ( value == null )
			return true;
		
		final boolean autoupdate = value.equals("true");
		account.setAutoUpdate(autoupdate);
		
		DBManager.getInstance().getDaoFactory().getFamilyDao().update(account);
		return true;
	}
	
	private boolean manageChangePlan( final HttpServletRequest req, final HttpServletResponse resp, final Family account ) 
			throws ServletException, IOException
	{
		final BillingPlan plan = BillingPlan.parseBillingPlan( req.getParameter("plan") );
		if ( plan == null )
		{
			req.setAttribute("changeplan", true);
			req.getRequestDispatcher("accounting/changeplan.jsp").forward(req, resp);
			return false;
		}
		
		final Billing nextBillingUpdate = account.getBillingReport().getNextUpdate();
		nextBillingUpdate.setPlan(plan.toString());
		
		DBManager.getInstance().getDaoFactory().getBillingDao().update(nextBillingUpdate);
		return true;
	}
}
