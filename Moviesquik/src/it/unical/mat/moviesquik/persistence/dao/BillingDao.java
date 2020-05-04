/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

import it.unical.mat.moviesquik.model.Billing;
import it.unical.mat.moviesquik.model.Family;

/**
 * @author Agostino
 *
 */
public interface BillingDao
{
	public Billing findByPrimaryKey( final Long key );
	public Billing findCurrent( final Family family );
	public Billing findNextUpdate( final Family family );
	public List<Billing> findHistory( final Family family );
	public boolean update( final Billing billing );
}
