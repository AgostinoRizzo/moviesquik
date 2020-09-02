/**
 * 
 */
package it.unical.mat.moviesquik.controller.accounting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import it.unical.mat.moviesquik.controller.ServletUtils;
import it.unical.mat.moviesquik.controller.SessionManager;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 2)
public class UserProfileUpload extends HttpServlet
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
		if ( action != null && action.equals("delete") )
		{
			user.setProfileImagePath(null);
			DBManager.getInstance().getDaoFactory().getUserDao().update(user);
			
			final RequestDispatcher rd = req.getRequestDispatcher("user");
			rd.forward(req, resp);
			
			return;
		}
		else
			ServletUtils.manageParameterError(req, resp);
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
		
		if ( ServletFileUpload.isMultipartContent(req) )
		{
			try
			{
				final Part filePart = req.getPart("user-profile-img");
				if ( filePart != null )
				{					
					final InputStream inStream = filePart.getInputStream();
					
					if ( inStream != null )
					{
						final String fileName = getUserProfileImageFileName( filePart.getContentType(), user );
						final File file = new File( DBManager.getFileSystemDataSource().getUserProfileImagePath() + 
													File.separator + fileName );
						
						final FileOutputStream out = new FileOutputStream(file);
						
						int readValue = inStream.read();
						while ( readValue != -1 )
						{
							out.write(readValue);
							readValue = inStream.read();
						}
						
						out.close();
						
						user.setProfileImagePath(fileName);
						DBManager.getInstance().getDaoFactory().getUserDao().update(user);
						
						final RequestDispatcher rd = req.getRequestDispatcher("user");
						rd.forward(req, resp);
						
						return;
					}
				}
			}
			catch (Exception e)
			{}
		}
		
		ServletUtils.manageParameterError(req, resp);
	}
	
	private String getUserProfileImageFileName( final String contentType, final User user )
	{
		final int i = contentType.lastIndexOf("/");
		if ( i >= 0 )
			return user.getId().toString() + "." + contentType.substring(i+1);
		return user.getId().toString();
	}
	
}
