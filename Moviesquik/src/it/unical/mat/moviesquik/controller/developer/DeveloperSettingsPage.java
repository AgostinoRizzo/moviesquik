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
public class DeveloperSettingsPage extends HttpServlet
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
		
		if ( user.getFamily().getBillingReport().getCurrent().canAccessDeveloperAPI() )
		{
			final String assistantServiceKey = APIKeyManager.getAssistantServiceAPI(getServletContext());
			final DeveloperSetting setting = DBManager.getInstance().getDaoFactory().getDeveloperSettingDao().findByUser(user);
			
			req.setAttribute("assistant_service_key", assistantServiceKey);
			req.setAttribute("developer_setting", setting);
		}
		
		req.getRequestDispatcher("developer/developer-settings-wrapper.jsp").forward(req, resp);
	}
}
