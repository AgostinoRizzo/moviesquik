/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.SearchResult;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.searching.DBSearchEngine;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class Search extends HttpServlet
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
		final SearchRequestType reqtype = SearchRequestType.parse( req.getParameter("reqtype") );
		final SortingPolicy sortingPolicy = SortingPolicy.parse( req.getParameter("sorting_policy") );
		final DBSearchEngine searchEngine = DBManager.getSearchEngine();
		final SearchResult result = new SearchResult();
		
		searchEngine.searchMediaContentsByQuery( searchQuery, sortingPolicy, result );
		if ( reqtype == SearchRequestType.FULL )
			searchEngine.searchUsersByQuery( searchQuery, result );
		
		req.setAttribute("search_result", result);
		sendView(req, resp, reqtype);
	}
	
	private void sendView(HttpServletRequest req, HttpServletResponse resp, SearchRequestType reqtype) throws ServletException, IOException
	{	
		final String jspPage = ( reqtype == SearchRequestType.MEDIA_CONTENTS_UPDATE )
								? "search-result.jsp" : "search.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher("searching/" + jspPage);
		rd.forward(req, resp);
	}
}
