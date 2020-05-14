/**
 * 
 */
package it.unical.mat.moviesquik.controller.streaming;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.streaming.StreamService;
import it.unical.mat.moviesquik.model.streaming.StreamServiceTable;
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
		
		ServletUtils.sendJson(createJsonManifest(mediaContentId), resp);
	}
	private JsonObject createJsonManifest( final long mediaContentId )
	{
		final JsonObject manifest = new JsonObject();
		
		final List<StreamService> services = StreamServiceTable.getInstance().getStreamServices();
		final JsonArray servers = new JsonArray();
		
		for ( final StreamService s : services )
			servers.add(s.getUrl());
		
		manifest.addProperty("streamspath", "/streams/mc-" + mediaContentId + "/");
		manifest.add("servers", servers);
		
		return manifest;
	}
}
