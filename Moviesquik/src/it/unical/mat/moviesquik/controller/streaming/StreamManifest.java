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
		
		/*
		Long mediaContentId;
		try { mediaContentId = Long.parseLong( req.getParameter("key") ); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		*/
		
		ServletUtils.sendJson(createJsonManifest(), resp);
	}
	private JsonObject createJsonManifest()
	{
		final JsonObject manifest = new JsonObject();
		
		final JsonArray servers = new JsonArray();
		servers.add("http://192.168.1.92:8000");
		
		final JsonArray times = new JsonArray();
		times.add(0.0);
		times.add(10.28);
		times.add(20.57);
		times.add(22.94);
		times.add(33.23);
		times.add(38.4);
		times.add(48.7);
		times.add(53.88);
		times.add(64.17);
		times.add(69.34);
		times.add(79.64);
		times.add(89.94);
		
		manifest.addProperty("duration", 94.48);
		manifest.addProperty("segments", 12);
		manifest.addProperty("streamspath", "/streams/mc-1/");
		manifest.add("servers", servers);
		manifest.add("times", times);
		
		return manifest;
	}
}
