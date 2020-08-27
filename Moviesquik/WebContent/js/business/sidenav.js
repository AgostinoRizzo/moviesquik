/**
 * 
 */

import './cdn-charts.js';
import './media-search.js';

const SELECTED_CLASS = 'selected';
const HOME_PAGE_CONTENT_ID = '#home-page-content';
const HOME_PAGE_CONTENT_LOADER_HTML = '<div class="loader loader-sm"></div>';

var currSidenavSelectedItem;

function onSidenavItemClick(clickedItem, homePageContentFetchUrl, onFetchCallback=null) 
{
	if ( currSidenavSelectedItem )
		currSidenavSelectedItem.removeClass(SELECTED_CLASS);
	currSidenavSelectedItem = clickedItem;
	currSidenavSelectedItem.addClass(SELECTED_CLASS);
	
	$(HOME_PAGE_CONTENT_ID).html(HOME_PAGE_CONTENT_LOADER_HTML);
	fetchHomePageContent(homePageContentFetchUrl, onFetchCallback);
}

function fetchHomePageContent(homePageContentFetchUrl, onFetchCallback=null) 
{
	$.ajax(
		{
			type: 'GET',
			url: homePageContentFetchUrl,
			dataType: "html",
			success: function(data)
				{
					$(HOME_PAGE_CONTENT_ID).html(data);
					if ( onFetchCallback )
						onFetchCallback();
				}
		}
	);
}

$(document).ready(function() 
	{
		onSidenavItemClick($('#dashboard-link-item'), 'business/media');
		
		$('#media-contents-link-item').click( function() { onSidenavItemClick($(this), 'business/media', initMediaSearch); } );
		$('#analytics-link-item')     .click( function() { onSidenavItemClick($(this), 'business/analytics'); } );
		$('#servers-link-item')       .click( function() { onSidenavItemClick($(this), 'business/cdn', fetchCDNServersList); } );
	}
);