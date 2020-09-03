/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.developer;

import it.unical.mat.moviesquik.model.accounting.User;
import it.unical.mat.moviesquik.model.developer.DeveloperSetting;

/**
 * @author Agostino
 *
 */
public interface DeveloperSettingDao
{
	public boolean insert( final DeveloperSetting setting, final User owner );
	public boolean update( final DeveloperSetting setting );
	public DeveloperSetting findByUser( final User owner );
}
