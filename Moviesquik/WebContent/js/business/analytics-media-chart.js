/**
 * 
 */

google.charts.load('current', {'packages' : ['corechart']});

const COMMON_CHART_OPTIONS =
		{
	      legend: {position: 'top', alignment: 'end', maxLines: 3, textStyle : {color: '#fff'}},
	      vAxis : {	viewWindowMode: 'explicit', viewWindow: {min:0.0, max:5.0}, textStyle : {color: '#fff'}, titleTextStyle : {color: '#fff'} },
	      hAxis : {	title: 'Day', textStyle : {color: '#fff'}, titleTextStyle : {color: '#fff'} },
	      pointSize: 5,
	      width: 450,
	      height: 300,
	      chartArea: {width : '90%', height : '60%'},
	      backgroundColor: '#1c1c1e',
	      titleTextStyle : {color: '#fff'},
	      series: {
	    	 	0: { color: '#9C48E8' },
	    	 	1: { color: '#28a745' },
	            2: { color: '#e2431e' },
	            3: { color: '#e7711b' },
	            4: { color: '#f1ca3a' },
	            5: { color: '#1c91c0' }
	          }
	    };

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

function getChartOptions(chartTitle) 
{
	var options = JSON.parse(JSON.stringify(COMMON_CHART_OPTIONS));
	options.title = chartTitle;
	return options;
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