/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.JsonObject;

import it.unical.mat.moviesquik.model.Exception;

/**
 * @author Agostino
 *
 */
public class ServletUtils
{
	public static void manageSessionError( HttpServletRequest req, HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		req.getSession().setAttribute("error", new Exception("session_error"));
		
		final RequestDispatcher rd = req.getRequestDispatcher("info.jsp");
		rd.forward(req, resp);
	}
	
	public static void manageParameterError( HttpServletRequest req, HttpServletResponse resp ) 
			throws ServletException, IOException
	{
		req.getSession().setAttribute("error", new Exception("parameter_error"));
		
		final RequestDispatcher rd = req.getRequestDispatcher("info.jsp");
		rd.forward(req, resp);
	}
	
	public static void printSessionAttributes( final HttpServletRequest req )
	{
		final Enumeration<String> attrs = req.getSession().getAttributeNames();
		while ( attrs.hasMoreElements() )
			System.out.println(attrs.nextElement());
	}
	
	public static void printRequestParameters( final HttpServletRequest req )
	{
		final Enumeration<String> pars = req.getParameterNames();
		while ( pars.hasMoreElements() )
			System.out.println(pars.nextElement());
	}
	
	public static void removeAllSessionAttributes( final HttpServletRequest req )
	{
		final Enumeration<String> attrs = req.getSession().getAttributeNames();
		final HttpSession session = req.getSession();
		while ( attrs.hasMoreElements() )
			session.removeAttribute(attrs.nextElement());
	}
	
	public static String extractFileName( final Part part )
	{
		final String contentDisp = part.getHeader("content-disposition");
		final String[] items = contentDisp.split(";");
		for ( final String s : items ) {
			if ( s.trim().startsWith("filename") )
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
		}
		return "";		
	}
	
	public static String readAllBody( final HttpServletRequest req ) throws IOException
	{
		final BufferedReader r = new BufferedReader( new InputStreamReader( req.getInputStream() ) );
		final StringBuilder ans = new StringBuilder();
		
		String line;
		try
		{
			while( (line = r.readLine()) != null )
				ans.append( line );
			return ans.toString();
		} catch 
		(IOException e)
		{ return ""; }
	}
	public static void setJsonContentAndEncoding( final HttpServletResponse resp )
	{
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
	}
	public static void sendJson( final JsonObject json, final HttpServletResponse resp ) throws IOException
	{
		setJsonContentAndEncoding(resp);
		
		final PrintWriter out = resp.getWriter();
		out.print(json);
		out.flush();
	}
}
