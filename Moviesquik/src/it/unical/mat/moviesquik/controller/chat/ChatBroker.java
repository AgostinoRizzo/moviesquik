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
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.model.chat.ChatMessage;
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
		
		Long groupId;
		try { groupId = Long.parseLong(req.getParameter("groupid"));}
		catch (NumberFormatException e) 
		{
			ServletUtils.manageParameterError(req, resp);
			return;
		}
		
		Long messageOffsetId = null;
		try { messageOffsetId = Long.parseLong(req.getParameter("offsetid")); }
		catch (NumberFormatException e) {}
		
		final List<ChatMessage> messages = DBManager.getInstance().getDaoFactory().getChatMessageDao()
												.findAll(groupId, messageOffsetId);
		
		ServletUtils.sendJsonArray( createJsonMessagesArray(messages), resp );
	}
	
	private JsonArray createJsonMessagesArray( final List<ChatMessage> messages )
	{
		final Gson gson = new Gson();
		final JsonArray allJsonMessages = new JsonArray();
		
		for ( final ChatMessage msg : messages )
			allJsonMessages.add( gson.toJsonTree( new ChatMessagePacket(msg) ) );
		
		return allJsonMessages;
	}
}
