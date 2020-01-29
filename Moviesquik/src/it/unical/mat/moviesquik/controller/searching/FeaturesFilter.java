/**
 * 
 */
package it.unical.mat.moviesquik.controller.searching;

import com.google.gson.JsonObject;

/**
 * @author Agostino
 *
 */
public enum FeaturesFilter
{
	ANY, _4K, HD;
	
	public static FeaturesFilter getFromJson( final JsonObject json )
	{
		return parse( json.get("features").getAsString() );
	}
	
	public static FeaturesFilter parse( final String str )
	{
		if ( str == null )
			return ANY;
		if ( str.equals("4K") ) return _4K;
		if ( str.equals("HD") ) return HD;
		return ANY;
	}
}
