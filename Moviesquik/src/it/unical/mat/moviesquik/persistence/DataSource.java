/**
 * 
 */
package it.unical.mat.moviesquik.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Agostino
 *
 */
public class DataSource
{
	private final String uri;
	private final String username;
	private final String password;
	
	public DataSource( final String uri, final String username, final String password )
	{
		this.uri = uri;
		this.username = username;
		this.password = password;
	}
	
	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(uri, username, password);
	}
	
}
