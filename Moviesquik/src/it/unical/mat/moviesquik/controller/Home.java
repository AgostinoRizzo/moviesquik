/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Agostino
 *
 */
public class Home extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		final RequestDispatcher rd = req.getRequestDispatcher("index.html");
		rd.forward(req, resp);
	}
}
