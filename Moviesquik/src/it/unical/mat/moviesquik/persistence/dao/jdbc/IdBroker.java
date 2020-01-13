/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Agostino
 *
 */
public class IdBroker
{
	private static final String NEXT_ID_QUERY = "select nextval('primary_key_sequence') as id";
	
	public static Long getNextId( StatementPrompterJDBC statementPrompter ) throws SQLException
	{
		final ResultSet result = statementPrompter.prepareStatement(NEXT_ID_QUERY).executeQuery();
		if ( result.next() )
			return result.getLong("id");
		throw new SQLException();
	}
}
