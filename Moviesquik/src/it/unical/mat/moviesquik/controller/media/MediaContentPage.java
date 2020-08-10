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
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.analytics.MediaContentReview;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

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
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final MediaContent mc = daoFactory.getMediaContentDao().findById(key);
		final MediaContentReview userReview = daoFactory.getMediaContentReviewDao().find(user.getId(), key);
		
		req.setAttribute("media_content", mc);
		req.setAttribute("user_review", userReview);
		
		final RequestDispatcher rd = req.getRequestDispatcher("media/media_content_page.jsp");
		rd.forward(req, resp);
	}
}
