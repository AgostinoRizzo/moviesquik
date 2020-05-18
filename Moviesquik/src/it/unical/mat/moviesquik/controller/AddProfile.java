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

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.Watchlist;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class AddProfile extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final Family account = SessionManager.checkAccountAuthentication(req, resp);
		if ( account == null )
			return;
		
		req.setAttribute( "account", account );
		final RequestDispatcher rd = req.getRequestDispatcher("registration/addprofile.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final Family account = SessionManager.checkAccountAuthentication(req, resp);
		if ( account == null )
			return;
		
		final User new_user = new User();
		
		new_user.setFirstName( req.getParameter("first_name") );
		new_user.setLastName( req.getParameter("last_name") );
		
		String email = req.getParameter("email");
		if ( email == null || email.length() == 0 )
			email = account.getEmail();
		new_user.setEmail( email );
		
		new_user.setBirthday( DateUtil.parse(req.getParameter("birthday")) );
		new_user.setGender( req.getParameter("gender") );
		
		final String password = req.getParameter("pin");
		new_user.setPassword( password == null || password.length() == 0 ? null : password );
		
		final String iskid = req.getParameter("kid");
		new_user.setIsKid( iskid != null && iskid.equals("true") );
		
		account.getMembers().add(new_user);
		new_user.setFamily(account);
		
		final DaoFactory daoFacotry = DBManager.getInstance().getDaoFactory();
		daoFacotry.getUserDao().save(new_user);
		
		daoFacotry.getWatchlistDao().insert(Watchlist.createNewWatchLaterWatchlist(new_user));
		daoFacotry.getWatchlistDao().insert(Watchlist.createNewFavoritesWatchlist(new_user));
		
//		final RequestDispatcher rd = req.getRequestDispatcher("whoiswatching.jsp");
//		rd.forward(req, resp);
		resp.sendRedirect("");
	}
}
