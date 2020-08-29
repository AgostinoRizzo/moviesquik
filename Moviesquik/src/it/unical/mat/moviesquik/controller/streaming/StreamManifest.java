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

import it.unical.mat.moviesquik.analytics.AnalyticsFacade;
import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.model.streaming.PlanStreamingQualityManager;
import it.unical.mat.moviesquik.model.streaming.StreamService;
import it.unical.mat.moviesquik.model.streaming.StreamServiceTable;
import it.unical.mat.moviesquik.model.streaming.StreamingQuality;
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
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
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
		
		final MediaContent mediaContent = DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaContentId);
		if ( mediaContent != null )
			AnalyticsFacade.getLogger().logNewMediaWatch(user, mediaContent);
		
		ServletUtils.sendJson(createJsonManifest(mediaContentId, user), resp);
	}
	private JsonObject createJsonManifest( final long mediaContentId, final User user )
	{
		final JsonObject manifest = new JsonObject();
		
		final List<StreamService> services = StreamServiceTable.getInstance().getStreamServices();
		final JsonArray servers = new JsonArray();
		
		for ( final StreamService s : services )
			servers.add(s.getUrl());
		
		final StreamingQuality streamingQuality = PlanStreamingQualityManager.getInstance()
				.getBillingStreamingQuality(user.getFamily().getBillingReport().getCurrent());
		
		manifest.addProperty("streamspath", "/streams/mc-" + mediaContentId + "/" + StreamingQuality.toString(streamingQuality) + "/");
		manifest.add("servers", servers);
		
		return manifest;
	}
}
