/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.accounting.Family;
import it.unical.mat.moviesquik.model.accounting.User;

/**
 * @author Agostino
 *
 */
public interface FamilyDao extends DataAccessObject<Family>
{
	public Family findByMember( final User component );
	public Family findByEmail( final String email );
	public Family findByLogin( final String email, final String password );
}
