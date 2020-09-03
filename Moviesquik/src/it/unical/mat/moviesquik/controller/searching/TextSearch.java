/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.SearchResult;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.media.MediaContent;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.searching.DBSearchEngine;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class TextSearch extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String searchQuery = req.getParameter("query");
		
		if ( searchQuery != null )
			manageSearchByQuery(searchQuery, req, resp);
		else
			ServletUtils.manageParameterError(req, resp);	
	}
	
	private void manageSearchByQuery( final String searchQuery, HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		final SortingPolicy sortingPolicy = SortingPolicy.parse( req.getParameter("sorting_policy") );
		final DBSearchEngine searchEngine = DBManager.getSearchEngine();
		final SearchResult result = new SearchResult();
		
		searchEngine.searchMediaContentsByQuery( searchQuery, sortingPolicy, result );
		searchEngine.searchUsersByQuery( searchQuery, result );
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		final PrintWriter out = resp.getWriter();
		final JsonArray json = new JsonArray();
		
		final JsonObject query_json = new JsonObject();
		query_json.addProperty("type", "search_query");
		query_json.addProperty("query", searchQuery);
		json.add(query_json);
		
		for ( final MediaContent mc : result.getContents() )
		{
			final JsonObject mc_json = new JsonObject();
			mc_json.addProperty("type", "media_content");
			mc_json.addProperty("id", mc.getId());
			mc_json.addProperty("title", mc.getTitle());
			mc_json.addProperty("subtitle", mc.getProduction());
			json.add(mc_json);
		}
		
		for ( final User usr : result.getUsers())
		{
			final JsonObject usr_json = new JsonObject();
			usr_json.addProperty("type", "user");
			usr_json.addProperty("id", usr.getId());
			usr_json.addProperty("title", usr.getFullName());
			usr_json.addProperty("subtitle", usr.getEmail());
			json.add(usr_json);
		}
		
		out.print(json.toString());
		out.flush();
	}
}
