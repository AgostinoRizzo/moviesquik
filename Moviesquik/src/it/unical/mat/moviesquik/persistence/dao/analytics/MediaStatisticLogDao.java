/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.analytics;

/**
 * @author Agostino
 *
 */
public interface MediaStatisticLogDao
{
	public boolean logHit( final Long userId, final Long mediaContentId );
	public boolean logScroll( final Long userId, final Long mediaContentId );
	public boolean logSpentTime( final Long userId, final Long mediaContentId, final Integer time );
}
