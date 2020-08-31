/**
 * 
 */

const MIN_SCROLL_PERCENTACE = 70; // at least 70% page scrolling

var pageScrollSent = false;
var startTime;

function computePageScrollPercentage() 
{
	var s = $(window).scrollTop(), d = $(document).height(), c = $(window).height();
	var scrollPercentage = ( s / (d - c) ) * 100;
	return scrollPercentage;
}

function onPageScrollLog(mediaContentId) 
{
	$.ajax(
		{
			type: "GET",
			url: 'analytics/mcstats?key=' + mediaContentId + '&event=scroll',
			dataType: "json",
			success: function(data) {}
		}
	);
}

function onPageSpentTimeLog(mediaContentId, time) 
{
	$.ajax(
		{
			type: "GET",
			url: 'analytics/mcstats?key=' + mediaContentId + '&event=time&val=' + time,
			dataType: "json",
			success: function(data) {}
		}
	);
}

window.initLogStats = function() 
{
	$(window).scroll( function() 
	{
		if ( pageScrollSent )
			return;
		
		var pageScroll = computePageScrollPercentage();
		if ( pageScroll >= MIN_SCROLL_PERCENTACE )
		{
			onPageScrollLog( $("#media-content-page-container #media-id").val() );
			pageScrollSent = true;
		}
	});
	
	pageScrollSent = false;
	startTime = new Date().getTime();
}

window.finalizeLogStats = function() 
{
	$(window).unbind('scroll');
	pageScrollSent = true;
	var spentTime = Math.round( (new Date().getTime() - startTime) / 1000 );
	onPageSpentTimeLog( $("#media-content-page-container #media-id").val(), spentTime );
}

$(document).ready(function()
{
	//initLogStats();
});