/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.Exception;
import it.unical.mat.moviesquik.model.Notification;
import it.unical.mat.moviesquik.model.NotificationFactory;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.movieparty.InvitationAnswer;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyParticipation;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.DateUtil;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class MovieParty extends HttpServlet
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
		
		final String action = req.getParameter("action");
		if ( action != null && action.equals("new") )
		{
			req.setAttribute("watchlist", user.getWatchlists().get(0));
			req.getRequestDispatcher("movieparty/create-party.jsp").forward(req, resp);
			return;
		}
		
		Long partyId;
		try
		{ 
			partyId = Long.parseLong(req.getParameter("key"));
		}
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		it.unical.mat.moviesquik.model.movieparty.MovieParty party = 
				DBManager.getInstance().getDaoFactory().getMoviePartyDao().findById(partyId, user);
		if ( party == null )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		MoviePartyInvitation userInvitation = findUserInvitation(party, user);
		
		if ( action != null && action.equals("invitation") )
		{
			final MoviePartyInvitation newUserInvitation = manageInvitationAction( req, userInvitation, party, user );
			if ( !newUserInvitation.equals(userInvitation) )
			{
				party = DBManager.getInstance().getDaoFactory().getMoviePartyDao().findById(partyId, user);
				userInvitation = newUserInvitation;
			}
		}
		
		req.setAttribute("party", party);
		req.setAttribute("invitation", userInvitation);
		
		String path = "movieparty/movie-party-wrapper.jsp";
		if ( req.getParameter("update") != null )
		{
			req.setAttribute("user", user);
			path = "movieparty/movie-party.jsp";
		}
		req.getRequestDispatcher(path).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final User user = SessionManager.checkUserAuthentication(req, resp, false);
		if ( user == null )
		{
			ServletUtils.manageSessionError(req, resp);
			return;
		}
		
		final it.unical.mat.moviesquik.model.movieparty.MovieParty party = 
				new it.unical.mat.moviesquik.model.movieparty.MovieParty();
		
		party.setName( req.getParameter("name") );
		party.setDescription( req.getParameter("description") );
		party.setStartDateTime( DateUtil.parseDateTimeFormat( req.getParameter("date"), req.getParameter("time") ) );
		party.setCreationDateTime( DateUtil.getCurrent() );
		
		Long mediaContentId;
		try { mediaContentId = Long.parseLong( req.getParameter("choosen-media-content") ); }
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		party.setMedia( daoFactory.getMediaContentDao().findById(mediaContentId) );
		
		final JsonArray invitedFriends = JSONUtil.fromStringToArray( req.getParameter("invited-users") );
		final List<MoviePartyInvitation> invitations = new ArrayList<MoviePartyInvitation>();
		
		MoviePartyInvitation invitation;
		for( final JsonElement elem : invitedFriends )
		{
			invitation = new MoviePartyInvitation();
			invitation.setParty(party);
			invitation.setGuest(daoFactory.getUserDao().findByPrimaryKey(elem.getAsLong()));
			invitations.add(invitation);
		}
		
		party.setPrivate(!invitations.isEmpty());
		party.setAdministrator(user);
		party.setInvitations(invitations);
		party.setParticipations(new ArrayList<MoviePartyParticipation>());
		
		if ( DateUtil.getCurrent().before(party.getStartDateTime()) )
		{
			// save media party.
			
			if ( daoFactory.getMoviePartyDao().save(party) )
			{
				// send invitation notifications to all invited friends.
				
				for ( final MoviePartyInvitation mpi : party.getInvitations() )
				{
					final Notification notification = NotificationFactory.getInstance().createMoviePartyInvitationNotification(party);
					daoFactory.getNotificationDao().save(notification, mpi.getGuest());
				}
				
				req.setAttribute("success", "");
				req.setAttribute("startTime", DateUtil.toString(party.getStartDateTime()));
			}
			else
				req.setAttribute("error", new Exception("creation_error"));
		}
		else
			req.setAttribute("error", new Exception("datetime_error"));
		
		req.getRequestDispatcher("/movieparty/info.jsp").forward(req, resp);
	}
	
	private MoviePartyInvitation manageInvitationAction( final HttpServletRequest req, MoviePartyInvitation currentInvitation, 
														 final it.unical.mat.moviesquik.model.movieparty.MovieParty currParty, final User usr )
	{
		if ( currentInvitation == null && currParty.isPrivate() )
			return null;
		
		String value = req.getParameter("value");
		if ( value == null )
			return currentInvitation;
		
		value = value.trim();
		InvitationAnswer answer = null;
		String takenAction = null;
		
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		
		if ( currParty.isPrivate() )
		{
			if ( value.equals("participate") )
				{ answer = InvitationAnswer.PARTICIPATE; takenAction = "on_participate"; }
			else if ( value.equals("maybe") )
				{ answer = InvitationAnswer.MAYBE; takenAction = "on_maybe"; }
			else if ( value.equals("not") )
				{ answer = InvitationAnswer.NOT; takenAction = "on_not"; }
			
			if ( answer != null )
			{
				final InvitationAnswer prevAnswer = currentInvitation.getAnswer();
				currentInvitation.setAnswer(answer);
				if ( !daoFactory.getMoviePartyInvitationDao().update(currentInvitation) )
					currentInvitation.setAnswer(prevAnswer);
				else if ( takenAction != null )
					req.setAttribute(takenAction, true);
			}
		}
		else if ( value.equals("participate") )
		{
			final MoviePartyInvitation newInvitation = new MoviePartyInvitation();
			newInvitation.setParty(currParty);
			newInvitation.setGuest(usr);
			newInvitation.setAnswer(InvitationAnswer.PARTICIPATE);
			
			if ( daoFactory.getMoviePartyInvitationDao().save(newInvitation) )
			{
				req.setAttribute("on_participate", true);
				return newInvitation;
			}
		}
		
		return currentInvitation;
	}
	
	public static MoviePartyInvitation findUserInvitation( final it.unical.mat.moviesquik.model.movieparty.MovieParty party, final User usr )
	{
		final List<MoviePartyInvitation> invitations = party.getInvitations();
		for ( final MoviePartyInvitation invitation : invitations )
			if ( invitation.getGuest().getId().equals( usr.getId() ) )
				return invitation;
		return null;
	}
}
