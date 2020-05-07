/**
 * 
 */

$(document).ready(function() {
	
	$(document).on("click", ".card-img-top", function() {
		
		var ajax_url = "getmedia?key=" + $(this).closest(".media-content-card").find("#media-id").val();
		
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
	
});