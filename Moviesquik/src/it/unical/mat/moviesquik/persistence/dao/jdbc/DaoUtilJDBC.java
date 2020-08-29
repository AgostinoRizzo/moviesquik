/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public class DaoUtilJDBC
{
	public static String addQueryOffsetLimit( final String query, final DataListPage dataListPage )
	{
		return query + " offset " + Integer.toString(dataListPage.getOffset()) + 
						" limit " + Integer.toString(dataListPage.getLimit());
	}
}
