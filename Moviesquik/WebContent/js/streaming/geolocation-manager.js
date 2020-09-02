/**
 * 
 */

import '../popup-alert.js';

window.getGeolocationCoords = function( callback ) 
{
	function success(position) 
	{
		showMainAlert("Geolocation service active");
		callback(position);
	}
	
	function error(error) 
	{
		showMainAlert("Geolocation service not allowed");
		callback(null);
	}
	
	if ( navigator.geolocation )
		navigator.geolocation.getCurrentPosition(success, error);
}