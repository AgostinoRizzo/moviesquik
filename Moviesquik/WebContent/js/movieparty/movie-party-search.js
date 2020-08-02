/**
 * 
 */

var movieparties_page_index = 0;
var movieparty_filter = null;

function onRemoveFilter()
{
	movieparty_filter = null;
	onMoviePartySearch('all');
}

function displayFilter() 
{
	$("#movieparty-filter-display").html('');
	
	if ( movieparty_filter == null || movieparty_filter == 'all' )
		return;
	
	var filterName = null;
	if      ( movieparty_filter == 'public' )   filterName = 'Public only';
	else if ( movieparty_filter == 'private' )  filterName = 'Private only';
	else if ( movieparty_filter == 'playing' )  filterName = 'Playing now';
	else if ( movieparty_filter == 'upcoming' ) filterName = 'Upcoming';
	else if ( movieparty_filter == 'expired' )  filterName = 'Expired';
	else return;
	
	$("#movieparty-filter-display")
		.append('<br><div class="btn btn-link btn-sm" id="remove-filter-btn"><i class="fa fa-remove"></i> ' + filterName + '</div>');
	
	$(document).on("click", "#remove-filter-btn",   function() { onRemoveFilter(); });
}

window.request_more_movieparties = function(view_more_tag) {
	
	++movieparties_page_index;
	var ajax_url = "getmovieparties?pageid=" + movieparties_page_index;
	
	if ( movieparty_filter != null )
		ajax_url += "&filter=" + movieparty_filter;
	
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

window.onMoviePartySearch = function(filter) 
{
	movieparty_filter = filter;
	
	var ajax_url = "getmovieparties?pageid=0&filter=" + movieparty_filter;
	
	//view_more_tag.find("#posts-view-more-btn").addClass("d-none");
	$("#movieparty-filter-search-loader").removeClass("d-none");
	
	$.ajax(
		{
			type: "GET",
			url: ajax_url,
			dataType: "html",
			success: function(data)
				{
					//var has_items = $($.parseHTML(data)).filter(".media").length > 0;
					$("#movieparties-list").html(data);
					$("#movieparties-view-more-btn").removeClass("d-none");
					
					movieparties_page_index = 0;
					
					$("#movieparty-filter-search-loader").addClass("d-none");
					//view_more_tag.find(".loader").addClass("d-none");
					
					displayFilter();
				}
		}
	);
}

