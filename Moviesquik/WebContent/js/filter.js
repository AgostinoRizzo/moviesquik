/**
 * 
 */

var selectedGenres = [];
var currentSelectedGenre = "";

function update() {
	if ( $("#full-view-btn").hasClass("active") )
		request_media_contents("mcsearch?type=" + typeSearch + "&view=full&reqtype=mc_update", null);
	else if ( $("#group-view-btn").hasClass("active") )
		request_media_contents("mcsearch?type=" + typeSearch + "&view=group&reqtype=mc_update", null);
}

function getCurrentFilterObject() {
	var filteredGenres = [currentSelectedGenre];
	if ( selectedGenres.length > 0 )
		filteredGenres = selectedGenres;
	
	var obj =
		{
			genres: filteredGenres,
			duration: $("#duration-select").val(),
			features: $("#features-select").val()
		};
	return obj;
}

function updateSingleGenreLabel() {
	var genreVal = $("#genres-select").find(":selected").val();
	if ( genreVal == "all" )
		{
			currentSelectedGenre = "";
			genreVal = "All Genres";
		}
	else
		currentSelectedGenre = genreVal;
	$("#selected-genre-label").text( genreVal );
}

function hideContentsOnUpdate() {
	$(".loader").removeClass("d-none");
	$("#media-contents-type-search-result-title").children().hide();
	$("#type-search-content").children().hide();
}

function showContentsOnUpdate() {
	$(".loader").addClass("d-none");
	$("#media-contents-type-search-result-title").children().show();
	$("#type-search-content").children().show();
}

function request_media_contents(url_str, newTypeSearch)
{
	hideContentsOnUpdate();
	
	$.ajax(
		{
			type: "POST",
			url: url_str,
			contentType: "application/json",
			dataType: "html",
			data: JSON.stringify( getCurrentFilterObject() ),
			success: function(data)
				{
					$("#type-search-content").html(data);
					$("#media-contents-type-search-result-title")
						.html( $("#media-contents-search-update-result-title").html() );
					
					if ( newTypeSearch != null )
						typeSearch = newTypeSearch;
					
					showContentsOnUpdate();
				}
		}
	);
};

function type_search_request_media_contents(url_str, newTypeSearch)
{
	hideContentsOnUpdate();
	
	$.ajax(
		{
			type: "POST",
			url: url_str,
			contentType: "application/json",
			dataType: "html",
			data: JSON.stringify( getCurrentFilterObject() ),
			success: function(data)
				{
					$("#type-search-content").html(data);
					if ( $("#media-contents-type-search-list-header").hasClass("d-none") )
						$("#media-contents-type-search-list-header").removeClass("d-none");
					else
						$("#media-contents-type-search-list-header").addClass("d-none");
					$("#media-contents-type-search-result-title")
						.html( $("#media-contents-search-update-result-title").html() );
					
					if ( newTypeSearch != null )
						typeSearch = newTypeSearch;
					
					showContentsOnUpdate();
				}
		}
	);
};



var typeSearch = "";

$(document).ready( function()
	{
		typeSearch = $("#type-search").val();
		
		$("#genres-select").change(function() {
			if ( selectedGenres.length == 0 )
				{
					updateSingleGenreLabel();
					update();
				}
		});
		
		$("#duration-select").change(function() {
			update();
		});
		
		$("#features-select").change(function() {
			update();
		});
		
		$("#add-genre-filter-btn").click(function() {
			 var selectedOption = $("#genres-select").find(":selected");
			 
			 if ( !selectedOption.hasClass("note") && selectedOption.attr("id") != "all-genres-option" )
				 {
				 	 selectedGenres.push( selectedOption.val() );
					 selectedOption.addClass("note");
					 
					 $("#genres-select #all-genres-option").addClass("note");
					 $("#clear-filter-btn").removeClass("d-none");
					 
					 $("#selected-genre-label").text("");
					 var first = true;
					 var genresStr = "";
					 
					 for ( var genre in selectedGenres )
						 if ( first )
							 {
							 	genresStr = selectedGenres[genre];
							 	first = false;
							 }
						 else
							 genresStr += ", " + selectedGenres[genre];
					 $("#selected-genre-label").text( genresStr );
					 
					 if ( selectedGenres.length > 1 )
						 update();
				 }
			 else
				 alert("Cannot add " + selectedOption.text() + ".");
		});
		
		$("#clear-filter-btn").click(function() {
			$("#clear-filter-btn").addClass("d-none");
			selectedGenres = [];
			$("#genres-select option").removeClass("note");
			$("#genres-select").val("all");
			updateSingleGenreLabel();
			update();
		});
		
		$("#sorting-policy-type-search-select").change(function() {
			
			var query = $(this).siblings("#search-query").val();
			var policy = $(this).val();
			
			request_media_contents("mcsearch?type=" + typeSearch + "&view=full&sorting_policy=" + policy + "&reqtype=mc_update", typeSearch);
		});	

		/* search result view template (group or full) */
		
		$("#group-view-btn").click(function() {
			if ( $("#full-view-btn").hasClass("active") )
				{
					$("#full-view-btn").removeClass("active");
					$("#group-view-btn").addClass("active");
					
					type_search_request_media_contents("mcsearch?type=" + typeSearch + "&view=group&reqtype=mc_update", typeSearch);
				}
		});
		
		$("#full-view-btn").click(function() {
			if ( $("#group-view-btn").hasClass("active") )
				{
					$("#group-view-btn").removeClass("active");
					$("#full-view-btn").addClass("active");
					
					type_search_request_media_contents("mcsearch?type=" + typeSearch + "&view=full&reqtype=mc_update", typeSearch);
				}
		});
	}
);