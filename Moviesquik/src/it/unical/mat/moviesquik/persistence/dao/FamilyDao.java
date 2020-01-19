/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;

/**
 * @author Agostino
 *
 */
public interface FamilyDao extends DataAccessObject<Family>
{
	public Family findByMember( final User component );
}
