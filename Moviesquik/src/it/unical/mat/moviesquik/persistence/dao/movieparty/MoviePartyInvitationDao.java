/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.movieparty;

import it.unical.mat.moviesquik.model.movieparty.MoviePartyInvitation;

/**
 * @author Agostino
 *
 */
public interface MoviePartyInvitationDao
{
	public boolean save( final MoviePartyInvitation invitation );
}
