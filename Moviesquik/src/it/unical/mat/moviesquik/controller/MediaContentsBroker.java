/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentsBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final int MAX_FIND_COUNT = 10;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String policy = req.getParameter("policy");
		
		if ( policy == null )
			return;
		
		List<MediaContent> mediaContents = null;
		
		if ( policy.equals("most_rated") )
			mediaContents = 
				DBManager.getInstance().getDaoFactory().getMediaContentDao().findMostRated(MAX_FIND_COUNT);
		
		else if ( policy.equals("most_popular") )
			mediaContents = 
			DBManager.getInstance().getDaoFactory().getMediaContentDao().findMostPopular(MAX_FIND_COUNT);
		
		else if ( policy.equals("most_favorites") )
			mediaContents = 
			DBManager.getInstance().getDaoFactory().getMediaContentDao().findMostFavorites(MAX_FIND_COUNT);
		
		else if ( policy.equals("suggested") || policy.equals("recently") )
		{
			final User user = (User) req.getSession().getAttribute("user");
			if ( user != null )
				mediaContents = 
				(policy.equals("suggested"))
				? DBManager.getInstance().getDaoFactory().getMediaContentDao().findSuggested(MAX_FIND_COUNT, user)
				: DBManager.getInstance().getDaoFactory().getMediaContentDao().findRecentlyWatched(MAX_FIND_COUNT, user);
			else
				mediaContents = new ArrayList<MediaContent>();
		}
		
		else
			mediaContents = new ArrayList<MediaContent>();
		
		mediaContents.addAll(mediaContents);
		Collections.shuffle(mediaContents);
		
		filterByCount(mediaContents, req);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		final PrintWriter out = resp.getWriter();
		out.print(JSONUtil.fromListToString(mediaContents));
		out.flush();
	}
	
	private void filterByCount( final List<MediaContent> contents, final HttpServletRequest req )
	{
		if ( contents.isEmpty() )
			return;
		
		try
		{
			final int count = Integer.parseInt( req.getParameter("count") );
			contents.stream().limit(count);
		}
		catch (Exception e) 
		{}
	}
}