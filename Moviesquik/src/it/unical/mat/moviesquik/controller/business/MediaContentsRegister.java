/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class MediaContentsRegister extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
		if ( admin == null )
		{
			req.getRequestDispatcher("/business/login.jsp").forward(req, resp);
			return;
		}
		
		final String check = req.getParameter("check");
		if ( check != null && check.equals("true") )
			manageMediaRegistrationCheck(req, resp);
		else
			manageMediaRegistration(req, resp);
	}
	
	private void manageMediaRegistrationCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final JsonObject mediaSearchResult = JSONUtil.readFromReader(req.getReader());
		
		try
		{
			final JsonArray mediaResults = mediaSearchResult.getAsJsonArray("Search");
			JsonObject mediaJsonObject;
			
			for ( Iterator<JsonElement> iter = mediaResults.iterator(); iter.hasNext(); )
				try
				{
					mediaJsonObject = iter.next().getAsJsonObject();
					mediaJsonObject.addProperty("registered", !DBManager.getInstance().getDaoFactory().getMediaContentDao()
							.findByTitleYear(mediaJsonObject.get("Title").getAsString(), mediaJsonObject.get("Year").getAsShort()).isEmpty() );
				}
				catch (Exception e) 
				{ iter.remove(); }
		}
		catch (Exception e) 
		{ mediaSearchResult.addProperty("error", true); }
		
		ServletUtils.sendJson(mediaSearchResult, resp);
	}
	
	private void manageMediaRegistration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final JsonObject response = new JsonObject();
		try
		{
			final MediaContent mediaContent = JSONUtil.readClassFromReader(req.getReader(), MediaContent.class);
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			
			mediaContent.setStreamTime(null);
			
			if ( daoFactory.getMediaContentDao().findByTitleYear
					(mediaContent.getTitle(), mediaContent.getYear()).isEmpty() && daoFactory.getMediaContentDao().save(mediaContent) )
				response.addProperty("success", true);
		}
		catch (Exception e) 
		{}
		
		ServletUtils.sendJson(response, resp);
	}
}
