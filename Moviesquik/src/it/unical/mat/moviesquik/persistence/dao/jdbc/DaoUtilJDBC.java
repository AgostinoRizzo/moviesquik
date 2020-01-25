/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

/**
 * @author Agostino
 *
 */
public class DaoUtilJDBC
{
	public static String addQueryLimit( final String query, final int limit )
	{
		return query + " limit " + Integer.toString(limit);
	}
}
