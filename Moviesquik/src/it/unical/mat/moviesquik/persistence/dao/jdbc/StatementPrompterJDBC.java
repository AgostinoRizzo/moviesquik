/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

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
	
	public StatementPrompterJDBC( final DataSource dataSource )
	{
		this.dataSource = dataSource;
	}
	
	public PreparedStatement prepareStatement( final String sql ) throws SQLException
	{
		return dataSource.getConnection().prepareStatement(sql);
	}
	
	public void onFinalize()
	{
		try
		{
			dataSource.getConnection().close();
		} 
		catch (SQLException e)
		{ e.printStackTrace(); }
	}
}
