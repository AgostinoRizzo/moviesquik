/**
 * 
 */
package it.unical.mat.moviesquik.controller.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.business.Analyst;
import it.unical.mat.moviesquik.model.business.CDNServer;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class ContextDistributionNetworkBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Analyst admin = SessionManager.checkAdminAuthentication(req, resp, false);
		if ( admin == null )
		{
			req.getRequestDispatcher("/business/login.jsp").forward(req, resp);
			return;
		}
		
		final List<CDNServer> cdnServers = 
				DBManager.getInstance().getDaoFactory().getCDNServerDao().findAll();
		req.setAttribute("cdnservers", cdnServers);
		
		ServletUtils.sendJson(createJsonResponse(cdnServers), resp);
	}
	
	private static JsonObject createJsonResponse( final List<CDNServer> serversList )
	{
		final JsonObject response = new JsonObject();
		
		if ( serversList != null )
			response.add("list", JSONUtil.fromListToJsonArray(serversList, CDNServer.class));
		
		return response;
	}
}
