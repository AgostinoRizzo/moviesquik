/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentsBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String policy = req.getParameter("policy");
		if ( policy != null && policy.equals("most_rated") )
		{
			final List<MediaContent> mediaContents =
					DBManager.getInstance().getDaoFactory().getMediaContentDao().findMostRated();
			resp.getOutputStream().println(JSONUtil.fromListToString(mediaContents));
		}
	}
}
