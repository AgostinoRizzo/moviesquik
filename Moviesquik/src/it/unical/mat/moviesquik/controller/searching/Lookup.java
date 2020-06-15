/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.SearchResult;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.searching.DBSearchEngine;

/**
 * @author Agostino
 *
 */
public class Lookup extends HttpServlet
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
		
		final String searchQuery = req.getParameter("query");
		if ( searchQuery == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final String type = req.getParameter("type");
		if ( type == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		if ( type.equals("user") )
			manageUserLookup(req, resp, searchQuery, user);
		else if ( type.equals("media") )
			manageMediaLookup(req, resp, searchQuery);
		else
			ServletUtils.manageSessionError(req, resp);
	}
	
	private void manageUserLookup( final HttpServletRequest req, final HttpServletResponse resp, final String searchQuery, final User usr ) 
			throws IOException
	{
		final String friends = req.getParameter("friends");
		final boolean onlyFriends = friends != null && friends.equals("true");
		
		final DBSearchEngine searchEngine = DBManager.getSearchEngine();
		final SearchResult result = new SearchResult();
		
		if ( onlyFriends )
			searchEngine.searchUsersByQuery( searchQuery, result, usr );
		else
			searchEngine.searchUsersByQuery( searchQuery, result );
		
		sendUserJson(req, resp, result.getUsers());
	}
	
	private void manageMediaLookup( final HttpServletRequest req, final HttpServletResponse resp, final String query )
	{
		
	}
	
	private void sendUserJson( final HttpServletRequest req, final HttpServletResponse resp, final List<User> users ) throws IOException
	{
		final PrintWriter out = resp.getWriter();
		final JsonArray json = new JsonArray();
		
		
		for ( final User usr : users)
		{
			final JsonObject usr_json = new JsonObject();
			usr_json.addProperty("id", usr.getId());
			usr_json.addProperty("fullname", usr.getFullName());
			usr_json.addProperty("email", usr.getEmail());
			usr_json.addProperty("profileimg", usr.getProfileImagePath());
			json.add(usr_json);
		}
		
		out.print(json.toString());
		out.flush();
	}
}
