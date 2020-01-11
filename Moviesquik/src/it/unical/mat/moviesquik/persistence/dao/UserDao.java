/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface UserDao extends DataAccessObject<User>
{
	public List<User> findByFamily( final Family family );
}
