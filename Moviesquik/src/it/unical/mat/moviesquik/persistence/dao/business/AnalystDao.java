/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.business;

import it.unical.mat.moviesquik.model.business.Analyst;

/**
 * @author Agostino
 *
 */
public interface AnalystDao
{
	public Analyst findByLogin( final String email, final String password );
}
