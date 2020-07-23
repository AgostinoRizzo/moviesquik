/**
 * 
 */

var posts_page_index = 0;
var movieparties_page_index = 0;

function request_more_posts(view_more_tag) {
	
	++posts_page_index;
	var ajax_url = "getposts?pageid=" + posts_page_index;
	
	view_more_tag.find("#posts-view-more-btn").addClass("d-none");
	view_more_tag.find(".loader").removeClass("d-none");
	
	$.ajax(
			{
				type: "GET",
				url: ajax_url,
				dataType: "html",
				success: function(data)
					{
						var has_items = $($.parseHTML(data)).filter(".media").length > 0;
						
						if ( has_items )
							{
								view_more_tag.prev("#news-list").append(data);
								view_more_tag.find("#posts-view-more-btn").removeClass("d-none");
							}
						
						view_more_tag.find(".loader").addClass("d-none");
					}
			}
		);
}

function request_all_posts(view_more_tag) {
	
	var ajax_url = "getcomments?postid=" + view_more_tag.closest(".media").find("#post-id").val();
	
	view_more_tag.find("#comments-view-more-btn").addClass("d-none");
	view_more_tag.find(".loader").removeClass("d-none");
	
	$.ajax(
			{
				type: "GET",
				url: ajax_url,
				dataType: "html",
				success: function(data)
					{
						view_more_tag.prev(".comments-list").html(data);
						view_more_tag.find(".loader").addClass("d-none");
					}
			}
		);
}


function request_more_movieparties(view_more_tag) {
	
	++movieparties_page_index;
	var ajax_url = "getmovieparties?pageid=" + movieparties_page_index;
	
	view_more_tag.find("#movieparties-view-more-btn").addClass("d-none");
	view_more_tag.find(".loader").removeClass("d-none");
	
	$.ajax(
			{
				type: "GET",
				url: ajax_url,
				dataType: "html",
				success: function(data)
					{
						var has_items = $($.parseHTML(data)).filter(".media").length > 0;
						
						if ( has_items )
							{
								view_more_tag.prev("#movieparties-list").append(data);
								view_more_tag.find("#movieparties-view-more-btn").removeClass("d-none");
							}
						
						view_more_tag.find(".loader").addClass("d-none");
					}
			}
		);
}

$(document).ready( function()
	{
		$(document).on("click", "#posts-view-more-btn", function() { request_more_posts($(this).closest("#view-more-posts-tag")) } );
		$(document).on("click", "#comments-view-more-btn", function() { request_all_posts($(this).closest("#view-more-comments-tag")) } );
		
		$(document).on("click", "#movieparties-view-more-btn", function() { request_more_movieparties($(this).closest("#view-more-movieparties-tag")) } );
	});