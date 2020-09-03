/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.accounting.User;

/**
 * @author Agostino
 *
 */
public interface UserGenreDao
{
	public List<String> findFavorites( final User user );
}
