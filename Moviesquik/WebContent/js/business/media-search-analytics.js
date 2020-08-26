/**
 * 
 */

import './media-search-utils.js';

const ANALYTICS_MEDIA_SEARCH_URL = 'business/analytics';
const MEDIA_SEARCH_RESULTS_CONTAINER_ID = '#analytics-media-search-results';
const RATING_SEARCH_RESULT_ICON_HTML = '<span class="fa fa-line-chart checked"></span>';
const TITLE_SEARCH_RESULT_ICON_HTML = '<span class="fa fa-search"></span>';

var searchResultsPageHtml = '';

function addAnalyticsSearchResults(mediaContents, resultTitle, isByRating=true)
{
	var titleHtml = isByRating ? RATING_SEARCH_RESULT_ICON_HTML : TITLE_SEARCH_RESULT_ICON_HTML;
	titleHtml += ' ' + resultTitle;
	$(MEDIA_SEARCH_RESULTS_CONTAINER_ID + ' #contents-section .media-contents-list-header-title').html(titleHtml);
	
	var mediaContentsCards = $(MEDIA_SEARCH_RESULTS_CONTAINER_ID + ' #contents-section .media-contents-list-items')
								.find('.media-content-card');
	
	mediaContentsCards.each(function() { $(this).hide(); });
	
	for ( var i in mediaContents )
	{
		var mediaCard = mediaContentsCards.eq(i);
		if ( !mediaCard.length )
			return;
		
		var media = mediaContents[i];
		
		mediaCard.find('#media-id').val(media.id);
		mediaCard.find('.card-img-top').attr('src', media.poster);
		mediaCard.find('.card-title').text(media.title);
		mediaCard.find('.rate-value').text(media.statistics.rate);
		
		mediaCard.show();
	}
}

function searchAnalyticsMedia(searchUrl, resultTitle, isByRating=true) 
{
	$.ajax(
		{
			type: 'GET',
			url: searchUrl,
			dataType: "json",
			success: function(data)
				{
					if ( data.media && data.media.length )
					{
						$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(searchResultsPageHtml);
						addAnalyticsSearchResults(data.media, resultTitle, isByRating);
					}
					else
						$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(MEDIA_SEARCH_EMPTY_ALERT_HTML);
				}
		}
	);
}

function onAnalyticsMediaSearch() 
{
	searchResultsPageHtml = $(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html();
	$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(SEARCH_LOADER_HTML);
	
	var url = ANALYTICS_MEDIA_SEARCH_URL + '?';
	const title = $('#media-search-form #title').val();
	
	if ( title.length )
	{
		url += 'title=' + title;
		searchAnalyticsMedia(url, 'By title: <span class="note">' + title + '</span>', false);
		return;
	}
	
	var ratingSelect = $('#media-search-form #rating');
	var nativeRatingSelect = $('#media-search-form #rating')[0];
	const rating  = ratingSelect.val();
	url += '&rating=' + rating;
	searchAnalyticsMedia(url, nativeRatingSelect[nativeRatingSelect.selectedIndex].text, false);
}

$(document).ready(function() 
	{
		$(document).on('click', '#media-search-form #search-analytics-media-btn', onAnalyticsMediaSearch);
	}
);