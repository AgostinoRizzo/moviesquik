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
		final SortingPolicy sortingPolicy = SortingPolicy.parse( req.getParameter("sorting_policy") );
		final SearchResult result = DBManager.getSearchEngine().searchByQuery( searchQuery, sortingPolicy );
		req.setAttribute("search_result", result);
		sendView(req, resp);
	}
	
	private void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final String reqtype = req.getParameter("reqtype");		
		final String jspPage = ( reqtype != null && reqtype.equals("update") )
								? "search-result.jsp" : "search.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher("searching/" + jspPage);
		rd.forward(req, resp);
	}
}
