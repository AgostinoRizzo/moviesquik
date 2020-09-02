/**
 * 
 */

window.showMainAlert = function(alertText) 
{
	$("#main-alert").html(alertText + '<button type="button" class="close" data-dismiss="alert">&times;</button>');
	$("#main-alert").hide();
	if ( $("#main-alert").hasClass('d-none') )
		$("#main-alert").removeClass('d-none');
	$("#main-alert").slideDown('slow');
}

window.showIconMainAlert = function(iconSrc, alertText) 
{
	showMainAlert( '<img src="' + iconSrc + '" class="avatar-img card-list-avatar-img rounded-circle">&nbsp;&nbsp;' + alertText );
}