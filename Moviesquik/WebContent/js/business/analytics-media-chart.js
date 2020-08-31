/**
 * 
 */

import './chart-utils.js';

google.charts.load('current', {'packages' : ['corechart']});

function getMaxChartDataValue(chartData) 
{
	var max = 0;
	for ( var i in chartData )
	{
		var values = chartData[i];
		if ( values.value1 > max )
			max = values.value1;
		if ( values.value2 > max )
			max = values.value2;
	}
}

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

function initChartData(chartTagId, chartData, options, value1Label, value2Label=null) 
{
	var data = google.visualization.arrayToDataTable( getDataArray(chartData, value1Label, value2Label) );

    var chart = new google.visualization.AreaChart(document.getElementById(chartTagId));
    chart.draw(data, options);
}

window.initTrendingChartData = function(chartData) 
{
	var options = getChartOptions('Trending/Popularity chart');
	options.vAxis.viewWindow.max = getMaxChartDataValue(chartData);
	initChartData('trending-chart', chartData, options, 'Trending Rate', 'Popularity Rate');
}

window.initRatingsChartData = function(chartData) 
{
	var options = getChartOptions('Ratings chart');
	initChartData('ratings-chart', chartData, options, 'Rate');
}

window.initLikesChartData = function(chartData) 
{
	var options = getChartOptions('Likes/Dislikes chart');
	initChartData('likes-chart', chartData, options, 'Likes', 'No Likes');
}

window.initViewsChartData = function(chartData) 
{
	var options = getChartOptions('Views chart');
	options.vAxis.viewWindow.max = getMaxChartDataValue(chartData);
	initChartData('views-chart', chartData, options, 'views');
}