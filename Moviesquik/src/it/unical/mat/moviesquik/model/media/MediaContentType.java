/**
 * 
 */
package it.unical.mat.moviesquik.model.media;

/**
 * @author Agostino
 *
 */
public enum MediaContentType
{
	MOVIE, TV_SHOW, ALL;
	
	public static MediaContentType parse( final String str )
	{
		if ( str == null )
			return null;
		str.trim();
		if ( str.equals("movie") )   return MOVIE;
		if ( str.equals("tv_show") ) return TV_SHOW;
		return null;
	}
	
	@Override
	public String toString()
	{
		switch (this) {
		case MOVIE:   return "movie";
		case TV_SHOW: return "tv_show";
		default:      return "";
		}
	}
}
