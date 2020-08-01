/**
 * 
 */

import './movie-party-sync.js';


$(document).ready( function() 
{
	$(".modal").modal('show');
	$(document).on("click", "#join-btn", onJoin);
	mediaStarted = $("#join-btn").length > 0;
	openSocket();
});