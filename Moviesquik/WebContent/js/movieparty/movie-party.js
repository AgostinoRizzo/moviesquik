/**
 * 
 */

import './movie-party-sync.js';


$(document).ready( function() 
{
	$(".party-info-modal").modal('show');
	$(document).on("click", "#join-btn", onJoin);
	setMediaStartedFlag($("#join-btn").length > 0);
	openSocket();
});