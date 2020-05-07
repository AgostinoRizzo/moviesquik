/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class SessionManager
{
	public static User checkUserAuthentication( final HttpServletRequest req, final HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		return checkUserAuthentication(req, resp, true);
	}
	public static User checkUserAuthentication( final HttpServletRequest req, final HttpServletResponse resp, final boolean finalize ) 
			throws ServletException, IOException
	{
		final Family account = (Family) req.getSession().getAttribute("account");
		if ( account == null )
		{
			if ( finalize ) ServletUtils.manageSessionError(req, resp);
			return null;
		}
		
		User user = (User) req.getSession().getAttribute("user");
		if ( user == null && finalize )
		{
			final RequestDispatcher rd = req.getRequestDispatcher("whoiswatching.jsp");
			rd.forward(req, resp);
		}
		
		if ( user != null )
		{
			user = DBManager.getInstance().getDaoFactory().getUserDao().findByPrimaryKey(user.getId());
			req.getSession().setAttribute("user", user);
		}
		
		return user;
	}
	public static Family checkAccountAuthentication( final HttpServletRequest req, final HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		return checkAccountAuthentication(req, resp, true, true);
	}
	public static Family checkAccountAuthentication( final HttpServletRequest req, final HttpServletResponse resp, final boolean finalize, final boolean isActiveFinalize ) 
			throws ServletException, IOException
	{
		final Family account = (Family) req.getSession().getAttribute("account");
		if ( account == null && finalize )
			ServletUtils.manageSessionError(req, resp);
		
		if ( account != null && !account.getBillingReport().isActive() && isActiveFinalize )
		{
			req.getSession().removeAttribute("user");
			final RequestDispatcher rd = req.getRequestDispatcher("whoiswatching.jsp");
			rd.forward(req, resp);
		}
		
		return account;
	}
}
