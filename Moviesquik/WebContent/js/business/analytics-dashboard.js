/**
 * 
 */

const GOOGLE_ANALYTICS_SIGNIN_BTN_CLASS = '.g-signin2';

// Google Analytics VIEW ID
var VIEW_ID = '';

var sessionsCountryMapChart;
var analyticsDataStartDate = '';
var analyticsDataEndDate = '';


google.charts.load('current', {
	   'packages': ['geochart']
	 });

function getTableRowDataHtml(dataArray) 
{
	var tdHtml = '';
	
	for ( var i in dataArray )
		tdHtml += '<td>' + dataArray[i] + '</td>';
	
	return tdHtml;
}

function fetchAnalyticsData(displayDataCallback, metrics, dimensions, sorting) 
{
	$('#fetch-data-error-div').html('');
	
	gapi.client.request({
		  path: '/v4/reports:batchGet',
		  root: 'https://analyticsreporting.googleapis.com/',
		  method: 'POST',
		  body: {
		    reportRequests: [
		      {
		        viewId: VIEW_ID,
		        "dateRanges": [
		            {"endDate": analyticsDataEndDate, "startDate": analyticsDataStartDate}
		          ],
		          "metrics": metrics,
		          "dimensions" : dimensions,
		          "orderBys" : sorting
		      }
		    ]
		  }
		}).then(displayDataCallback, function(response) {
			if ( response.result.error )
				$('#fetch-data-error-div').html('<span class="fa fa-warning text-warning"></span> ' + response.result.error.message + '<br><br>');
		});
}


// users and pageviews over time
function displayUsersAndPageviewsResults(response) 
{
	var totals = response.result.reports[0].data.totals[0];
	var totalSessions = totals.values[0];
	var totalPageviews = totals.values[1];
	var sessionDuration = totals.values[2];
	
	$('#total-sessions').text(totalSessions);
	$('#total-pageviews').text(totalPageviews);
	$('#total-session-duration').text(sessionDuration + 's');
}
function fetchUsersAndPageviews() 
{
	var metrics = [ {"expression": "ga:sessions"}, {"expression": "ga:pageviews"}, {"expression": "ga:sessionDuration"} ];
	fetchAnalyticsData(displayUsersAndPageviewsResults, metrics);
}

// mobile traffic
function displayMobileTrafficResults(response) 
{
	var totals = response.result.reports[0].data.totals[0];
	var sessions = totals.values[0];
	var pageviews = totals.values[1];
	var sessionDuration = totals.values[2];
	
	$('#mobile-sessions').text(sessions);
	$('#mobile-pageviews').text(pageviews);
	$('#mobile-session-duration').text(sessionDuration + 's');
}
function fetchMobileTraffic() 
{
	var metrics = [ {"expression": "ga:sessions"}, {"expression": "ga:pageviews"}, {"expression": "ga:sessionDuration"} ];
	var dimensions = [ {"name": "ga:mobileDeviceInfo"}, {"name": "ga:source"} ];
	fetchAnalyticsData(displayMobileTrafficResults, metrics, dimensions);
}

// new vs returning sessions
function displayNewVsReturningSessionsResults(response) 
{
	var allData = response.result.reports[0].data.rows;
	var newVisitorsData = allData[0];
	var returningVisitorsData = allData[1];
	
	var newVisitors = newVisitorsData.metrics[0].values[0];
	var returningVisitors = returningVisitorsData.metrics[0].values[0];
	
	$('#new-visitors').text(newVisitors);
	$('#returning-visitors').text(returningVisitors);
}
function fetchNewVsReturningSessions() 
{
	var metrics = [ {"expression": "ga:sessions"} ];
	var dimensions = [ {"name": "ga:userType"} ];
	fetchAnalyticsData(displayNewVsReturningSessionsResults, metrics, dimensions);
}

// top content (pages paths)
function displayTopContentResults(response) 
{	
	$('#top-content-data').html('');
	var allData = response.result.reports[0].data.rows;
	
	for( var i in allData )
	{
		var rowData = allData[i];
		
		var pagePath = rowData.dimensions[0];
		var tdHtml = '<td>' + pagePath + '</td>' + getTableRowDataHtml(rowData.metrics[0].values);
		
		$('#top-content-data').append('<tr>' + tdHtml + '</tr>');
	}
}
function fetchTopContent() 
{
	var metrics = [ {"expression": "ga:pageviews"}, {"expression": "ga:uniquePageviews"}, {"expression": "ga:timeOnPage"}, {"expression": "ga:bounces"},
					{"expression": "ga:entrances"}, {"expression": "ga:exits"} ];
	var dimensions = [ {"name": "ga:pagePath"} ];
	var sorting = [ {"fieldName": "ga:pageviews" } ];
	fetchAnalyticsData(displayTopContentResults, metrics, dimensions, sorting);
}

