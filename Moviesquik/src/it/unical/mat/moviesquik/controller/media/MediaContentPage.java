/**
 * 
 */
package it.unical.mat.moviesquik.controller.media;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class MediaContentPage extends HttpServlet
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
		
		final String keyString = req.getParameter("key");
		Long key;
		try
		{
			key = Long.parseLong(keyString);
		}
		catch (Exception e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final MediaContent mc = DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(key);
		req.setAttribute("media_content", mc);
		
		final RequestDispatcher rd = req.getRequestDispatcher("media/media_content_page.jsp");
		rd.forward(req, resp);
	}
}
