/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.movieparty;

import it.unical.mat.moviesquik.model.movieparty.MoviePartyParticipation;

/**
 * @author Agostino
 *
 */
public interface MoviePartyParticipationDao
{
	public boolean save( final MoviePartyParticipation participation );
}
