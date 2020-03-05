/**
 * 
 */
package it.unical.mat.moviesquik.controller.posting;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class PostsBroker
{
	public static void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String jspPage = "posting/news_content.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}
	
	public static JsonArray getPosts( final User user )
	{
		final List<Post> posts = DBManager.getInstance().getDaoFactory().getPostDao()
									.findByFollowedUsers(user, Post.POSTS_LIMIT);
		final JsonArray json = new JsonArray();
		for ( final Post p : posts )
		{
			final JsonObject post_json = new JsonObject();
			post_json.addProperty("dateTime", p.getDateTime().toString());
			post_json.addProperty("text", p.getText());
			post_json.addProperty("owner", JSONUtil.toJson(p.getOwner()));
			json.add(post_json);
		}
		return json;
	}
}
