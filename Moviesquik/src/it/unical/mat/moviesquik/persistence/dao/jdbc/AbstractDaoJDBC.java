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
public abstract class AbstractDaoJDBC<T>
{
	protected final StatementPrompterJDBC statementPrompter;
	
	protected AbstractDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	protected abstract T createFromResult( final ResultSet result ) throws SQLException;
}
