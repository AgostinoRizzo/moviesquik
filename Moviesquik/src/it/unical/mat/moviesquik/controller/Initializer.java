/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import it.unical.mat.moviesquik.controller.analytics.MediaAnalyticsHistoryLogUpdater;
import it.unical.mat.moviesquik.controller.movieparty.sync.MoviePartySyncUpdater;
import it.unical.mat.moviesquik.model.streaming.StreamServiceSync;
import it.unical.mat.moviesquik.persistence.DBConnectionPool;
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
		DBManager.getFileSystemDataSource().setSrcPath(sce.getServletContext().getRealPath(""));
		
		StreamServiceSync.getInstance().start();
		MoviePartySyncUpdater.getInstance().start();
		MediaAnalyticsHistoryLogUpdater.getInstance().start();
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		MoviePartySyncUpdater.getInstance().finalize();
		MediaAnalyticsHistoryLogUpdater.getInstance().finalize();
		DBConnectionPool.getInstance().finalize();
	}
}
