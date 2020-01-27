/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

/**
 * @author Agostino
 *
 */
public enum SearchRequestType
{
	FULL,
	MEDIA_CONTENTS_UPDATE;
	
	public static SearchRequestType parse( final String str )
	{
		if ( str == null )
			return FULL;
		if ( str.equals("mc_update") ) return MEDIA_CONTENTS_UPDATE;
		return FULL;
	}
}
