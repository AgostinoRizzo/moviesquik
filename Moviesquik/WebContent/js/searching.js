/**
 * 
 */

function request_media_contents(url_str)
{
	$.ajax(
		{
			type: "GET",
			url: url_str,
			dataType: "html",
			success: function(data)
				{
					$("#search-result").html(data);
					$("#media-contents-search-result-title")
						.html( $("#media-contents-search-update-result-title").html() );
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