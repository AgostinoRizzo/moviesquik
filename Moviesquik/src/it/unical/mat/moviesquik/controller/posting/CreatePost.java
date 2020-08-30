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
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.Post;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class CreatePost extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final String postText = req.getParameter("post-text");
		final Post newPost = new Post();
		newPost.setDateTime( DateUtil.getCurrent() );
		newPost.setText(postText);
		newPost.setOwner(user);
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		daoFactory.getPostDao().save(newPost);
		
		//user.getPosts();
		//PostsBroker.sendView(req, resp);
		
		final RequestDispatcher rd = req.getRequestDispatcher("/");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}
}
