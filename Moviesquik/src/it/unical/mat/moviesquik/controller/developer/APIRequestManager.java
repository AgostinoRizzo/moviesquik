/**
 * 
 */
package it.unical.mat.moviesquik.controller.developer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;

/**
 * @author Agostino
 *
 */
public class APIRequestManager extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("On API Request!");
		ServletUtils.sendJson(createJsonResponse(true), resp);
	}
	
	private static JsonObject createJsonResponse( final boolean success )
	{
		final JsonObject response = new JsonObject();
		if ( success )
			response.addProperty("success", true);
		else
			response.addProperty("error", "");
		return response;
	}
	
}
