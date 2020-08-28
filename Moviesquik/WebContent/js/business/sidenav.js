/**
 * 
 */

import './cdn-charts.js';
import './media-search.js';
import './analytics-dashboard.js';

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

function onDashboardLinkClick()     { onSidenavItemClick($('#dashboard-link-item'), 'business/dashboard', initAnalyticsDashboard); }
function onMediaContentsLinkClick() { onSidenavItemClick($('#media-contents-link-item'), 'business/media', initMediaSearch); }
function onAnalyticsLinkClick()     { onSidenavItemClick($('#analytics-link-item'), 'business/analytics'); }
function onServersLinkClick()       { onSidenavItemClick($('#servers-link-item'), 'business/cdn', fetchCDNServersList); }

$(document).ready(function() 
	{
		onDashboardLinkClick();
		
		$('#dashboard-link-item')     .click( onDashboardLinkClick );
		$('#media-contents-link-item').click( onMediaContentsLinkClick );
		$('#analytics-link-item')     .click( onAnalyticsLinkClick );
		$('#servers-link-item')       .click( onServersLinkClick );
		
		$('#header-dashboard-link-item')     .click( onDashboardLinkClick );
		$('#header-media-contents-link-item').click( onMediaContentsLinkClick );
		$('#header-analytics-link-item')     .click( onAnalyticsLinkClick );
		$('#header-servers-link-item')       .click( onServersLinkClick );
		
		$('#menu-dashboard-link-item')     .click( onDashboardLinkClick );
		$('#menu-media-contents-link-item').click( onMediaContentsLinkClick );
		$('#menu-analytics-link-item')     .click( onAnalyticsLinkClick );
		$('#menu-servers-link-item')       .click( onServersLinkClick );
	}
);