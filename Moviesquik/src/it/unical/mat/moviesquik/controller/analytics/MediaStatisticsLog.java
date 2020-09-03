/**
 * 
 */
package it.unical.mat.moviesquik.controller.analytics;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.analytics.AnalyticsFacade;
import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.User;

/**
 * @author Agostino
 *
 */
public class MediaStatisticsLog extends HttpServlet
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
		
		try
		{
			final Long mediaContentId = Long.parseLong(req.getParameter("key"));
			final String event = req.getParameter("event");
			
			if ( event == null )
				throw new IllegalArgumentException();
			
			if ( event.equals("scroll") )
				AnalyticsFacade.getLogger().logMediaPageScroll(user.getId(), mediaContentId);
			else if ( event.equals("time") )
			{
				final Integer spentTime = Integer.parseInt(req.getParameter("val"));
				AnalyticsFacade.getLogger().logMediaPageSpentTime(user.getId(), mediaContentId, spentTime);
			}
			else
				throw new IllegalArgumentException();
		}
		catch (Exception e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}	
	}
}
