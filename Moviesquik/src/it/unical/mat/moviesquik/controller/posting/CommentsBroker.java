/**
 * 
 */
package it.unical.mat.moviesquik.controller.posting;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class CommentsBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final String postIdString = req.getParameter("postid");
		Long post_id;
		
		try
		{ post_id = Long.parseLong(postIdString); }
		catch (Exception e)
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		final Post referredPost = daoFactory.getPostDao().findById(post_id);
		if ( referredPost == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		referredPost.setComments( daoFactory.getCommentDao().findByPost(referredPost, DataListPage.INFINITE_PAGE) );
		
		req.setAttribute("post_to_display", referredPost);
		sendView(req, resp);		
	}
	
	public static void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String jspPage = "posting/comments_list.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}
}
