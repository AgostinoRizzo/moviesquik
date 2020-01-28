/**
 * 
 */
package it.unical.mat.moviesquik.model;

/**
 * @author Agostino
 *
 */
public enum MediaContentGroup
{
	TOP_RATED,
	MOST_POPULAR,
	MOST_FAVORITES,
	SUGGESTED,
	RECENTLY_WATCHED;
	
	@Override
	public String toString()
	{
		switch (this) {
		case TOP_RATED:        return "top_rated";
		case MOST_POPULAR:     return "most_viewed";
		case SUGGESTED:        return "suggested";
		case RECENTLY_WATCHED: return "recently_watched";
		default:               return "";
		}
	}
}
