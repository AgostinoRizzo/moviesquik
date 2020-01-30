/**
 * 
 */

function request_media_contents(url_str)
{
	$(".loader").removeClass("d-none");
	$("#media-contents-search-result-title").children().hide();
	$("#media-contents-list").children().hide();
	
	$.ajax(
		{
			type: "GET",
			url: url_str,
			dataType: "html",
			success: function(data)
				{
					$(".loader").addClass("d-none");
					$("#search-result").html(data);
					$("#media-contents-search-result-title")
						.html( $("#media-contents-search-update-result-title").html() );
					$("#media-contents-search-result-title").children().show();
					$("#media-contents-list").children().show();
				}
		}
	);
};


$(document).ready( function()
	{
		$("#sorting-policy-select").change(function() {
			
			var query = $(this).siblings("#search-query").val();
			var policy = $(this).val();
			
			request_media_contents("search?query=" + query +  "&sorting_policy=" + policy + "&reqtype=mc_update");
		});		
	}
);