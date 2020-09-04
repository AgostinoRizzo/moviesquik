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
			$(contents_items_tag_id + " .content-ratings-box .rate-value").eq(i).text(media_content.statistics.actualRate);
			$(contents_items_tag_id + " .media-content-card").eq(i).removeClass('d-none');
			
			$(contents_items_tag_id).parent().find(".view-all-btn").removeClass("invisible");
		}
	}
};

function append_media_contents(contents_items_tag_id, data)
{
	var content_html = $(contents_items_tag_id).find(".media-col-item:first")[0].outerHTML;
	var start = $(contents_items_tag_id).find(".media-col-item").length;
	
	var rowColAdded = 0;;
	
	var fullHtml = '';
	
	for ( var i in data )
	{
		var addColRow = (i % 4) == 0;
		
		if ( addColRow )
		{
			if ( rowColAdded > 0 )
				fullHtml += '</div></div>';
			fullHtml += ( rowColAdded > 0 ? '</div>' : '' ) + '<div class="col-6"><div class="row">';
			rowColAdded = 1;
		}
		
		fullHtml += content_html;
	}
	
	if ( rowColAdded > 0 )
		fullHtml += '</div></div>';
	
	$(contents_items_tag_id).append(fullHtml);
	
	for ( var i in data )
	{
		var real_i = +start + +i;
		var media_content = data[i];
		
		$(contents_items_tag_id + " img").eq(real_i).attr("src", media_content.poster);
		$(contents_items_tag_id + " .card-title").eq(real_i).text(media_content.title);
		$(contents_items_tag_id + " .card-text").eq(real_i).text(media_content.production);
	}
};

function request_media_contents(contents_items_tag_id, url_str, fill_callback, onAppend=false)
{
	$(contents_items_tag_id).closest('.media-contents-list').find('.loader').removeClass('d-none');
	
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
						
						if ( onAppend )
							$(contents_items_tag_id).parent().find(".more-collapse-box").removeClass("d-none");
						$(contents_items_tag_id).closest('.media-contents-list').find('.loader').addClass('d-none');
					}
			}
		);
	}
};

function request_more_media_contents(contents_items_tag_id, policy)
{
	$(contents_items_tag_id).parent().find(".more-collapse-box").addClass("d-none");
	
	var itemsCount = $(contents_items_tag_id).find(".media-col-item").length;
	if ( (itemsCount % N_ITEMS_IN_ROW) != 0 )
		return;
	var start = itemsCount / N_ITEMS_IN_ROW;
	//N_ITEMS_IN_ROW = start;
	request_media_contents(contents_items_tag_id, "listcontents?policy=" + policy + "&start=" + start, append_media_contents, true);
	
	$(contents_items_tag_id).parent().find(".view-all-btn").addClass("d-none");
}

function on_collapse_view_media_contents(contents_items_tag_id)
{	
	$(contents_items_tag_id).parent().find(".more-collapse-box").addClass("d-none");
	
	$(contents_items_tag_id).parent().find(".view-all-btn").removeClass("d-none");
	
	while ( $(contents_items_tag_id).find(".media-col-item").length > N_ITEMS_IN_ROW )
		$(contents_items_tag_id).find(".media-col-item").eq(N_ITEMS_IN_ROW).remove();
}

function initMediaContentCards(parentTagId) 
{
	$(parentTagId).find(".media-content-card").each( function() 
	{
		$(this).addClass('d-none');
	});
}

$(document).ready( function()
	{
		/* media contents requests */
		
		initMediaContentCards("#suggested-media-contents-items");
		initMediaContentCards("#maylike-media-contents-items");
		initMediaContentCards("#trending-now-media-contents-items");
		initMediaContentCards("#most-popular-media-contents-items");
		initMediaContentCards("#top-rated-media-contents-items");
		initMediaContentCards("#most-favorites-media-contents-items");
		initMediaContentCards("#recently-watched-media-contents-items");
	
		request_media_contents("#suggested-media-contents-items", "listcontents?policy=suggested", fill_media_contents);
		request_media_contents("#maylike-media-contents-items", "listcontents?policy=maylike", fill_media_contents);
		request_media_contents("#trending-now-media-contents-items", "listcontents?policy=trending", fill_media_contents);
		request_media_contents("#most-popular-media-contents-items", "listcontents?policy=most_popular", fill_media_contents);
		request_media_contents("#top-rated-media-contents-items", "listcontents?policy=most_rated", fill_media_contents);
		request_media_contents("#most-favorites-media-contents-items", "listcontents?policy=most_favorites", fill_media_contents);
		request_media_contents("#recently-watched-media-contents-items", "listcontents?policy=recently", fill_media_contents);
		
		$("#suggested-view-all-btn").click( function()        { request_more_media_contents("#suggested-media-contents-items", "suggested");           });
		$("#maylike-view-all-btn").click( function()          { request_more_media_contents("#maylike-media-contents-items", "maylike");               });
		$("#trending-now-view-all-btn").click( function()     { request_more_media_contents("#trending-now-media-contents-items", "trending");         });
		$("#most-popular-view-all-btn").click( function()     { request_more_media_contents("#most-popular-media-contents-items", "most_popular");     });
		$("#top-rated-view-all-btn").click( function()        { request_more_media_contents("#top-rated-media-contents-items", "most_rated");          });
		$("#most-favorites-view-all-btn").click( function()   { request_more_media_contents("#most-favorites-media-contents-items", "most_favorites"); });
		$("#recently-watched-view-all-btn").click( function() { request_more_media_contents("#recently-watched-media-contents-items", "recently");      });
		
		$("#suggested-view-more-btn").click( function()        { request_more_media_contents("#suggested-media-contents-items", "suggested");           });
		$("#maylike-view-more-btn").click( function()          { request_more_media_contents("#maylike-media-contents-items", "maylike");               });
		$("#trending-now-view-more-btn").click( function()     { request_more_media_contents("#trending-now-media-contents-items", "trending");               });
		$("#most-popular-view-more-btn").click( function()     { request_more_media_contents("#most-popular-media-contents-items", "most_popular");     });
		$("#top-rated-view-more-btn").click( function()        { request_more_media_contents("#top-rated-media-contents-items", "most_rated");          });
		$("#most-favorites-view-more-btn").click( function()   { request_more_media_contents("#most-favorites-media-contents-items", "most_favorites"); });
		$("#recently-watched-view-more-btn").click( function() { request_more_media_contents("#recently-watched-media-contents-items", "recently");     });
		
		$("#suggested-view-collapse-btn").click( function()        { on_collapse_view_media_contents("#suggested-media-contents-items");        });
		$("#maylike-view-collapse-btn").click( function()          { on_collapse_view_media_contents("#maylike-media-contents-items");          });
		$("#trending-now-view-collapse-btn").click( function()     { on_collapse_view_media_contents("#trending-now-media-contents-items");     });
		$("#most-popular-view-collapse-btn").click( function()     { on_collapse_view_media_contents("#most-popular-media-contents-items");     });
		$("#top-rated-view-collapse-btn").click( function()        { on_collapse_view_media_contents("#top-rated-media-contents-items");        });
		$("#most-favorites-view-collapse-btn").click( function()   { on_collapse_view_media_contents("#most-favorites-media-contents-items");   });
		$("#recently-watched-view-collapse-btn").click( function() { on_collapse_view_media_contents("#recently-watched-media-contents-items"); });
		
	}
);