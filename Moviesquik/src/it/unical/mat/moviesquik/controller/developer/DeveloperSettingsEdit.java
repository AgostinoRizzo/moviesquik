/**
 * 
 */
package it.unical.mat.moviesquik.controller.developer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.developer.DeveloperSetting;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class DeveloperSettingsEdit extends HttpServlet
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
		
		if ( user.getIsKid() )
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		final String action = req.getParameter("action");
		final String subject = req.getParameter("subject");
		
		final String assistantServiceKey = APIKeyManager.getAssistantServiceAPI(getServletContext());
		final DeveloperSetting setting = manageEditing(user, action, subject, assistantServiceKey);
		
		req.setAttribute("assistant_service_key", assistantServiceKey);
		req.setAttribute("developer_setting", setting);
		
		req.getRequestDispatcher("../developer-settings.jsp").forward(req, resp);
	}
	
	private DeveloperSetting manageEditing( final User user, final String action, final String subject, final String assistantServiceKey )
	{
		DeveloperSetting setting = DBManager.getInstance().getDaoFactory()
				.getDeveloperSettingDao().findByUser(user);
		
		if ( action == null || subject == null )
			return setting;
		
		boolean update = action.equals("activate") ? manageActivation(setting, subject, assistantServiceKey) 
												   : manageDeactivation(setting, subject);
		
		if ( update )
			setting = DBManager.getInstance().getDaoFactory().getDeveloperSettingDao().findByUser(user);
		
		return setting;
	}
	
	private boolean manageActivation( final DeveloperSetting setting, final String subject, final String assistantServiceKey )
	{
		boolean update = false;
		
		if ( subject.equals("api_key") )
		{
			setting.setActive(true);
			if ( setting.getApiKey() == null )
				setting.setApiKey( APIKeyManager.generateNewAPIKey() );
			update = true;
		}
		else if ( subject.equals("assistant_service_key") )
		{
			setting.setAssistantServiceKey(assistantServiceKey);
			update = true;
		}
		else if ( subject.equals("play_action") )
		{
			setting.setPlayAction(true);
			update = true;
		}
		
		if ( update )
			DBManager.getInstance().getDaoFactory().getDeveloperSettingDao().update(setting);
		
		return update;
	}
	
	private boolean manageDeactivation( final DeveloperSetting setting, final String subject )
	{
		boolean update = false;
		
		if ( subject.equals("api_key") )
		{
			setting.setActive(false);
			update = true;
		}
		else if ( subject.equals("assistant_service_key") )
		{
			setting.setAssistantServiceKey(null);
			update = true;
		}
		else if ( subject.equals("play_action") )
		{
			setting.setPlayAction(false);
			update = true;
		}
		
		if ( update )
			DBManager.getInstance().getDaoFactory().getDeveloperSettingDao().update(setting);
		
		return update;
	}
	
}
