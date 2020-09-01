/**
 * 
 */
package it.unical.mat.moviesquik.controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;

/**
 * @author Agostino
 *
 */
public class ServletContextPath
{
	private static ServletContext servletContext = null;
	private static final Lock lock = new ReentrantLock();
	private static final Condition contextAvailableCond = lock.newCondition();
	
	public static void setServletContext( final ServletContext context )
	{
		lock.lock();
		servletContext = context;
		contextAvailableCond.signalAll();
		lock.unlock();
	}
	
	public static String getRealPath( final String path )
	{
		lock.lock();
		
		try
		{
			while ( servletContext == null )
			{
				try
				{ contextAvailableCond.await(); } 
				catch (InterruptedException e)
				{}
			}
			
			return servletContext.getRealPath(path);
		}
		finally { lock.unlock(); }
	}
}
