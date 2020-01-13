/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

/**
 * @author Agostino
 *
 */
public interface InsertTransaction<E>
{
	public boolean execute( final E data );
}
