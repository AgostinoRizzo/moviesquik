/**
 * 
 */
package it.unical.mat.moviesquik.controller.streaming;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class Watch extends HttpServlet
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
		
		if ( req.getParameter("party") != null )
		{
			manageMoviePartyWatch(req, resp, mediaContentId, user);
			return;
		}
		
		req.setAttribute("media_content", DBManager.getInstance().getDaoFactory().getMediaContentDao().findById(mediaContentId));
		req.getRequestDispatcher("streaming/watch.jsp").forward(req, resp);
	}
	
	private void manageMoviePartyWatch( final HttpServletRequest req, final HttpServletResponse resp, final Long partyId, final User usr ) 
										throws ServletException, IOException
	{
		final MovieParty party = DBManager.getInstance().getDaoFactory().getMoviePartyDao().findById(partyId, usr);
		if ( party == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final MoviePartyInvitation invitation = 
				it.unical.mat.moviesquik.controller.movieparty.MovieParty.findUserInvitation(party, usr);
		
		req.setAttribute("media_content", party.getMedia());
		req.setAttribute("party", party);
		req.setAttribute("invitation", invitation);
		req.setAttribute("curr_watch_timestamp", party.getCurrentWatchingTimestamp());
		req.getRequestDispatcher("streaming/watch.jsp").forward(req, resp);
	}
}
