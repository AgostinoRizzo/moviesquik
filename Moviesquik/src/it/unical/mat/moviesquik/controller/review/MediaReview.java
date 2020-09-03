/**
 * 
 */
package it.unical.mat.moviesquik.controller.review;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.analytics.MediaContentReview;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class MediaReview extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		if ( user == null )
		{
			sendAnswer(false, resp);
			return;
		}
		
		Long mediaContentId = null;
		try { mediaContentId = Long.parseLong(req.getParameter("media")); }
		catch (NumberFormatException e) 
		{
			sendAnswer(false, resp);
			return;
		}
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		MediaContentReview review = daoFactory.getMediaContentReviewDao().find( user.getId(), mediaContentId );
		
		if ( review == null )
		{
			final MediaContent mediaContent = daoFactory.getMediaContentDao().findById(mediaContentId);
			if ( mediaContent == null )
			{
				sendAnswer(false, resp);
				return;
			}
			review = new MediaContentReview();
			review.setSubject(user);
			review.setMediaContent(mediaContent);
		}
		
		try 
		{
			final Integer rate = Integer.parseInt(req.getParameter("rate"));
			review.setRate(rate);
		}
		catch (NumberFormatException e)
		{
			final String like = req.getParameter("like");
			if ( like == null )
			{
				sendAnswer(false, resp);
				return;
			}
			
			if ( like.equals("true") )
				review.setLike(true);
			else if ( like.equals("false") )
				review.setLike(false);
			else if ( like.equals("null") )
				review.setLike(null);
			else
			{
				sendAnswer(false, resp);
				return;
			}
		}
		
		sendAnswer( daoFactory.getMediaContentReviewDao().save(review), resp );
	}
	
	private JsonObject createJsonAnswer( final boolean status )
	{
		final JsonObject answer = new JsonObject();
		answer.addProperty("status", status);
		return answer;
	}
	
	private void sendAnswer( final boolean status, final HttpServletResponse resp ) throws IOException
	{
		ServletUtils.sendJson(createJsonAnswer(status), resp);
	}
}
