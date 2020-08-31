/**
 * 
 */
package it.unical.mat.moviesquik.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * @author Agostino
 *
 */
public class ConfigUtil
{
	public static final String FACEBOOK_APP_CONFIG_FILENAME = "configs/fb-app.config";
	public static final String EXTERNAL_OMDB_API_CONFIG_FILENAME = "configs/omdb-api.config";
	public static final String GOOGLE_ANALYTICS_API_CONFIG_FILENAME = "configs/google-analytics-api.config";
	public static final String MOVIESQUIK_API_CONFIG_FILENAME = "configs/moviesquik-api.config";
	public static final String NY_TIMES_REVIEWS_API_CONFIG_FILENAME = "configs/nytimes-reviews-api.config";
	
	public static Properties loadConfigFile( final String filename, final ServletContext context )
	{
		final Properties config = new Properties();
		InputStream stream;
		
		try { stream = new FileInputStream( context.getRealPath(filename) ); } 
		catch (FileNotFoundException e) 
		{ return config; }
		
		try { config.load(stream); } 
		catch (IOException e) {}
		finally 
		{
			try { stream.close(); } 
			catch (IOException e) {}
		}
		
		return config;
	}
}
