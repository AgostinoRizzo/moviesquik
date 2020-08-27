/**
 * 
 */

import './chart-utils.js';

google.charts.load('current', {'packages' : ['corechart']});


function getDataArray(chartData, value1Label, value2Label=null)  
{
	var dataArray = [];
	
	dataArray.push(value2Label ? ['Day', value1Label, value2Label] : ['Day', value1Label]);
	for ( var i in chartData )
	{
		var item = chartData[i];
		dataArray.push(value2Label ? [item.day, item.value1, item.value2] : [item.day, item.value1]);
	}
	
	return dataArray;
}

function initChartData(chartTitle, chartTagId, chartData, value1Label, value2Label=null) 
{
	var data = google.visualization.arrayToDataTable( getDataArray(chartData, value1Label, value2Label) );
	var options = getChartOptions(chartTitle);

    var chart = new google.visualization.AreaChart(document.getElementById(chartTagId));
    chart.draw(data, options);
}

window.initTrendingChartData = function(chartData) 
{
	initChartData('Trending/Popularity chart', 'trending-chart', chartData, 'Trending Rate', 'Popularity Rate');
}

window.initRatingsChartData = function(chartData) 
{
	initChartData('Ratings chart', 'ratings-chart', chartData, 'Rate');
}

window.initLikesChartData = function(chartData) 
{
	initChartData('Likes/Dislikes chart', 'likes-chart', chartData, 'Likes', 'No Likes');
}

window.initViewsChartData = function(chartData) 
{
	initChartData('Views chart', 'views-chart', chartData, 'views');
}