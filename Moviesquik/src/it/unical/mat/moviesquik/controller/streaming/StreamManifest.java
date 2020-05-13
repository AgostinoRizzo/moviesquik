/**
 * 
 */
package it.unical.mat.moviesquik.controller.streaming;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class StreamManifest extends HttpServlet
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
		
		Long mediaContentId;
		try { mediaContentId = Long.parseLong( req.getParameter("key") ); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		DBManager.getInstance().getDaoFactory().getMediaContentDao().updateNewViewById(mediaContentId);
		
		ServletUtils.sendJson(createJsonManifest(), resp);
	}
	private JsonObject createJsonManifest()
	{
		final JsonObject manifest = new JsonObject();
		
		final JsonArray servers = new JsonArray();
		servers.add("http://192.168.1.92:8000");
		
		manifest.addProperty("streamspath", "/streams/mc-12/");
		manifest.add("servers", servers);
		
		return manifest;
	}
}
