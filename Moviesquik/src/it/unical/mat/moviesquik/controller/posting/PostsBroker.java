/**
 * 
 */
package it.unical.mat.moviesquik.controller.posting;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.posting.Post;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class PostsBroker extends HttpServlet
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
		
		final String pageIndexString = req.getParameter("pageid");
		int page_index;
		
		try
		{ page_index = Integer.parseInt(pageIndexString); }
		catch (Exception e)
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final int pageIndex = (pageIndexString == null) ? 0 : page_index;
		final DataListPage page = new DataListPage(pageIndex, DataListPage.POSTS_PAGE_LIMIT);
		
		final List<Post> posts = DBManager.getInstance().getDaoFactory().getPostDao().findByFollowedUsers(user, page);
		
		req.setAttribute("posts", posts);
		sendView(req, resp);		
	}
	
	public static void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String jspPage = "posting/news_content.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}
	
	public static JsonArray getPosts( final User user )
	{
		final List<Post> posts = DBManager.getInstance().getDaoFactory().getPostDao()
									.findByFollowedUsers(user, DataListPage.DEFAULT_POSTS_PAGE);
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
