/**
 * 
 */

import './log-media-content-stats.js';

var previous_page_content = "";

function onMediaContentShow(mckey) 
{
	var ajax_url = "getmedia?key=" + mckey;
	
	previous_page_content = $("#page-content, #movie-party-page-container").html();
	$("#page-content, #movie-party-page-container").html('<div class="container" id="media-content-loading-container"><div class="loader loader-sm"></div></div');
	
	$.ajax(
		{
			type: "GET",
			url: ajax_url,
			dataType: "html",
			success: function(data)
				{
					$("#page-content, #movie-party-page-container").html(data);
					initLogStats();
				}
		}
	);
}

$(document).ready(function() {
	
//	$(document).on("click", ".card-img-top", function() { 
//		location.href="getmedia?key=" + $(this).closest(".media-content-card").find("#media-id").val(); });
	
	$(document).on("click", ".card-img-top", function() {
		var key = $(this).closest(".media-content-card").find("#media-id").val();
		onMediaContentShow(key);
	});
	
	$(document).on("click", ".media-content-link", function() {
		var key = $(this).closest(".media-post-box").find("#media-id").val();
		onMediaContentShow(key);
	});
	
	$(document).on("click", "#mc-page-back", function() {
		finalizeLogStats();
		if ( previous_page_content.length > 0 )
			{
				$("#page-content, #movie-party-page-container").html(previous_page_content);
				previous_page_content = "";
			}
	});
	
});