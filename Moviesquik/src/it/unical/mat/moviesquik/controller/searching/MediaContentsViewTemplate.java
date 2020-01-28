/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

/**
 * @author Agostino
 *
 */
public enum MediaContentsViewTemplate
{
	FULL, GROUP;
	
	public static MediaContentsViewTemplate parse( final String str )
	{
		if ( str == null )
			return null;
		str.trim();
		if ( str.equals("full") )  return FULL;
		if ( str.equals("group") ) return GROUP;
		return null;
	}
}
