/**
 * 
 */
package it.unical.mat.moviesquik.controller.chat;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.chat.ChatMessageProxy;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class ChatBroker extends HttpServlet
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
		
		Long messageOffsetId = null;
		try { messageOffsetId = Long.parseLong(req.getParameter("offsetid")); }
		catch (NumberFormatException e) {}
		
		List<ChatMessageProxy> messages;
		
		try 
		{
			final Long groupId = Long.parseLong(req.getParameter("groupid"));
			messages = DBManager.getInstance().getDaoFactory().getChatMessageDao()
													.findAllGroup(groupId, messageOffsetId);
		}
		catch (NumberFormatException e) 
		{
			try
			{
				final Long userId = Long.parseLong(req.getParameter("userid"));
				messages = DBManager.getInstance().getDaoFactory().getChatMessageDao()
														.findAllUser(userId, messageOffsetId);
			}
			catch (NumberFormatException e2) 
			{
				ServletUtils.manageParameterError(req, resp);
				return;
			}
		}
		
		ServletUtils.sendJsonArray( createJsonMessagesArray(messages), resp );
	}
	
	private JsonArray createJsonMessagesArray( final List<ChatMessageProxy> messages )
	{
		final Gson gson = new Gson();
		final JsonArray allJsonMessages = new JsonArray();
		
		for ( final ChatMessageProxy msg : messages )
			allJsonMessages.add( gson.toJsonTree( new ChatMessagePacket(msg) ) );
		
		return allJsonMessages;
	}
}
