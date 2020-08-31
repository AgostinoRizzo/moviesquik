/**
 * 
 */

var NY_TIMES_REVIEWS_LIST_TAG_ID = '#ny-times-reviews-list';

var NY_TIMES_API_KEY = '';
var NY_TIMES_REQUEST_URL = '';

function createNewYorkTimeReviewHtml(reviewData) 
{
	var html = 
		'<div class="tile row clickable-light">' +
		
			'<div class="col-auto">' +
				'<img src="res/drawable/ny_times_logo.png" class="avatar-img rounded-circle">' +
			'</div>' +
			
			'<div class="col">' +
			'<strong>Title:</strong> ' + reviewData.display_title + '<br>' +
			'<strong>Author:</strong> ' + reviewData.byline + '<br>' +
			'<strong>Headline:</strong> ' + reviewData.headline + '<br>' +
			'<strong>Short summary about the review:</strong> ' + reviewData.summary_short + '<br><br>' +
			
			'<p class="note">' +
				'<strong>Publication date:</strong> ' + reviewData.publication_date + '<br>' +
				//'<strong>Opening date:</strong> ' + reviewData.opening_date + '<br>' +
				'<strong>Last modified:</strong> ' + reviewData.date_updated + '<br>' +
			'</p>' +
			
			( reviewData.link
			? '<a href="' + reviewData.link.url + '">' + reviewData.link.suggested_link_text + '</a>' : '' ) +
			'</div>' +
		'</div>';
	
	return html;
}

function addNewYorkTimesReviews(allReviews) 
{
	$(NY_TIMES_REVIEWS_LIST_TAG_ID).html('');
	
	if ( !allReviews.length )
		$(NY_TIMES_REVIEWS_LIST_TAG_ID).html('No reviews found.');
	
	for ( var i in allReviews )
	{
		var reviewData = allReviews[i];
		$(NY_TIMES_REVIEWS_LIST_TAG_ID).append( createNewYorkTimeReviewHtml(reviewData) );
	}
}

function loadNewYorkTimesReviews(mediaContentTitle) 
{
	if ( !mediaContentTitle || !mediaContentTitle.length )
		return;
	
	$.ajax(
		{
			type: 'GET',
			url: NY_TIMES_REQUEST_URL + '?query=' + mediaContentTitle + '&api-key=' + NY_TIMES_API_KEY,
			dataType: "json",
			success: function(data) 
			{
				if ( data.status == 'OK' && data.results )
					addNewYorkTimesReviews(data.results);
			}
		}
	);
}

$(document).ready(function()
	{
		var mediaContentTitle = $('#media-content-page-container').find('#media-title').val();
		
		$.ajax(
			{
				type: 'GET',
				url: 'api/nytimes',
				dataType: "json",
				success: function(data)
					{
						if ( data.api_key && data.request_url && data.request_url.length )
						{
							NY_TIMES_API_KEY = data.api_key;
							NY_TIMES_REQUEST_URL = data.request_url;
							
							loadNewYorkTimesReviews(mediaContentTitle);
						}
					}
			}
		);
	}
);