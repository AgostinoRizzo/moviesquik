/**
 * 
 */

$(document).ready(function() {
	
//	$(document).on("click", ".card-img-top", function() { 
//		location.href="getmedia?key=" + $(this).closest(".media-content-card").find("#media-id").val(); });
	
	var previous_page_content = "";
	
	$(document).on("click", ".card-img-top", function() {
		
		var ajax_url = "getmedia?key=" + $(this).closest(".media-content-card").find("#media-id").val();
		
		previous_page_content = $("#page-content").html();
		$("#page-content").html('<div class="container" id="media-content-loading-container"><div class="loader loader-sm"></div></div');
		
		$.ajax(
				{
					type: "GET",
					url: ajax_url,
					dataType: "html",
					success: function(data)
						{
							$("#page-content").html(data);
						}
				}
			);
		
	});
	
	$(document).on("click", "#mc-page-back", function() {
		if ( previous_page_content.length > 0 )
			{
				$("#page-content").html(previous_page_content);
				previous_page_content = "";
			}
	});
	
});