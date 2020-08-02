/**
 * 
 */
package it.unical.mat.moviesquik.controller.movieparty;

/**
 * @author Agostino
 *
 */
public enum MoviePartySearchFilter
{
	ALL, PUBLIC, PRIVATE, PLAYING, UPCOMING, EXPIRED;
	
	public static MoviePartySearchFilter parse( final String str )
	{
		if ( str == null )
			return ALL;
		final String toParseFrom = str.trim().toLowerCase();
		if ( toParseFrom.equals("public") )   return PUBLIC;
		if ( toParseFrom.equals("private") )  return PRIVATE;
		if ( toParseFrom.equals("playing") )  return PLAYING;
		if ( toParseFrom.equals("upcoming") ) return UPCOMING;
		if ( toParseFrom.equals("expired") )  return EXPIRED;
		return ALL;
	}
}
