/**
 * 
 */

import './chart-utils.js';
import './cdn-usage-updater.js';

const CDN_SERVERS_LIST_URL = 'business/cdn/list';
const CDN_CHARTS_LOADER_ID = '#cdn-charts-loader';
const CDN_MAP_CONTAINER_ID = 'cdn-map';
const CDN_MAP_VIEW_ZOOM = 5;
const CDN_USAGE_CHART_CONTAINER_ID = 'cdn-usage';
const CDN_USAGE_CHART_TITLE = 'CDN Servers usage chart';

var serversList;
var cdnUsageChart;

google.charts.load('current', {'packages' : ['corechart']});


/**** CDN (openstreetmap) map chart ****/

function getCdnMapViewLocation(serversList) 
{
	if ( !serversList.length )
		return [0.0, 0.0];
	
	var firstServerLocation = serversList[0].location;
	return [firstServerLocation.latitude, firstServerLocation.longitude];
}

function initCDNMapChartData(serversList)
{
	if ( !serversList.length )
		return;
	
	// initialize CDN map.
	var cdnMap = L.map(CDN_MAP_CONTAINER_ID).setView( getCdnMapViewLocation(serversList), CDN_MAP_VIEW_ZOOM );
	L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
	}).addTo(cdnMap);
	
	// add servers markers to the CDN map.
	for ( var i in serversList )
	{
		const serverData = serversList[i];
		const serverLocation = serverData.location;
		
		L.marker([serverLocation.latitude, serverLocation.longitude]).addTo(cdnMap)
	    	.bindPopup( serverData.name + '<br>' + serverData.description )
	    	.openPopup();
	}
}


/**** CDN usage (google) chart ****/

function getClearSample(serversCount) 
{
	const count = serversCount + 1;
	var sample = [];
	for ( var i=0; i<count; i++ )
		sample.push(0);
	return sample;
}

function getDataArray(serversList)  
{
	var dataArray = [];
	var dataHeader = ['Time'];
	
	for ( var i in serversList )
		dataHeader.push( serversList[i].name );
	dataArray.push(dataHeader);
	
	var sample = getClearSample(serversList.length);
	dataArray.push(sample);
	
	sample[0] = 1;
	dataArray.push(sample);
	
	return dataArray;
}

function getCDNUsageChartOptions() 
{
	var options = getChartOptions(CDN_USAGE_CHART_TITLE);
	
	options.width = 550;
	options.hAxis.textPosition = 'none';
	options.hAxis.title = 'Time';
	options.chartArea.height = '65%';
	options.pointSize = 0;
	options.vAxis.viewWindow.max = 3.0;
	
	return options;
}

function onCDNUsageDataChartUpdate( cdnUsageData ) 
{
	var chartContainer = document.getElementById(CDN_USAGE_CHART_CONTAINER_ID);
	if ( !chartContainer )
	{
		cdnUsageUpdaterFinalize();
		return;
	}
	
	if ( !serversList || !cdnUsageChart || !cdnUsageData.serversUsageData.length )
		return;
	
	var dataArray = getDataArray(serversList);
	dataArray.pop();
	dataArray.pop();
	
	for ( var j in cdnUsageData.serversUsageData[0] )
	{
		var sample = [j];
		
		for ( var i in cdnUsageData.serversUsageData )
			sample.push(cdnUsageData.serversUsageData[i][j]);
		
		dataArray.push(sample);
	}
	
	var data = google.visualization.arrayToDataTable( dataArray );
	var options = getCDNUsageChartOptions();
	
	cdnUsageChart.draw(data, options);
}

function initCDNUsageChartData(serversList) 
{
	if ( !serversList.length )
		return;
	
	var data = google.visualization.arrayToDataTable( getDataArray(serversList) );
	var options = getCDNUsageChartOptions();

	cdnUsageChart = new google.visualization.AreaChart(document.getElementById(CDN_USAGE_CHART_CONTAINER_ID));
	cdnUsageChart.draw(data, options);
    
    // initialize CDN usage updater WebSocket.
    cdnUsageUpdaterInit( onCDNUsageDataChartUpdate );
}

window.fetchCDNServersList = function() 
{
	$(CDN_CHARTS_LOADER_ID).removeClass('d-none');
	
	$.ajax(
		{
			type: 'GET',
			url: CDN_SERVERS_LIST_URL,
			dataType: "json",
			success: function(data)
				{
					if ( data.list )
					{
						serversList = data.list;
						initCDNMapChartData( data.list );
						initCDNUsageChartData( data.list );
						
						$(CDN_CHARTS_LOADER_ID).addClass('d-none');
					}
				}
		}
	);
}
