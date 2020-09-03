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
import it.unical.mat.moviesquik.model.accounting.Family;

/**
 * @author Agostino
 *
 */
public class AccountManager extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final Family account = SessionManager.checkAccountAuthentication(req, resp, true, false);
		if ( account == null )
			return;
		
		account.getBillingReport().reload();
		final RequestDispatcher rd = req.getRequestDispatcher("accounting/account.jsp");
		rd.forward(req, resp);
	}

}
