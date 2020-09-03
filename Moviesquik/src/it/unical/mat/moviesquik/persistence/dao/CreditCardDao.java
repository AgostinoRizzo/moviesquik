/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.model.accounting.CreditCard;

/**
 * @author Agostino
 *
 */
public interface CreditCardDao
{
	public boolean save( final CreditCard card );
	public boolean existsMatch( final CreditCard card );
	public CreditCard findByPrimaryKey( final Long key );
}
