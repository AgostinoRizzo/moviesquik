/**
 * 
 */
package it.unical.mat.moviesquik.controller.developer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.developer.DeveloperSetting;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.util.JSONUtil;

/**
 * @author Agostino
 *
 */
public class APIRequestManager extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		final JsonObject jsonRequest = JSONUtil.readClassFromReader(req.getReader(), JsonObject.class);
		boolean success = false;
		
		try
		{
			final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
			final Long userId = jsonRequest.get("user_id").getAsLong();
			final User user = daoFactory.getUserDao().findByPrimaryKey(userId);
			final DeveloperSetting developerSetting = daoFactory.getDeveloperSettingDao().findByUser(user);
			
			if ( !user.getIsKid() && user.getFamily().getBillingReport().getCurrent().canAccessDeveloperAPI() &&
					developerSetting.getApiKey().equals(jsonRequest.get("api_key").getAsString()) &&
					developerSetting.getAssistantServiceKey().equals(jsonRequest.get("service_key").getAsString()) )
			{
				final String query = jsonRequest.get("query").getAsString();
				success = APITriggerQueryManager.manageTriggerQuery(developerSetting, user, query);
			}
		}
		catch (Exception e) {}
		
		ServletUtils.sendJson(createJsonResponse(success), resp);
	}
	
	private static JsonObject createJsonResponse( final boolean success )
	{
		final JsonObject response = new JsonObject();
		if ( success )
			response.addProperty("success", true);
		else
			response.addProperty("error", "");
		return response;
	}
	
}
