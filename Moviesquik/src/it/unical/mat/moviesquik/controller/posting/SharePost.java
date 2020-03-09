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
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class SharePost extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = (User) req.getSession().getAttribute("user");
		
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final Long source_post_id = Long.parseLong( req.getParameter("share-post-id") );
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		final Post source_post = daoFactory.getPostDao().findById(source_post_id);
		final Post newPost = new Post();
		newPost.setDateTime( DateUtil.getCurrent() );
		newPost.setText(source_post.getText());
		newPost.setOwner(user);
		
		daoFactory.getPostDao().save(newPost);
		
		final RequestDispatcher rd = req.getRequestDispatcher("/");
		rd.forward(req, resp);
	}
}