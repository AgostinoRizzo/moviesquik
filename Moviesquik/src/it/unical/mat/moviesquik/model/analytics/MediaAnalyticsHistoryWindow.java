/**
 * 
 */
package it.unical.mat.moviesquik.model.analytics;

/**
 * @author Agostino
 *
 */
public enum MediaAnalyticsHistoryWindow
{
	LAST_3DAYS, LAST_WEEK, LAST_2WEEKS, LAST_MONTH;
	
	public static int getDaysCount( final MediaAnalyticsHistoryWindow win )
	{
		switch (win) {
		case LAST_3DAYS:  return  3;
		case LAST_WEEK:   return  7;
		case LAST_2WEEKS: return 14;
		case LAST_MONTH:  return 31;
		default:          return  0;
		}
	}
	
	public static MediaAnalyticsHistoryWindow parse( final String str )
	{
		if ( str == null || str.isEmpty() )
			return LAST_WEEK;
		
		if ( str.equals("last-3days") )  return LAST_3DAYS;
		if ( str.equals("last-week") )   return LAST_WEEK;
		if ( str.equals("last-2weeks") ) return LAST_2WEEKS;
		if ( str.equals("last-month") )  return LAST_MONTH;
		
		return LAST_WEEK;
	}
}
