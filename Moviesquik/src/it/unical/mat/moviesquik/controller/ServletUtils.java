/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
		for ( final String s : items ) {System.out.println(s);
			if ( s.trim().startsWith("filename") )
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
		}
		return "";		
	}
}
