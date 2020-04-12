/**
 * 
 */

var N_ITEMS_IN_ROW = 8

function fill_media_contents(contents_items_tag_id, data)
{
	for ( var i in data )
	{
		if ( i < N_ITEMS_IN_ROW )
		{
			var media_content = data[i];
			$(contents_items_tag_id + " #media-id").eq(i).attr("value", media_content.id);
			$(contents_items_tag_id + " img").eq(i).attr("src", media_content.poster);
			$(contents_items_tag_id + " .card-title").eq(i).text(media_content.title);
			$(contents_items_tag_id + " .card-text").eq(i).text(media_content.production);
			
			$(contents_items_tag_id).parent().find(".view-all-btn").removeClass("invisible");
		}
	}
};

function append_media_contents(contents_items_tag_id, data)
{
	var content_html = $(contents_items_tag_id).children(".media-col-item:first")[0].outerHTML;
	var start = $(contents_items_tag_id).children(".media-col-item").length;
	
	for ( var i in data )
	{
		var real_i = +start + +i;
		var media_content = data[i];
			
		$(contents_items_tag_id).append(content_html);
		
		$(contents_items_tag_id + " img").eq(real_i).attr("src", media_content.poster);
		$(contents_items_tag_id + " .card-title").eq(real_i).text(media_content.title);
		$(contents_items_tag_id + " .card-text").eq(real_i).text(media_content.production);
	}
};

function request_media_contents(contents_items_tag_id, url_str, fill_callback)
{
	if ( $(contents_items_tag_id).length )
	{
		$.ajax(
			{
				type: "GET",
				url: url_str,
				dataType: "json",
				success: function(data)
					{					
						fill_callback(contents_items_tag_id, data);
					}
			}
		);
	}
};

function request_more_media_contents(contents_items_tag_id, policy)
{
	var start = $(contents_items_tag_id).children(".media-col-item").length;
	N_ITEMS_IN_ROW = start;
	request_media_contents(contents_items_tag_id, "listcontents?policy=" + policy + "&start=" + start, append_media_contents);
	
	$(contents_items_tag_id).parent().find(".view-all-btn").addClass("d-none");
	$(contents_items_tag_id).parent().find(".more-collapse-box").removeClass("d-none");
}

function on_collapse_view_media_contents(contents_items_tag_id)
{	
	$(contents_items_tag_id).parent().find(".more-collapse-box").addClass("d-none");
	
	$(contents_items_tag_id).parent().find(".view-all-btn").removeClass("d-none");
	
	while ( $(contents_items_tag_id).children(".media-col-item").length > N_ITEMS_IN_ROW )
		$(contents_items_tag_id).children().eq(N_ITEMS_IN_ROW).remove();
}

$(document).ready( function()
	{
		/* media contents requests */
	
		request_media_contents("#suggested-media-contents-items", "listcontents?policy=suggested", fill_media_contents);
		request_media_contents("#recently-media-contents-items", "listcontents?policy=recently", fill_media_contents);
		request_media_contents("#top-rated-media-contents-items", "listcontents?policy=most_rated", fill_media_contents);
		request_media_contents("#most-popular-media-contents-items", "listcontents?policy=most_popular", fill_media_contents);
		request_media_contents("#most-favorites-media-contents-items", "listcontents?policy=most_favorites", fill_media_contents);
		
		$("#suggested-view-all-btn").click( function() { request_more_media_contents("#suggested-media-contents-items", "suggested"); });
		$("#recently-view-all-btn").click( function() { request_more_media_contents("#recently-media-contents-items", "recently"); });
		$("#top-rated-view-all-btn").click( function() { request_more_media_contents("#top-rated-media-contents-items", "most_rated"); });
		$("#most-popular-view-all-btn").click( function() { request_more_media_contents("#most-popular-media-contents-items", "most_popular"); });
		$("#most-favorites-view-all-btn").click( function() { request_more_media_contents("#most-favorites-media-contents-items", "most_favorites"); });
		
		$("#suggested-view-more-btn").click( function() { request_more_media_contents("#suggested-media-contents-items", "suggested"); });
		$("#recently-view-more-btn").click( function() { request_more_media_contents("#recently-media-contents-items", "recently"); });
		$("#top-rated-view-more-btn").click( function() { request_more_media_contents("#top-rated-media-contents-items", "most_rated"); });
		$("#most-popular-view-more-btn").click( function() { request_more_media_contents("#most-popular-media-contents-items", "most_popular"); });
		$("#most-favorites-view-more-btn").click( function() { request_more_media_contents("#most-favorites-media-contents-items", "most_favorites"); });
		
		$("#suggested-view-collapse-btn").click( function() { on_collapse_view_media_contents("#suggested-media-contents-items"); });
		$("#recently-view-collapse-btn").click( function() { on_collapse_view_media_contents("#recently-media-contents-items"); });
		$("#top-rated-view-collapse-btn").click( function() { on_collapse_view_media_contents("#top-rated-media-contents-items"); });
		$("#most-popular-view-collapse-btn").click( function() { on_collapse_view_media_contents("#most-popular-media-contents-items"); });
		$("#most-favorites-view-collapse-btn").click( function() { on_collapse_view_media_contents("#most-favorites-media-contents-items"); });
		
	}
);