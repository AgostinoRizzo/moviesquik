/**
 * 
 */

import './media-search-utils.js';
import './analytics-media-chart.js';

const ANALYTICS_MEDIA_DASHBOARD_FETCH_URL = 'business/analytics';
const ANALYTICS_MEDIA_DASHBOARD_CHART_DATA_URL = 'business/analytics/chart';

const MEDIA_ANALYTICS_DASHBOARD_CONTAINER_ID = '#analytics-media-dashboard-container';
const MEDIA_ANALYTICS_DASHBOARD_CARD_COL_ID = MEDIA_ANALYTICS_DASHBOARD_CONTAINER_ID + ' #dashboard-media-card-col';
const MEDIA_ANALYTICS_DASHBOARD_CONTENT_COL_ID = MEDIA_ANALYTICS_DASHBOARD_CONTAINER_ID + ' #media-dashboard-content-col';
const MEDIA_ANALYTICS_CHARTS_LOADER_ID = '#media-analytics-charts-loader';

var mediaAnalyticsDashboardPageHtml = '';
var mediaAnalyticsDashboardContentHtml = '';

var mediaContentCard;
var chartsWindow = 'last-week';

function onMediaAnalyticsDashboardFetch() 
{
	const mediaContentId = mediaContentCard.find('#media-id').val();
	var mediaDashboard = $(MEDIA_ANALYTICS_DASHBOARD_CONTAINER_ID);
	
	mediaAnalyticsDashboardContentHtml = $(MEDIA_ANALYTICS_DASHBOARD_CONTENT_COL_ID).html();
	
	$(MEDIA_ANALYTICS_DASHBOARD_CARD_COL_ID).html(mediaContentCard.closest('.analytics-media-col-item').html());
	$(MEDIA_ANALYTICS_CHARTS_LOADER_ID).removeClass('d-none');
	
	$(MEDIA_ANALYTICS_DASHBOARD_CONTAINER_ID).removeClass('d-none');
	
	$.ajax(
		{
			type: 'GET',
			url: ANALYTICS_MEDIA_DASHBOARD_CHART_DATA_URL + '?media=' + mediaContentId + '&win=' + chartsWindow,
			dataType: "json",
			success: function(data)
				{
					if ( data )
					{
						initTrendingChartData( data.trending );
						initRatingsChartData( data.ratings );
						initLikesChartData( data.likes );
						initViewsChartData( data.views );
						
						$(MEDIA_ANALYTICS_CHARTS_LOADER_ID).addClass('d-none');
					}
					//else
					//	$(MEDIA_SEARCH_RESULTS_CONTAINER_ID).html(MEDIA_SEARCH_EMPTY_ALERT_HTML);
				}
		}
	);
}

$(document).ready(function() 
	{
		$(document).on('click', '.analytics-media-col-item', function() {
			mediaContentCard = $(this).find('.media-content-card');
			onMediaAnalyticsDashboardFetch();
		});
		
		$(document).on('change', '#charts-window-select', function() {
			chartsWindow = $(this).val();
			onMediaAnalyticsDashboardFetch();
		});
	}
);