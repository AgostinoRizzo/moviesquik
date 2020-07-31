/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.movieparty;

import java.util.List;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyParticipation;

/**
 * @author Agostino
 *
 */
public interface MoviePartyParticipationDao
{
	public List<MoviePartyParticipation> findByMovieParty( final MovieParty party );
}
