/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty.sync;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import it.unical.mat.moviesquik.model.NotificationFactory;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;

/**
 * @author Agostino
 *
 */
public class MoviePartySyncUpdater extends Thread
{
	private static MoviePartySyncUpdater instance = null;
	
	private boolean stop = false;
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	
	public static MoviePartySyncUpdater getInstance()
	{
		if ( instance == null )
			instance = new MoviePartySyncUpdater();
		return instance;
	}
	
	private MoviePartySyncUpdater()
	{}
	
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
			final long targetTime = computeNextTargetTime();
			
			long residual = getResidualTime(targetTime);
			while ( residual > 0 && !stop )
			{
				try
				{ cond.await(residual, TimeUnit.MILLISECONDS); } 
				catch (InterruptedException e)
				{}
				residual = getResidualTime(targetTime);
			}
			
			if ( !stop )
			{
				MoviePartySynchronizer.sendUpdateCheck();
				sendMoviePartyNotifications();
			}
		}
		
		lock.unlock();
	}
	
	private static long getResidualTime( final long targetTime )
	{
		final long residual = targetTime - System.currentTimeMillis();
		if ( residual > 0 )
			return residual;
		return 0;
	}
	
	private static long computeNextTargetTime()
	{
		final long currMillisTime = System.currentTimeMillis();
		
		Calendar currCal = Calendar.getInstance();
		currCal.setTimeInMillis(currMillisTime);
		
		Calendar nextCal = Calendar.getInstance();
		nextCal.set(Calendar.YEAR, currCal.get(Calendar.YEAR));
		nextCal.set(Calendar.MONTH, currCal.get(Calendar.MONTH));
		nextCal.set(Calendar.DAY_OF_MONTH, currCal.get(Calendar.DAY_OF_MONTH));
		nextCal.set(Calendar.HOUR, currCal.get(Calendar.HOUR));
		nextCal.set(Calendar.MINUTE, currCal.get(Calendar.MINUTE) + 1);
		nextCal.set(Calendar.SECOND, 0);
		nextCal.set(Calendar.MILLISECOND, 0);
		
		long residual = nextCal.getTimeInMillis() - currMillisTime;
		if ( residual < 0 )
			residual = 0;
		
		return currMillisTime + residual;
	}
	
	private void sendMoviePartyNotifications()
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final NotificationFactory notificationFactory = NotificationFactory.getInstance();
		
		List<MovieParty> parties = daoFactory.getMoviePartyDao().findAllStartedNow();
		for ( final MovieParty party : parties )
			it.unical.mat.moviesquik.controller.movieparty.MovieParty
				.sendNotificationToAllParticipants( notificationFactory.createMoviePartyStartedNotification(party), party, null, true );
		
		parties = daoFactory.getMoviePartyDao().findAllEndedNow();
		for ( final MovieParty party : parties )
			it.unical.mat.moviesquik.controller.movieparty.MovieParty
				.sendNotificationToAllParticipants( notificationFactory.createMoviePartyEndedNotification(party), party, null, true );
		
		final int[] minutes = {5, 3, 1};
		for ( final int min : minutes )
		{
			parties = daoFactory.getMoviePartyDao().findAllStarting(min);
			for ( final MovieParty party : parties )
				it.unical.mat.moviesquik.controller.movieparty.MovieParty
					.sendNotificationToAllParticipants( notificationFactory.createMoviePartyReminderNotification(party, min), party, null, true );
		}
			
	}
}
