/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import java.util.List;

/**
 * @author Agostino
 *
 */
public interface DataAccessObject<E>
{
	public boolean save( final E obj );
	public E findByPrimaryKey( final Long key );
	public List<E> findAll();
	public boolean update( final E obj );
	public boolean delete( final E obj );
}
