/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import it.unical.mat.moviesquik.persistence.DBManager;

/**
 * @author Agostino
 *
 */
public class Initializer implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		DBManager.getInstance().getFiller()
			.fillMediaContents(sce.getServletContext().getRealPath(File.separator));
	}
}
