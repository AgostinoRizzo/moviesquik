/**
 * 
 */

import './media-search-utils.js';

const EXTERNAL_DB_API_DATA_REQUEST_URL = 'business/media/apidata';

const MEDIA_SEARCH_RESULTS_CONTAINER_ID = '#media-search-results-container';

const REGISTERED_MEDIA_INFO_HTML = 
		'<div><strong><span class="fa fa-check-square text-success"></span> Currently registered on the Moviesquik database.</strong></div>' +
		'<div class="note">The media content is stored in the official Moviesquik database, currently available to all users and subjectd to the analytics monitoring.</div>';

const MEDIA_REGISTRATION_URL = 'business/media/register';

var EXTERNAL_DB_API_KEY;
var EXTERNAL_DB_API_URL;
var searchResult;

function createMediaSearchResultHtml(mediaContent, mediaContentIndex) 
{
	var html = '';
	if ( mediaContentIndex % 2 == 0 )
		html = '<div class="row">';
	html +=
			'<div class="col-2">' + 
				'<img class="card-img-top can-point" src="' + mediaContent.Poster + '">' +
			'</div>' +
			'<div class="col-4 media-info tile">' + 
				'<input type="hidden" class="omdb-id" value="' + mediaContent.imdbID + '">' +
				'<h5><strong>' + mediaContent.Title + '</strong></h5><br>' +
				'<div class="note"><strong>Year:</strong> ' + mediaContent.Year + '</div>' +
				'<div class="note"><strong>Type:</strong> ' + mediaContent.Type + '</div><br>' +
				'<div class="media-registration-info">' +
					( mediaContent.registered
					? REGISTERED_MEDIA_INFO_HTML
					: '<div><strong><span class="fa fa-warning text-warning"></span> Currently unavailable on the Moviesquik database.</strong></div><br>' +
					  '<div><button type="button" class="btn btn-primary" id="register-media-btn"><span class="fa fa-plus"></span> Register</button><span class="registering-status"></span></div><br>' +
					  '<div class="note">By registering the media content, it will be stored in the official Moviesquik database, available to all users and subjectd to the analytics monitoring.</div>') +
				'</div>' +
			'</div>';
	if ( mediaContentIndex % 2 == 1 )
		html += '</div><br>';
	return html;
}

function addMediaRegistrationStatus(mediaSearchResult) 
{
	$.ajax(
		{
			type: 'POST',
			url: MEDIA_REGISTRATION_URL + '?check=true',
			contentType: 'application/json',
			data: JSON.stringify(mediaSearchResult),
			success: function(data) 
				{
					if ( data.error )
					{
						$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html( MEDIA_SEARCH_ERROR_ALERT_HTML );
						return;
					}
					
					searchResult = data;
					
					var html = '';
					for( var i in data.Search )
						html += createMediaSearchResultHtml(data.Search[i], i);
					$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(html);
				}
		}
	);
}

function onMediaSearch() 
{
	const title = $('#media-search-form #title').val();
	
	if ( !title.length )
	{
		$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(SEARCH_TITLE_REQUIRED_ALERT_HTML);
		return;
	}
	
	const year  = $('#media-search-form #year').val();
	
	const titleParam = title.length ? '&s=' + title : '';
	const yearParam  = year.length  ? '&y=' + year  : '';
	
	$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(SEARCH_LOADER_HTML);
	
	$.ajax(
		{
			type: 'GET',
			url: EXTERNAL_DB_API_URL + titleParam + yearParam,
			dataType: "json",
			success: function(data)
				{
					if ( data.Search )
					{
						if ( !data.Search.length )
						{
							$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html( MEDIA_SEARCH_EMPTY_ALERT_HTML );
							return;
						}
						addMediaRegistrationStatus(data);
					}
					else
						$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(MEDIA_SEARCH_ERROR_ALERT_HTML);
				}
		}
	);
}

function sendMediaRegistrationRequest(mediaData, registerBtn) 
{
	$.ajax(
		{
			type: 'POST',
			url: MEDIA_REGISTRATION_URL,
			contentType: 'application/json',
			data: JSON.stringify(mediaData),
			success: function(data) 
				{
					var mediaInfo = registerBtn.closest('.media-info');
					if ( data.success )
						mediaInfo.find('.media-registration-info').html(REGISTERED_MEDIA_INFO_HTML);
					else
					{
						mediaInfo.find('.registering-status').html(' <span class="fa fa-warning text-warning"></span> Error during registration.');
						registerBtn.prop('disabled', false);
						registerBtn.html('<span class="fa fa-plus"></span> Register');
					}
				}
		}
	);
}

function onMediaRegister(registerBtn, omdbId) 
{
	registerBtn.prop('disabled', true);
	registerBtn.html('<span class="fa fa-cloud-upload"></span> Registering...');
	
	$.ajax(
		{
			type: 'GET',
			url: EXTERNAL_DB_API_URL + '&i=' + omdbId,
			dataType: "json",
			success: function(data)
				{
					if ( !data.Error )
						sendMediaRegistrationRequest(data, registerBtn);
				}
		}
	);
}

window.initMediaSearch = function() 
{
	$.ajax(
		{
			type: 'GET',
			url: EXTERNAL_DB_API_DATA_REQUEST_URL,
			dataType: "json",
			success: function(data)
				{
					if ( data.url && data.key )
					{
						EXTERNAL_DB_API_KEY = data.key;
						EXTERNAL_DB_API_URL = data.url + EXTERNAL_DB_API_KEY;
						
						$(document).on('click', '#media-search-form #search-media-btn', onMediaSearch);
						$(document).on('click', '#media-search-results-container #register-media-btn', function() {
							onMediaRegister( $(this), $(this).closest(".media-info").find('.omdb-id').val() );
						});
					}
				}
		}
	);
}