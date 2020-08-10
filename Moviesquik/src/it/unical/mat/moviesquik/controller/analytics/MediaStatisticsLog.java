/**
 * 
 */
package it.unical.mat.moviesquik.controller.analytics;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

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
			
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			
			if ( event.equals("scroll") )
				daoFactory.getMediaStatisticLogDao().logScroll(user.getId(), mediaContentId);
			else if ( event.equals("time") )
			{
				final Integer spentTime = Integer.parseInt(req.getParameter("val"));
				daoFactory.getMediaStatisticLogDao().logSpentTime(user.getId(), mediaContentId, spentTime);
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
