/**
 * 
 */
package it.unical.mat.moviesquik.controller.posting;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.Notification;
import it.unical.mat.moviesquik.model.NotificationFactory;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.PostFeedback;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class SendPostFeedback extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		final Long post_id = Long.parseLong( req.getParameter("postid") );
		final boolean is_like = req.getParameter("islike").equals("true");
		final Post referred_post = daoFactory.getPostDao().findById(post_id);
		
		final PostFeedback feedback = new PostFeedback();
		feedback.setLike(is_like);
		feedback.setOwner(user);
		feedback.setReferredPost(referred_post);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		final PrintWriter out = resp.getWriter();
		final JsonObject json_response = new JsonObject();
		
		json_response.addProperty("added", daoFactory.getPostFeedbackDao().save(feedback));
		
		if ( json_response.get("added").getAsBoolean() )
		{
			final Post post = daoFactory.getPostDao().findById(post_id);
			json_response.addProperty("nlikes", post.getNumLikes());
			json_response.addProperty("nloves", post.getNumLoves());
			json_response.addProperty("ncomments", post.getNumAllComments());
			
			final User receiver = referred_post.getOwner();
			if ( receiver.getId() != user.getId() )
			{
				final Notification notification = feedback.isLike() 
												  ? NotificationFactory.getInstance().createPostLikeFeedbackNotification(user)
												  : NotificationFactory.getInstance().createPostLoveFeedbackNotification(user);
				daoFactory.getNotificationDao().save(notification, receiver);
			}
			
		}
		
		out.print(json_response.toString());
		out.flush();
	}
}
