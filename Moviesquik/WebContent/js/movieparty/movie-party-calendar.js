/**
 * 
 */

function onCalendarShow() 
{
	var ajax_url = 'movieparty/calendar';
	$("#calendar-container .loader").removeClass("d-none");
	
	$.ajax(
		{
			type: "GET",
			url: ajax_url,
			dataType: "html",
			success: function(data)
				{
					$("#calendar-container").html(data);
					$("#calendar-container .loader").addClass("d-none");
				}
		}
	);
}

$(document).ready( function() 
	{
		$("#calendar-container").on('shown.bs.collapse', onCalendarShow);
	}
);