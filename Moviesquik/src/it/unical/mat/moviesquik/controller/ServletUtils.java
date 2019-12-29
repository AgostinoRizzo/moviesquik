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
	
	public static void printSessionAttributes( final HttpServletRequest req )
	{
		final Enumeration<String> attrs = req.getSession().getAttributeNames();
		while ( attrs.hasMoreElements() )
			System.out.println(attrs.nextElement());
	}
}
