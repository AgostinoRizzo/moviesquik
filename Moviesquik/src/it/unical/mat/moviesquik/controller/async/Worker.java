/**
 * 
 */
package it.unical.mat.moviesquik.controller.async;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Agostino
 *
 */
public abstract class Worker extends Thread
{
	private boolean stop = false;
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	
	public void finalize()
	{
		lock.lock();
		stop = true;
		cond.signal();
		lock.unlock();
	}
	
	@Override
	public void run()
	{
		lock.lock();
		
		while ( !stop )
		{
			doWork();
			
			if ( !stop )
				onFinalize();
		}
		
		lock.unlock();
	}
	
	protected void waitForNextWork( final long time )
	{
		try
		{ cond.await(time, TimeUnit.MILLISECONDS); } 
		catch (InterruptedException e)
		{}
	}
	
	protected abstract void doWork();
	protected abstract void onFinalize();
}
