/**
 * 
 */
package it.unical.mat.moviesquik.controller.streaming;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class Watch extends HttpServlet
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
		
		Long mediaContentId;
		try { mediaContentId = Long.parseLong( req.getParameter("key") ); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		req.setAttribute("media_content", DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaContentId));
		req.getRequestDispatcher("streaming/watch.jsp").forward(req, resp);
	}
}
