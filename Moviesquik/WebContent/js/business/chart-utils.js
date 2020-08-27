 	/**
 * 
 */

const COMMON_CHART_OPTIONS =
		{
	      legend: { position: 'top', alignment: 'end', maxLines: 3, textStyle : {color: '#fff'} },
	      vAxis : {	viewWindowMode: 'explicit', viewWindow: {min:0.0, max:5.0}, textStyle : {color: '#fff'}, titleTextStyle : {color: '#fff'} },
	      hAxis : {	title: 'Day', textStyle : {color: '#fff'}, titleTextStyle : {color: '#fff'}, gridlines: {color: 'transparent'} },
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

window.getChartOptions = function(chartTitle) 
{
	var options = JSON.parse(JSON.stringify(COMMON_CHART_OPTIONS));
	options.title = chartTitle;
	return options;
}