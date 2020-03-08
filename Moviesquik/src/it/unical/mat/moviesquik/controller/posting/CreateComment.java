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
import it.unical.mat.moviesquik.model.Comment;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class CreateComment extends HttpServlet
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
		
		final Long post_id = Long.parseLong( req.getParameter("postid") );
		final String commentText = req.getParameter("text");
		
		final Post referredPost = new Post();
		referredPost.setId(post_id);
		
		final Comment newComment = new Comment();
		newComment.setDateTime( DateUtil.getCurrent() );
		newComment.setText(commentText);
		newComment.setOwner(user);
		newComment.setReferredPost(referredPost);
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		daoFactory.getCommentDao().save(newComment);
		
		req.setAttribute("post_to_display", daoFactory.getPostDao().findById(post_id));
		
		final RequestDispatcher rd = req.getRequestDispatcher("posting/comments_list.jsp");
		rd.forward(req, resp);
	}
}
