/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.movieparty;

import java.util.List;

import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;

/**
 * @author Agostino
 *
 */
public interface MoviePartyInvitationDao
{
	public boolean save( final MoviePartyInvitation invitation );
	public boolean update( final MoviePartyInvitation invitation );
	public List<MoviePartyInvitation> findByMovieParty( final MovieParty party );
}
