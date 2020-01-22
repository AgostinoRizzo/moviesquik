/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.unical.mat.moviesquik.persistence.DataSource;

/**
 * @author Agostino
 *
 */
public class StatementPrompterJDBC
{
	private final DataSource dataSource;
	private Connection connection = null;
	
	public StatementPrompterJDBC( final DataSource dataSource )
	{
		this.dataSource = dataSource;
	}
	
	public PreparedStatement prepareStatement( final String sql ) throws SQLException
	{
		onFinalize();
		connection = dataSource.getNewConnection();
		return connection.prepareStatement(sql);
	}
	
	public void startTransaction() throws SQLException
	{
		onFinalize();
		connection = dataSource.getNewConnection();
		connection.setAutoCommit(false);
	}
	
	public void commit() throws SQLException
	{
		connection.commit();
	}
	
	public void onFinalize()
	{
		try
		{
			if ( connection != null )
				connection.close();
		} 
		catch (SQLException e)
		{ e.printStackTrace(); }
		
		finally 
		{ connection = null; }
	}
	
	public DataSource getDataSource()
	{
		return dataSource;
	}
	
}
