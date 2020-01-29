/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import com.google.gson.JsonObject;

/**
 * @author Agostino
 *
 */
public enum DurationFilter
{
	ANY, SHORT, LONG;
	
	public static DurationFilter getFromJson( final JsonObject json )
	{
		return parse( json.get("duration").getAsString() );
	}
	
	public static DurationFilter parse( final String str )
	{
		if ( str == null )
			return ANY;
		if ( str.equals("short") ) return SHORT;
		if ( str.equals("long") )  return LONG;
		return ANY;
	}
}
