/**
 * 
 */
package it.unical.mat.moviesquik.controller.api;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.util.ConfigUtil;

/**
 * @author Agostino
 *
 */
public class FacebookAppConfigsBroker extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final JsonObject jsonResp = createJsonResponse();
		ServletUtils.sendJson(jsonResp, resp);
	}
	
	private JsonObject createJsonResponse()
	{
		final Properties config = ConfigUtil.loadConfigFile(ConfigUtil.FACEBOOK_APP_CONFIG_FILENAME, getServletContext());
		final JsonObject resp = new JsonObject();
		
		final Set<String> properties = config.stringPropertyNames();
		for ( final String prop : properties )
			resp.addProperty(prop, config.getProperty(prop));
		
		return resp;
	}
}
