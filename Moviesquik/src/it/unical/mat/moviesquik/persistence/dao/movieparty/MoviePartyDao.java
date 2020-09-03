/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.movieparty;

import java.util.List;

import it.unical.mat.moviesquik.controller.movieparty.MoviePartySearchFilter;
import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public interface MoviePartyDao
{
	public boolean save( final MovieParty party );
	public List<MovieParty> findAll( final DataListPage page );
	public List<MovieParty> findAllByUser( final User user, final MoviePartySearchFilter filter, final DataListPage page );
	public List<MovieParty> findCommitmentsByUser( final User user, final MoviePartySearchFilter filter, final DataListPage page );
	public MovieParty findById( final Long id, final User user );
	public MovieParty findById( final Long id );
	public List<MovieParty> findAllStartedNow();
	public List<MovieParty> findAllEndedNow();
	public List<MovieParty> findAllStarting( final int minutes );
}
