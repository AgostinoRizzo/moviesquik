/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface UserProfileImageDao
{
	public String findByUser( final User user );
}
