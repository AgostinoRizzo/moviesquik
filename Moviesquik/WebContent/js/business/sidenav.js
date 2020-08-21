/**
 * 
 */

const SELECTED_CLASS = 'selected';
const HOME_PAGE_CONTENT_ID = '#home-page-content';
const HOME_PAGE_CONTENT_LOADER_HTML = '<div class="loader loader-sm"></div>';

var currSidenavSelectedItem;

function onSidenavItemClick(clickedItem) 
{
	if ( currSidenavSelectedItem )
		currSidenavSelectedItem.removeClass(SELECTED_CLASS);
	currSidenavSelectedItem = clickedItem;
	currSidenavSelectedItem.addClass(SELECTED_CLASS);
	
	$(HOME_PAGE_CONTENT_ID).html(HOME_PAGE_CONTENT_LOADER_HTML);
	fetchHomePageContent();
}

function fetchHomePageContent() 
{
	$.ajax(
		{
			type: 'GET',
			url: 'business/media',
			dataType: "html",
			success: function(data)
				{
					$(HOME_PAGE_CONTENT_ID).html(data);
				}
		}
	);
}

$(document).ready(function() 
	{
		onSidenavItemClick($('#dashboard-link-item'));
		
		$('#media-contents-link-item').click( function() 
		{
			onSidenavItemClick($(this));
		});
	}
);