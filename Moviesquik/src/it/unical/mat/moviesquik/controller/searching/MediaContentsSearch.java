/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.MediaContent;
import it.unical.mat.moviesquik.model.MediaContentGroup;
import it.unical.mat.moviesquik.model.MediaContentSearchResult;
import it.unical.mat.moviesquik.model.MediaContentType;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.searching.SortingPolicy;

/**
 * @author Agostino
 *
 */
public class MediaContentsSearch extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final MediaContentType type = MediaContentType.parse( req.getParameter("type") );
		final MediaContentsViewTemplate viewTemplate = MediaContentsViewTemplate.parse( req.getParameter("view") );
		if ( type == null || viewTemplate == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final User user = (User) req.getSession().getAttribute("user");
		
		switch ( viewTemplate ) {
		case FULL:  manageFullViewTemplate(req, resp, type, viewTemplate, user); break;
		case GROUP: manageGroupViewTemplate(req, resp, type, viewTemplate, user); break;
		default: ServletUtils.manageParameterError(req, resp);
		}
	}
	
	private void manageFullViewTemplate( HttpServletRequest req, HttpServletResponse resp,
											final MediaContentType type, final MediaContentsViewTemplate viewTemplate, final User user )
													throws ServletException, IOException
	{
		final List<MediaContent> mediaContents = DBManager.getSearchEngine()
				.fullMediaContentSearch(type, "", SortingPolicy.NONE, user);
		final MediaContentSearchResult searchResult = new MediaContentSearchResult();
		
		searchResult.setType(type.toString());
		searchResult.setViewTemplate("full");
		searchResult.setFullContents(mediaContents);
		
		req.setAttribute("search_result", searchResult);
		req.setAttribute("genres", DBManager.getInstance().getMediaContentsGenres());
		sendView(req, resp);
	}
	
	private void manageGroupViewTemplate( HttpServletRequest req, HttpServletResponse resp,
											final MediaContentType type, final MediaContentsViewTemplate viewTemplate, final User user ) 
													throws ServletException, IOException
	{
		final Map<MediaContentGroup, List<MediaContent>> groupMediaContents = DBManager.getSearchEngine()
				.groupMediaContentSearch(type, "", SortingPolicy.NONE, user);

		final MediaContentSearchResult searchResult = new MediaContentSearchResult();
		
		searchResult.setType(type.toString());
		searchResult.setViewTemplate("group");
		searchResult.setTopRatedContents( groupMediaContents.get(MediaContentGroup.TOP_RATED) );
		searchResult.setMostPopularContents( groupMediaContents.get(MediaContentGroup.MOST_POPULAR) );
		searchResult.setMostFavoritesContents( groupMediaContents.get(MediaContentGroup.MOST_FAVORITES) );
		searchResult.setSuggestedContents( groupMediaContents.get(MediaContentGroup.SUGGESTED) );
		searchResult.setRecentlyWatchedContents( groupMediaContents.get(MediaContentGroup.RECENTLY_WATCHED) );
		
		req.setAttribute("search_result", searchResult);
		req.setAttribute("genres", DBManager.getInstance().getMediaContentsGenres());
		sendView(req, resp);
	}
	
	private void sendView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final SearchRequestType reqtype = SearchRequestType.parse( req.getParameter("reqtype") );
		final String jspPage = ( reqtype == SearchRequestType.MEDIA_CONTENTS_UPDATE )
								? "searching/type-search-content.jsp" : "searching/type-search.jsp";
		
		final RequestDispatcher rd = req.getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}
}