// browser and operating system
function displayBrowserOperatingSystemResults(response) 
{	
	$('#browser-os-data').html('');
	var allData = response.result.reports[0].data.rows;
	
	for( var i in allData )
	{
		var rowData = allData[i];
		var tdHtml = getTableRowDataHtml(rowData.dimensions) + '<td>' + rowData.metrics[0].values[0] + '</td>';
		
		$('#browser-os-data').append('<tr>' + tdHtml + '</tr>');
	}
}
function fetchBrowserOperatingSystem() 
{
	var metrics = [ {"expression": "ga:sessions"} ];
	var dimensions = [ {"name": "ga:operatingSystem"}, {"name": "ga:operatingSystemVersion"}, {"name": "ga:browser"}, {"name": "ga:browserVersion"} ];
	fetchAnalyticsData(displayBrowserOperatingSystemResults, metrics, dimensions);
}

// sessions by country
function getSessionsCountryMapChartEmpyDataArray() 
{
	var dataArray = [['Country', 'Total Sessions']];
	return dataArray;
}
function getSessionsCountryMapChartOptions() 
{
	var options = {
			colorAxis: {
				colors: ['#eebe7c','#ff9400','#f37f13','#87200F','#eb640b',]
			},
			backgroundColor: '#F8F9FA',
			datalessRegionColor: '#EEE',
			legend: { textStyle: {color: '#000', fontSize: 14} }
		};
	return options;
}
function displaySessionsByCountryResults(response) 
{
	var chartDataArray = getSessionsCountryMapChartEmpyDataArray();
	var allData = response.result.reports[0].data.rows;
	
	for( var i in allData )
	{
		var rowData = allData[i];
		
		var country = rowData.dimensions[0];
		var sessionsCount = parseInt(rowData.metrics[0].values[0]);
		
		chartDataArray.push([country, sessionsCount]);
	}
	
	var chartData = google.visualization.arrayToDataTable(chartDataArray);
	var options = getSessionsCountryMapChartOptions();
	
	sessionsCountryMapChart.draw(chartData, options);
}
function fetchSessionsByCountry() 
{
	var metrics = [ {"expression": "ga:sessions"} ];
	var dimensions = [ {"name": "ga:country"} ];
	fetchAnalyticsData(displaySessionsByCountryResults, metrics, dimensions);
}


function fetchAllGoogleAnalyticsData() 
{
	fetchUsersAndPageviews();
	fetchMobileTraffic();
	fetchNewVsReturningSessions();
	
	fetchSessionsByCountry();
	
	fetchTopContent();
	fetchBrowserOperatingSystem();
}

function getValidGoogleAnalyticsDateFormat(dateString) 
{
	if ( !dateString )
		return null;
	return new Date(dateString).toISOString().substr(0, 10);
}

function updateAnalyticsDataTimePeriod(startDateString, endDateString) 
{
	var startDate = getValidGoogleAnalyticsDateFormat(startDateString);
	var endDate   = getValidGoogleAnalyticsDateFormat(endDateString);
	
	if ( startDate == null || endDate == null )
		return;
	
	analyticsDataStartDate = startDate;
	analyticsDataEndDate = endDate;
	
	fetchAllGoogleAnalyticsData();
}

function initAnalyticsDataTimePeriod() 
{
	var endDate = new Date();
	var startDate = new Date();
	startDate.setTime( endDate.getTime() - ((24*60*60*1000) * 7) );
	
	var startDateString = startDate.toISOString().substr(0, 10);
	var endDateString = endDate.toISOString().substr(0, 10);
	
	$('#start-date').val(startDateString);
	$('#end-date').val(endDateString);
	
	analyticsDataStartDate = startDateString;
	analyticsDataEndDate = endDateString;
}

window.initAnalyticsDashboard = function() 
{
	if ( !$(GOOGLE_ANALYTICS_SIGNIN_BTN_CLASS).length )
		return;
	
	// set google analytics VIEW ID
	VIEW_ID = $('#ga-view-id').val();
	
	// initialize sessions by country map chart
	var data = google.visualization.arrayToDataTable(getSessionsCountryMapChartEmpyDataArray());
	var options = getSessionsCountryMapChartOptions();
	
	var mapChartImageDiv = document.getElementById('image-sessions-by-country-chart');
	var mapChartDiv = document.getElementById('sessions-by-country-chart');
	
	sessionsCountryMapChart = new google.visualization.GeoChart(mapChartDiv);
	google.visualization.events.addListener(sessionsCountryMapChart, 'ready', function () {
			mapChartImageDiv.innerHTML = '<img src="' + sessionsCountryMapChart.getImageURI() + '">';
	    });
	
	sessionsCountryMapChart.draw(data, options);
	
	// initialize analytics data time period
	initAnalyticsDataTimePeriod();
	
	// register data time period change event
	$(document).on('click', '#analytics-data-sync-btn', function() 
	{
		var startDateString = $('#start-date').val();
		var endDateString   = $('#end-date').val();
		updateAnalyticsDataTimePeriod(startDateString, endDateString);
	});
	
	// fetch google analytics data
	fetchAllGoogleAnalyticsData();
}