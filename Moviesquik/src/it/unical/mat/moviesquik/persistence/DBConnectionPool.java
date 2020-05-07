/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Agostino
 *
 */
public class DBConnectionPool implements Runnable
{
	public static final int  CAPACITY   = 1;
	public static final long KEEP_ALIVE = 10000;
	
	private static DBConnectionPool instance = null;
	
	// opened connections available to clients and not used.
	private final Queue<Connection> availableConnections = new LinkedList<Connection>();
	private final Map<Thread, Connection> usedConnections = new HashMap<Thread, Connection>();
	private final Map<Connection, Long> availableConnectionCloseTimes = new HashMap<Connection, Long>();
	
	private final Lock lock = new ReentrantLock();
	private final Condition fullUsedConnectionsCond       = lock.newCondition();
	private final Condition usedConnectionsCond           = lock.newCondition();
	private final Condition emptyAvailableConnectionsCond = lock.newCondition();
	private final Condition waitCloseTimeCond             = lock.newCondition();
	
	private final Thread connectionCloser;
	
	public static DBConnectionPool getInstance()
	{
		if ( instance == null )
			instance = new DBConnectionPool();
		return instance;
	}
	private DBConnectionPool()
	{
		connectionCloser = new Thread(this);
		connectionCloser.start();
	}	
	public Connection getConnection() throws SQLException
	{
		final Thread currentThread = Thread.currentThread();
		try
		{
			lock.lock();
			
			Connection conn = usedConnections.get(currentThread);
			if ( conn != null )
				return conn;
			
			while ( usedConnections.size() >= CAPACITY )
				fullUsedConnectionsCond.await();
			
			if ( availableConnections.isEmpty() )
				conn = DBManager.getDataSource().getNewConnection();
			else
			{
				conn = availableConnections.remove();
				availableConnectionCloseTimes.remove(conn);
			}
			
			usedConnections.put(currentThread, conn);
			printStatus("get");
			
			return conn;
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{ lock.unlock(); }
	}
	public void releaseConnection()
	{
		final Thread currentThread = Thread.currentThread();
		try
		{
			lock.lock();
			
			final Connection conn = usedConnections.get(currentThread);
			if ( conn == null )
				return;
			
			if ( usedConnections.size() == CAPACITY )
				fullUsedConnectionsCond.signalAll();
			if ( usedConnections.isEmpty() )
				usedConnectionsCond.signalAll();
			if ( availableConnections.isEmpty() )
				emptyAvailableConnectionsCond.signal();
			
			usedConnections.remove(currentThread);
			availableConnections.add(conn);
			availableConnectionCloseTimes.put(conn, System.currentTimeMillis() + KEEP_ALIVE);
			
			printStatus("release");
		}
		finally
		{ lock.unlock(); }
	}
	public void finalize()
	{
		try
		{
			lock.lock();
			
			while ( !usedConnections.isEmpty() )
				usedConnectionsCond.await();
			
			connectionCloser.interrupt();
			
			for ( final Connection c : availableConnections )
				try { c.close(); } 
				catch (SQLException e) { e.printStackTrace(); }
			
			availableConnections.clear();
			availableConnectionCloseTimes.clear();
			
			printStatus("finalization");
			// TODO: add close boolean variable.
		} 
		catch (InterruptedException e)
			{ e.printStackTrace(); }
		finally 
			{ lock.unlock(); }
	}
	@Override
	public void run()
	{
		lock.lock();
		
		try 
		{ 
			while ( !connectionCloser.isInterrupted() )
			{
				while ( availableConnections.isEmpty() )
					emptyAvailableConnectionsCond.await();
				
				final long minCloseTime = Collections.min(availableConnectionCloseTimes.values());
				long timeToWait = minCloseTime - System.currentTimeMillis();
				
				if ( timeToWait > 0 )
					waitCloseTimeCond.await(timeToWait, TimeUnit.MILLISECONDS);
					
				if ( closeConnectionWithCloseTime(minCloseTime) )
					printStatus("garbage collection");
			}
		}
		catch (InterruptedException e) 
		{}
		finally
		{ lock.unlock(); }
	}
	private void printStatus( final String operation )
	{
		System.out.println("############ DB CONNECTION POOL ############\n" + 
						   "After " + operation + " operation:\n" +
						   "\tAvailable connections: " + availableConnections.size() + "\n" +
						   "\tUsed connections: " + usedConnections.size() + "\n");
	}
	private boolean closeConnectionWithCloseTime( final long closeTime )
	{
		final List<Connection> toClose = new ArrayList<Connection>();
		
		for ( final Entry<Connection, Long> keyValue : availableConnectionCloseTimes.entrySet() )
			if ( keyValue.getValue().equals(closeTime) )
				toClose.add(keyValue.getKey());
		
		for ( final Connection c : toClose )
		{
			try { c.close(); } 
			catch (SQLException e) { e.printStackTrace(); }
			availableConnectionCloseTimes.remove(c);
			availableConnections.remove(c);
		}
		
		return !toClose.isEmpty();
	}
}
