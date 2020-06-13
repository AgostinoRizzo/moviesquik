/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class FamilyBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final Family family = daoFactory.getFamilyDao().findByMember(user);
		final List<User> members = family.getMembers();
		
		for ( final User m : members )
			m.setFamily(null);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		final PrintWriter out = resp.getWriter();
		out.print(JSONUtil.toJson(family));
		out.flush();
	}
}
