/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.business;

import java.util.List;

import it.unical.mat.moviesquik.model.business.CDNServer;

/**
 * @author Agostino
 *
 */
public interface CDNServerDao
{
	public List<CDNServer> findAll();
}
