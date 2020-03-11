/**
 * 
 */

function read_notifications(navbar) {
	
	var ajax_url = "notification?action=readall";
	
	$.ajax(
			{
				type: "GET",
				url: ajax_url,
				success: function(data)
					{
						navbar.find(".badge").css("visibility", "hidden");
					}
			}
		);
}

$(document).ready( function()
		{
			$(document).on("click", "#notifications-menu-btn", function() { read_notifications($(this).closest(".navbar-nav")); } );
		});