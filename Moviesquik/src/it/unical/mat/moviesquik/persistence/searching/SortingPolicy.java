/**
 * 
 */
package it.unical.mat.moviesquik.persistence.searching;

/**
 * @author Agostino
 *
 */
public enum SortingPolicy
{
	NONE, 
	SUGGESTED,
	YEAR_RELEASED,
	TITLE_ASC,
	TITLE_DESC;
	
	public static SortingPolicy parse( final String str )
	{
		if ( str == null )
			return NONE;
		if ( str.equals("suggested") )     return SUGGESTED;
		if ( str.equals("year-released") ) return YEAR_RELEASED;
		if ( str.equals("title-asc") )     return TITLE_ASC;
		if ( str.equals("title-desc") )    return TITLE_DESC;
		
		return NONE;
	}
}
